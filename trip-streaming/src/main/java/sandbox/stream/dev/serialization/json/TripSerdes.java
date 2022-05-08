package sandbox.stream.dev.serialization.json;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import sandbox.stream.dev.serialization.Trip;

public class TripSerdes implements Serde<Trip> {
    @Override
    public Serializer<Trip> serializer() {
        return new TripSerializer();
    }

    @Override
    public Deserializer<Trip> deserializer() {
        return new TripDeserializer();
    }
}
