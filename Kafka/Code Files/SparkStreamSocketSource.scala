// SparkStreamSocketSource.scala
// nc -lk 9999
// spark-submit kafkaspark_2.11-0.1.0-SNAPSHOT.jar --class example.SparkStreamSocketSource
// OR
// spark-shell -i SparkStreamSocketSource.scala
package example

import org.apache.spark.sql.{SparkSession}
import org.apache.spark.sql.functions._

object SparkStreamSocketSource {
  def main_s(args: Array[String]): Unit = {

    //// Create Spark Session
    // val spark = SparkSession
    //   .builder()
    //   .master("local")
    //   .appName("Socket Source")
    //   .getOrCreate()
    val spark = SparkSession.builder.appName("Socket Source").config("spark.master", "local[*]").getOrCreate()

    // Set Spark logging level to ERROR.
    spark.sparkContext.setLogLevel("ERROR")

    // Create Streaming DataFrame
    // Define host and port number to Listen.
    val host = "127.0.0.1"
    val port = "9999"

    // Create Streaming DataFrame by reading data from socket.
    val initDF = (spark.readStream
      .format("socket")
      .option("host", host)
      .option("port", port)
      .load())

    // Check if DataFrame is streaming or Not.
    println("Streaming DataFrame : " + initDF.isStreaming)

    // Perform word count on streaming DataFrame
    val wordCount = (initDF
      .select(explode(split(col("value"), " ")).alias("words"))
      .groupBy("words")
      .count())

    // Print Schema of DataFrame
    println("Schema of DataFame wordCount.")
    println(wordCount.printSchema())

    // Output to Console
    wordCount.writeStream
      .outputMode("update") // Try "update" and "complete" mode.
      .option("truncate", false)
      .format("console")
      .start()
      .awaitTermination()

  }
}
