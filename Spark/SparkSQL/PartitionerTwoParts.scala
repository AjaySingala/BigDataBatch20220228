// PartitionerTwoParts.scala
// https://gist.github.com/girisandeep/f90e456da6f2381f9c86e8e6bc4e8260
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.PartitionerTwoParts

package example

import org.apache.spark.sql.SparkSession
import org.apache.spark.Partitioner

class TwoPartsPartitioner(override val numPartitions: Int) extends Partitioner {
    def getPartition(key: Any): Int = key match {
        case s: String => {
            // println(s"key = $key")
            // println(s"s(0)= ${s(0)}")
            if (s(0).toUpper > 'J') 1 else 0
        }   
    }
}
object PartitionerTwoParts {
    def main_PartitionerTwoParts(args: Array[String]): Unit = {
        val spark = SparkSession
        .builder()
        .appName("Spark Two Parts Partitioner")
        .getOrCreate()
        
        spark.sparkContext.setLogLevel("ERROR")
        val sc = spark.sparkContext

        var x = sc.parallelize(Array(("sandy",1),("giri",1),("abbey",1),("sean",1),("jude",1)), 3)
        // glom() transforms each partition into a tuple (immutabe list) of elements.
        // It creates an RDD of tuples. One tuple per partition.
        // glom does not shuffle the data across partitions.
        x.glom().collect()

        //Array(Array((sandy,1)), Array((giri,1), (abbey,1)), Array((sean,1), (jude,1)))
        //[ [(sandy,1)], [(giri,1), (abbey,1)], [(sean,1), (jude,1)] ]

        var y = x.partitionBy(new TwoPartsPartitioner(2))
        println(s"# of partitions: ${y.partitions.size}")

        println("Data of partition #0...")
        y.mapPartitionsWithIndex( (index, it) =>it.toList.map(x => if (index == 0) {println(x)}).iterator).collect()

        println("Data of partition #1...")
        y.mapPartitionsWithIndex( (index, it) =>it.toList.map(x => if (index == 1) {println(x)}).iterator).collect()

        println("Data of all partitions...")
        y.glom().collect().show()
        //Array(Array((giri,1), (abbey,1), (jude,1)), Array((sandy,1), (sean,1)))
        //[ [(giri,1), (abbey,1), (jude,1)], [(sandy,1), (sean,1)] ]
    }
}
