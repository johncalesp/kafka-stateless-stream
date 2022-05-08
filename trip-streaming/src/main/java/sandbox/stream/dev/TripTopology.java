package sandbox.stream.dev;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import sandbox.stream.dev.model.EntityTrip;
import sandbox.stream.dev.processing.MainProcessor;
import sandbox.stream.dev.serialization.Trip;
import sandbox.stream.dev.serialization.avro.AvroSerdes;
import sandbox.stream.dev.serialization.json.TripSerdes;

import java.util.Map;

public class TripTopology {
    // Filter if trip_seconds, trip_miles, or trip_total is 0 or null
    // Make a split for records with company equal to null or "" and replace with "unknown"
    // enrich record by creating two new values cost by second (trip_total/trip_seconds) and cost by mile (trip_total/trip_miles)

    public static Topology build(){
        StreamsBuilder builder = new StreamsBuilder();

        KStream<byte[], Trip> stream =
                builder.stream("trips", Consumed.with(Serdes.ByteArray(), new TripSerdes()));



        // filter out trips with 0 or null values in if trip_seconds, trip_miles, or trip_total
        KStream<byte[], Trip> filtered =
                stream.filter((key, trip) -> isValidTrip(trip));

        filtered.print(Printed.<byte[],Trip>toSysOut().withLabel("filtered-record"));
        // match all trips with empty company value
        Predicate<byte[], Trip> emptyCompany = (key, trip) -> {
            System.out.println(trip.getCompany());
            return trip.getCompany().isEmpty();
        };

        // branch based on previous values
        Map<String, KStream<byte[], Trip>> branches = filtered.split(Named.as("branch-"))
                .branch(emptyCompany, Branched.as("no-company"))
                .defaultBranch(Branched.as("company"));

        KStream<byte[], Trip> emptyCompanyStream = branches.get("branch-no-company");
        emptyCompanyStream.print(Printed.<byte[], Trip>toSysOut().withLabel("trip-no-company"));

        KStream<byte[], Trip> noEmptyCompanyStream = branches.get("branch-company");
        noEmptyCompanyStream.print(Printed.<byte[], Trip>toSysOut().withLabel("trip-company"));

        // set company = unknown for the stream emptyCompanyStream
        MainProcessor mprocessor = new MainProcessor();

        KStream<byte[], Trip> filledCompany = emptyCompanyStream.mapValues(
                (trip) -> mprocessor.fillCompany(trip));

        // merge the two streams
        KStream<byte[], Trip> mergedCompanies = noEmptyCompanyStream.merge(filledCompany);

        KStream<byte[], EntityTrip> aggregatedFieldsTrip =
                mergedCompanies.mapValues((trip) -> {
                    EntityTrip enrichedTrip = mprocessor.generateFieldTrips(trip);
                    return enrichedTrip;
                });

        String sink = "http://localhost:8081";
        aggregatedFieldsTrip.to(
            "trips-enriched",
                Produced.with(Serdes.ByteArray(), AvroSerdes.EntityTrip(sink,false))
        );

        return builder.build();
    }

    private static boolean isValidTrip (Trip trip){
        return (trip.getTripSeconds() != 0 && trip.getTripMiles() != 0 && trip.getTripTotal() != 0 ? true: false);
    }
}
