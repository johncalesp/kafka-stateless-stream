package sandbox.stream.dev;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.util.Properties;

public class App {
    public static void main(String[] args) {
        Topology topology = TripTopology.build();

        // server
        String server = "localhost:29092";

        // set the required properties for running Kafka Streams
        Properties properties = new Properties();
        properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "development");
        properties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,server);

        // build the topology and start streaming!
        KafkaStreams streams = new KafkaStreams(topology, properties);

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        System.out.println("Starting Trips streams");
        streams.start();
    }
}
