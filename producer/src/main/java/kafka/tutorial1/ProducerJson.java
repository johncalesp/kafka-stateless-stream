package kafka.tutorial1;


import kafka.tutorial1.serialization.Trip;
import kafka.tutorial1.serialization.json.TripSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

public class ProducerJson {
    public static void main(String[] args) throws IOException {

        // logger
        Logger logger = LoggerFactory.getLogger(ProducerJson.class);

        // kafka server
        String bootstrapServers = "192.168.80.134:9094";

        // create producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TripSerializer.class.getName());

        // create the producer
        KafkaProducer<String, Trip> producer = new KafkaProducer<String, Trip>(properties);

        // initialize record
        ProducerRecord<String, Trip> record;

        // set topic
        String topic = "trips";

        // import json parser
        JSONParser jsonParser  = new JSONParser();

        // File path
        String path = System.getProperty("user.dir") + "\\kafka-basics\\data\\taxi_trips.json";

        try {
            Object obj = jsonParser.parse(new FileReader(path));
            JSONArray trips = (JSONArray) obj;

            Iterator<JSONObject> tripsIterator = trips.iterator();
            while (tripsIterator.hasNext()) {

                JSONObject jsonTrip = tripsIterator.next();
                Trip trip = new Trip();

                trip.setTripSeconds(Integer.parseInt(jsonTrip.get("trip_seconds") == null ? "0": jsonTrip.get("trip_seconds").toString()));
                trip.setTripMiles(Float.parseFloat(jsonTrip.get("trip_miles") == null ? "0": jsonTrip.get("trip_miles").toString()));
                trip.setFare(Float.parseFloat(jsonTrip.get("fare") == null ? "0": jsonTrip.get("fare").toString()));
                trip.setTips(Float.parseFloat(jsonTrip.get("tips") == null ? "0": jsonTrip.get("tips").toString()));
                trip.setTolls(Float.parseFloat(jsonTrip.get("tolls") == null ? "0": jsonTrip.get("tolls").toString()));
                trip.setExtras(Float.parseFloat(jsonTrip.get("extras") == null ? "0": jsonTrip.get("extras").toString()));
                trip.setTripTotal(Float.parseFloat(jsonTrip.get("trip_total") == null ? "0": jsonTrip.get("trip_total").toString()));
                trip.setPaymentType((String) jsonTrip.get("payment_type"));
                trip.setCompany((String) jsonTrip.get("company"));

                System.out.println(trip.getCompany().isEmpty());

                record = new ProducerRecord<>(topic,trip);

                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if(e==null){
                            logger.info("Received new metadata. \n" +
                                    "Topic: " + recordMetadata.topic() + "\n" +
                                    "Partition: " + recordMetadata.partition() + "\n" +
                                    "Offset: " + recordMetadata.offset() + "\n" +
                                    "Timestamp: " + recordMetadata.timestamp());
                        }else{
                            logger.error("Error generating new record",e);
                        }
                    }
                });

                Thread.sleep(2000);
            }

            // flush data
            producer.flush();

            // close producer
            producer.close();

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
