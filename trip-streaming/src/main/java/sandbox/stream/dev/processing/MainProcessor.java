package sandbox.stream.dev.processing;

import sandbox.stream.dev.serialization.Trip;
import sandbox.stream.dev.model.EntityTrip;
public class MainProcessor {

    public Trip fillCompany(Trip trip){
        trip.setCompany("unknown");
        return trip;
    }


    public EntityTrip generateFieldTrips (Trip trip){
        EntityTrip enrichedTrip = new EntityTrip();

        enrichedTrip.setTripSeconds(trip.getTripSeconds());
        enrichedTrip.setFareBySeconds(trip.getFare()/trip.getTripSeconds());
        enrichedTrip.setTripMiles(trip.getTripMiles());
        enrichedTrip.setFareByMile(trip.getFare()/trip.getTripMiles());
        enrichedTrip.setFare(trip.getFare());
        enrichedTrip.setTips(trip.getTips());
        enrichedTrip.setTolls(trip.getTolls());
        enrichedTrip.setExtras(trip.getExtras());
        enrichedTrip.setTripTotal(trip.getTripTotal());
        enrichedTrip.setPaymentType(trip.getPaymentType());
        enrichedTrip.setCompany(trip.getCompany());
        return enrichedTrip;
    }

}
