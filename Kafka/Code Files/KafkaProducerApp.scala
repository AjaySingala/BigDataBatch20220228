// KafkaProducerApp.scala
// $KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic text_topic
// $KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper sandbox-hdp.hortonworks.com:2181 --replication-factor 1 --partitions 1 --topic text_topic
// spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.3.4 producer.jar --class example.KafkaProducerApp

package example

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object KafkaProducerApp {
  def main_kp(args: Array[String]): Unit = {
    val props: Properties = new Properties()
    //props.put("bootstrap.servers","localhost:9092")
    props.put("bootstrap.servers", "sandbox-hdp.hortonworks.com:6667")
    props.put(
      "key.serializer",
      "org.apache.kafka.common.serialization.StringSerializer"
    )
    props.put(
      "value.serializer",
      "org.apache.kafka.common.serialization.StringSerializer"
    )
    // The acks config controls the criteria under which requests are considered complete.
    // The "all" setting we have specified will result in blocking on the full commit of the record,
    // the slowest but most durable setting.
    props.put("acks", "all")
    val producer = new KafkaProducer[String, String](props)
    val topic = "text_topic"
    try {
      for (i <- 0 to 15) {
        val record = new ProducerRecord[String, String](
          topic,
          i.toString,
          "This is item #" + i
        )
        val metadata = producer.send(record)
        printf(
          s"sent record(key=%s value=%s) " +
            "meta(partition=%d, offset=%d)\n",
          record.key(),
          record.value(),
          metadata.get().partition(),
          metadata.get().offset()
        )
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      producer.close()
    }
  }
}
