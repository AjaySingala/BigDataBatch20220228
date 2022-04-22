// PartitionerPersonal.scala
// https://stackoverflow.com/questions/53098659/custom-partitioner-in-scala-for-rdd-is-not-working-as-expected
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.PartitionerPersonal

package example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

class Mypartitioner2( num:Int) extends org.apache.spark.Partitioner {
    override def numPartitions: Int = num

    override def getPartition(key: Any): Int = {
        if(key.toString.size == 3){
                2
        }
        else if(key.toString.size == 2){
                1
        }
        else {
                0
        }
    }
 }

object PartitionerPersonal {
    def main_PartitionerPersonal(args: Array[String]): Unit = {
        val spark = SparkSession
        .builder()
        .appName("Spark Personal Partitioner")
        .getOrCreate()
        
        spark.sparkContext.setLogLevel("ERROR")
        val sc = spark.sparkContext

        val df =sc.parallelize(Array(
            ("aaa",2),("aaa",3),("aaa",1),("aaa",0),("aaa",4),
            ("aa",2),("aa",3),("aa",1),("aa",0),("aa",4),
            ("a",2),("a",3),("a",1),("a",0),("a",4) )
        )
        df.partitionBy(new Mypartitioner2(3)).saveAsTextFile("/tmp/partitions/personalpartitioner")
   }
}