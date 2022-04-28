// SparkStreamDir.scala
// Run the program, then copy files from SparkSamples/data/folder_streaming to /tmp/stream_folder (a few at a time to see results).
// spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.3.4 ./kafkaspark_2.11-0.1.0-SNAPSHOT.jar --class example.SparkStreamingFromDirectory

package example
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object SparkStreamingFromDirectory {

  def main_d(args: Array[String]): Unit = {

    val spark:SparkSession = SparkSession.builder()
      .master("local[3]")
      .appName("SparkStreamingFromDirectory")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val schema = StructType(
      List(
        StructField("RecordNumber", IntegerType, true),
        StructField("Zipcode", StringType, true),
        StructField("ZipCodeType", StringType, true),
        StructField("City", StringType, true),
        StructField("State", StringType, true),
        StructField("LocationType", StringType, true),
        StructField("Lat", StringType, true),
        StructField("Long", StringType, true),
        StructField("Xaxis", StringType, true),
        StructField("Yaxis", StringType, true),
        StructField("Zaxis", StringType, true),
        StructField("WorldRegion", StringType, true),
        StructField("Country", StringType, true),
        StructField("LocationText", StringType, true),
        StructField("Location", StringType, true),
        StructField("Decommisioned", StringType, true)
      )
    )

    val df = spark.readStream
      .schema(schema)
      .json("file:///tmp/stream_folder")
    df.printSchema()

    val groupDF = df.select("Zipcode")
        .groupBy("Zipcode").count()
    groupDF.printSchema()

    groupDF.writeStream
      .format("console")
      .outputMode("complete")
      .start()
      .awaitTermination()
  }
}
