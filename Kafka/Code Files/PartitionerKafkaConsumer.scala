// PartitionerKafkaConsumer.scala
// https://dzone.com/articles/Custom-partitioner-in-kafka-using-scala-lets-take
// $KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic department
// $KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper sandbox-hdp.hortonworks.com:2181 --replication-factor 1 --partitions 1 --topic department
// spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.3.0 kafkaspark_2.11-0.1.0-SNAPSHOT.jar --class example.PartitionerKafkaConsumer

package example

import java.util
import java.util.Properties
//import scala.jdk.CollectionConverters._
import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.KafkaConsumer

object PartitionerKafkaConsumer {
  def main_PartitionerKafkaConsumer(args: Array[String]): Unit = {
    val props: Properties = new Properties()
    val topicName = "department"

    props.put("group.id", "test")
    //props.put("bootstrap.servers", "localhost:9092")
    props.put("bootstrap.servers", "sandbox-hdp.hortonworks.com:6667")
    props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer")

    val consumer = new KafkaConsumer(props)

    try {
      consumer.subscribe(util.Arrays.asList(topicName))
      while (true) {
        val records = consumer.poll(10)
        for (record <- records.asScala) {
          println(s"record: $record")
          println("Topic: " + record.topic() + ", Offset: " + record.offset() +", Partition: " + record.partition())
        }
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      consumer.close()
    }
  }
}
