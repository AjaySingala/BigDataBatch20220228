// SparkStreamKafkaSource.scala
// spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.3.0 ~/kafkaspark_2.11-0.1.0-SNAPSHOT.jar --class example.SparkStreamKafkaSource
package example

// On Custom VM:
// kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic testscala
// kafka-console-producer.sh --broker-list localhost:9092 --topic testscala
// kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic testscala

// On Hortonworks VM:
// $KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper sandbox-hdp.hortonworks.com:2181  --replication-factor 1 --partitions 1 --topic testscala
// $KAFKA_HOME/bin/kafka-console-producer.sh --broker-list sandbox-hdp.hortonworks.com:6667  --topic testscala
// $KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server sandbox-hdp.hortonworks.com:6667  --topic testscala

// Import Libraries
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._

object SparkStreamKafkaSource {
  def main_ks(args: Array[String]): Unit = {
    // Create Spark Session
    //val sparkSession = SparkSession
    //  .builder()
    //  .master("local")
    //  .appName("Kafka Source")
    //  .getOrCreate()

    val spark = SparkSession.builder.appName("Kafka Source").config("spark.master", "local[*]").getOrCreate()

    import spark.implicits._
    // Set Spark logging level to ERROR.
    spark.sparkContext.setLogLevel("ERROR")
    // Create streaming DF.
    val initDf = spark.readStream
      .format("kafka")
      //.option("kafka.bootstrap.servers", "localhost:9092")
      .option("kafka.bootstrap.servers", "sandbox-hdp.hortonworks.com:6667")
      .option("subscribe", "testscala")
      .load()
      .select(col("value").cast("string"))

    // Transformation.
    val wordCount = initDf
      .select(explode(split(col("value"), " ")).alias("words"))
      .groupBy("words")
      .count()

    // Output to console.
    wordCount.writeStream
      .outputMode("update")
      .format("console")
      .start()
      .awaitTermination()

    // // Output sink — Kafka.
    // // Kafka expects data in a column name value, so let's concatenate all the column values and store them in one value column.
    // val resultDf = initDf.withColumn("value", 
    //   concat_ws("|",$"Name",$"Date",$"High",$"Low",$"Open",$"Close"))

    // resultDf
    //   .writeStream
    //   .format("kafka")
    //   .option("kafka.bootstrap.servers", "localhost:9092")
    //   .option("topic", "testConsumer")
    //   .option("checkpointLocation", "file:///home/maria_dev/output/checkpoint/kafka_checkpoint")
    //   .start()
    //   .awaitTermination()
  }
}
