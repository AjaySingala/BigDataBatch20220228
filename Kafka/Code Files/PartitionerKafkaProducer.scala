// PartitionerKafkaProducer.scala
// https://dzone.com/articles/Custom-partitioner-in-kafka-using-scala-lets-take
// $KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic department
// $KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper sandbox-hdp.hortonworks.com:2181 --replication-factor 1 --partitions 1 --topic department
// spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.3.0 kafkaspark_2.11-0.1.0-SNAPSHOT.jar --class example.PartitionerKafkaConsumer

package example

import java.util.Properties
import org.apache.kafka.clients.producer._

object PartitionerKafkaProducer {
  def main_pkp(args: Array[String]): Unit = {
    val props = new Properties()
    val topicName = "department"

    //props.put("bootstrap.servers", "localhost:9092")
    props.put("bootstrap.servers", "sandbox-hdp.hortonworks.com:6667")
    props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer")
    props.put("partitioner.class", "example.PartitionerCustom")

    val producer = new KafkaProducer[String, String](props)

    try {
      for (i <- 0 to 5) {
        val record = new ProducerRecord[String, String](topicName,"IT" + i,"My Site is ajaysingala.com " + i)
        producer.send(record)
      }

      for (i <- 0 to 5) {
        val record = new ProducerRecord[String, String](topicName,"COMP" + i,"My Site is ajaysingala.com " + i)
        producer.send(record)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      producer.close()
    }
  }
}
