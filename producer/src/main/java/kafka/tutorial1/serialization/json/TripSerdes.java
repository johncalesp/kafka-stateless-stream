package kafka.tutorial1.serialization.json;

import kafka.tutorial1.serialization.Trip;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class TripSerdes  implements Serde<Trip> {
    @Override
    public Serializer<Trip> serializer() {
        return new TripSerializer();
    }

    @Override
    public Deserializer<Trip> deserializer() {
        return new TripDeserializer();
    }
}
