// PartitionerCustom.scala
// https://dzone.com/articles/Custom-partitioner-in-kafka-using-scala-lets-take
package example

import java.util
import org.apache.kafka.common.record.InvalidRecordException
import org.apache.kafka.common.utils.Utils
import org.apache.kafka.clients.producer.Partitioner
import org.apache.kafka.common.Cluster

class PartitionerCustom extends Partitioner {

  val departmentName = "IT"

  def toPositive(number: Int) : Int = {
    println(s"Inside toPositive with $number...")
    var ret = (number & 0x7FFFFFFF)
    println(ret)
    ret
  }
  
  override def configure(configs: util.Map[String, _]): Unit = {}
  override def partition(topic: String,key: Any, keyBytes: Array[Byte], value: Any,valueBytes: Array[Byte],cluster: Cluster): Int = {
    val partitions = cluster.partitionsForTopic(topic)
    val numPartitions = if(partitions.size == 0) 1 else partitions.size 
    var it = Math.abs(numPartitions * 0.4).asInstanceOf[Int]
    it = if(it == 0) 2 else it
    println(s"numPartitions: $numPartitions")
    println(s"it: $it")

    if ((keyBytes == null) || (!key.isInstanceOf[String]))
      throw new InvalidRecordException("All messages must have department name as key")

    println(s"keyBytes: $keyBytes")
    println(s"murmur: ${Utils.murmur2(keyBytes)}")
    println(s"toPositive: ${toPositive(Utils.murmur2(keyBytes)) % it}")
    if (key.asInstanceOf[String].startsWith(departmentName)) {
      val p = toPositive(Utils.murmur2(keyBytes)) % it
         p
    } else {
      println("Inside else part...")
      println(s"(numPartitions - it): ${(numPartitions - it)}")
      val p = toPositive(Utils.murmur2(keyBytes)) % (numPartitions - it) + it
           p
    }
  }

  override def close(): Unit = {}
}
