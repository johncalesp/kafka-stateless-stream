package sandbox.stream.dev.serialization.json;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serializer;
import sandbox.stream.dev.serialization.Trip;

import java.nio.charset.StandardCharsets;

public class TripSerializer implements Serializer<Trip> {
    private Gson gson = new Gson();

    @Override
    public byte[] serialize(String topic, Trip trip) {
        if (trip == null) return null;
        return gson.toJson(trip).getBytes(StandardCharsets.UTF_8);
    }
}
