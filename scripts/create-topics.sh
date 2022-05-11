echo "Waiting for Kafka to come online..."

cub kafka-ready -b kafka:9092 1 20

# create the tweets topic
kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic trips \
  --replication-factor 1 \
  --partitions 4 \
  --create

# create the crypto-sentiment topic
kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic trips-enriched \
  --replication-factor 1 \
  --partitions 4 \
  --create

sleep infinity
