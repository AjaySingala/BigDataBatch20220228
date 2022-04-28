// KafkaConsumerSubscribeApp.scala
// spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.3.4 consumer.jar --class example.KafkaConsumerSubscribeApp

package example

import java.util.{Collections, Properties}
import java.util.regex.Pattern
import org.apache.kafka.clients.consumer.KafkaConsumer
import scala.collection.JavaConverters._

object KafkaConsumerSubscribeApp {
  def main(args: Array[String]): Unit = {

    val props: Properties = new Properties()
    props.put("group.id", "test")
    // props.put("bootstrap.servers","localhost:9092")
    props.put("bootstrap.servers", "sandbox-hdp.hortonworks.com:6667")
    props.put(
      "key.deserializer",
      "org.apache.kafka.common.serialization.StringDeserializer"
    )
    props.put(
      "value.deserializer",
      "org.apache.kafka.common.serialization.StringDeserializer"
    )
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    val consumer = new KafkaConsumer(props)
    val topics = List("text_topic")
    try {
      consumer.subscribe(topics.asJava)
      while (true) {
        val records = consumer.poll(10)
        for (record <- records.asScala) {
          println(
            "Topic: " + record.topic() +
              ",Key: " + record.key() +
              ",Value: " + record.value() +
              ", Offset: " + record.offset() +
              ", Partition: " + record.partition()
          )
        }
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      consumer.close()
    }
  }
}
