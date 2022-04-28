// SparkStreamRateSource.scala
// spark-submit kafkaspark_2.11-0.1.0-SNAPSHOT.jar --class example.SparkStreamRateSource
// OR
// spark-shell -i SparkStreamRateSource.scala
package example

import org.apache.spark.sql.{SparkSession}
import org.apache.spark.sql.functions._

object SparkStreamRateSource {
  def main_r(args: Array[String]): Unit = {

    // // If running with spark-submit.
    // // Create Spark Session
    // val sparkSession = SparkSession
    //   .builder()
    //   .master("local")
    //   .appName("Rate Source")
    //   .getOrCreate()
    val sparkSession = SparkSession.builder.appName("Rate Source").config("spark.master", "local[*]").getOrCreate()

    // // If running with spark-shell:
    // val sparkSession = spark

    // Set Spark logging level to ERROR to avoid various other logs on console.
    sparkSession.sparkContext.setLogLevel("ERROR")

    // Create streaming DataFrame.
    val initDF = (sparkSession.readStream
      .format("rate")
      .option("rowsPerSecond", 1)
      .load())

    println("Streaming DataFrame : " + initDF.isStreaming)

    // Basic transformation: generate another column result by just adding 1 to column value
    val resultDF = initDF
      .withColumn("result", col("value") + lit(1))

    // Output to console.
    resultDF.writeStream
      .outputMode("append")
      .option("truncate", false)
      .format("console")
      .start()
      .awaitTermination()

    // Use df.writeStream.option(“numRows”,50) to display more than 20 rows (which is the default).
  }
}
