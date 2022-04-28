// producer.scala
// $KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic quick_start
// $KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper sandbox-hdp.hortonworks.com:2181 --replication-factor 1 --partitions 1 --topic quick_start
package example

import java.util.Properties
import org.apache.kafka.clients.producer._

object Producer {

  def main_p(args: Array[String]): Unit = {
    writeToKafka("quick-start")
  }

  def writeToKafka(topic: String): Unit = {
    val props = new Properties()
    //props.put("bootstrap.servers", "localhost:9094")
    props.put("bootstrap.servers", "sandbox-hdp.hortonworks.com:6667")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)
    val record = new ProducerRecord[String, String](topic, "key", "value")
    producer.send(record)
    producer.close()
  }
}
