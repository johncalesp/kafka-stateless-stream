package sandbox.stream.dev.serialization.avro;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import sandbox.stream.dev.model.EntityTrip;
import java.util.Collections;
import java.util.Map;


public class AvroSerdes {
    public static Serde<EntityTrip> EntityTrip(String url, boolean isKey) {
        Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", url);
        Serde<EntityTrip> serde = new SpecificAvroSerde<>();
        serde.configure(serdeConfig, isKey);
        return serde;
    }
}
