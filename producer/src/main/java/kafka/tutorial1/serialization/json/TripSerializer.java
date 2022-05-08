package kafka.tutorial1.serialization.json;

import com.google.gson.Gson;
import kafka.tutorial1.serialization.Trip;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class TripSerializer implements Serializer<Trip> {

    private Gson gson = new Gson();

    @Override
    public byte[] serialize(String topic, Trip trip) {
        if (trip == null) return null;
        return gson.toJson(trip).getBytes(StandardCharsets.UTF_8);
    }
}
