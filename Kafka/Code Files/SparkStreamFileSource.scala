// SparkStreamFileSource.scala
// spark-submit kafkaspark_2.11-0.1.0-SNAPSHOT.jar --class example.SparkStreamFileSource
// spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.3.4 kafkaspark_2.11-0.1.0-SNAPSHOT.jar --class example.SparkStreamFileSource
// OR
// spark-shell -i SparkStreamFileSource.scala
package example

// Copy these files in the sequence below from data/stocks to data/stream to simulate streaming.
// 1.	MSFT_2017.csv
// 2.	GOOGL_2017.csv
// 3.	MSFT_2016.csv
// 4.	AMZN_2017.csv

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Column
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Column, Dataset, Row, SparkSession}

object SparkStreamFileSource {
  def main_fs(args: Array[String]): Unit = {

    // Create Spark Session
    // val spark = SparkSession
    //   .builder()
    //   .master("local")
    //   .appName("File Source")
    //   .getOrCreate()
    val spark = SparkSession.builder
      .appName("File Source")
      .config("spark.master", "local[*]")
      .getOrCreate()

    // Set Spark logging level to ERROR.
    spark.sparkContext.setLogLevel("ERROR")

    // Define Schema
    // Check schema with data file first.
    println("Defining the schema...")
    val schema = StructType(
      List(
        StructField("Date", StringType, true),
        StructField("Open", DoubleType, true),
        StructField("High", DoubleType, true),
        StructField("Low", DoubleType, true),
        StructField("Close", DoubleType, true),
        //StructField("Adjusted Close", DoubleType, true),
        StructField("Volume", DoubleType, true),
        StructField("Name", StringType, true)
      )
    )

    // // Extract the Name of the stock from the file name.
    // def getFileName: Column = {
    //   val file_name = reverse(split(input_file_name(), "/")).getItem(0)
    //   split(file_name, "_").getItem(0)
    // }

    // val get_cus_val = udf((filePath: String) => {
    //   val filename = filePath.split("/").last
    //   filename.split("_")(0)
    // })

    // Create Streaming DataFrame by reading data from File Source.
    // This will read maximum of 2 files per mini batch. However, it can read less than 2 files.
    println("Creating Streaming DF by reading file source...")
    val initDF = (
      spark.readStream
        .format("csv")    // supports csv, json, orc, parquet.
        .option("maxFilesPerTrigger", 2)
        .option("header", true)
        .option("path", "data/stream")      // hdfs://user/maria_dev/data/stream
        .schema(schema)
        .load()
        //.withColumn("Name",getFileName)
        //.withColumn("name", get_cus_val(input_file_name))
    )
    initDF.printSchema()
    
    // Uncomment this line and comment the below view and query statements.
    // Basic Transformation.
    println("Some basic transformation...")
    val stockDf = initDF
      .groupBy(col("Name"), year(col("Date")).as("Year"))
      .agg(max("High").as("Max"))
    stockDf.printSchema()

    // // Comment above line to use this. And uncomment these.
    // // Register DataFrame as view.
    // initDF.createOrReplaceTempView("stockView")

    // // Run SQL Query
    // val query =
    //   """select year(Date) as Year, Name, max(High) as Max from stockView group by Name, Year"""
    // val stockDf = spark.sql(query)

    // // Output to Console
    // // Try "update" and "complete" mode.
    // println("Output to console...")
    // stockDf.writeStream
    //   .outputMode("update") 
    //   .option("truncate", false)
    //   .option("numRows", 3)
    //   .format("console")
    //   .start()
    //   .awaitTermination()

    // // Output to file sink: CSV.
    // // File sink only supports append output mode.
    // // Delete the folder first.
    // println("Output to csv...")
    // val resultDf = initDF.select("Name", "Date", "Open", "Close")
    // resultDf
    //   .writeStream
    //   .outputMode("append") // Filesink only support Append mode.
    //   .format("csv") // supports these formats : csv, json, orc, parquet
    //   .option("path", "file:///home/maria_dev/output/filesink_output")
    //   .option("header", true)
    //   .option("checkpointLocation", "file:///home/maria_dev/output/checkpoint/filesink_checkpoint")
    //   .start()
    //   .awaitTermination()
    //
    // // Output to file sink: JSON
    // val resultDf = initDF.select("Name", "Date", "Open", "Close")
    // println("Output to json...")
    // resultDf
    //   .writeStream
    //   .outputMode("append") // Filesink only support Append mode.
    //   .format("json") // supports these formats : csv, json, orc, parquet
    //   .option("path", "file:///home/maria_dev/output/filesink_output")
    //   .option("checkpointLocation", "file:///home/maria_dev/output/checkpoint/filesink_checkpoint")
    //   .start()
    //   .awaitTermination()
    //
    // // Output to Kafka sink.
    // // $KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic testConsumer1
    // // Run a consumer:
    // // $KAFKA_HOME/bin/kafka-console-consumer.sh  --topic testConsumer1 --bootstrap-server sandbox-hdp.hortonworks.com:6667 --from-beginning
    // println("Output to Kafka Topic 'testConsumer1'...")
    // val resultDf = initDF.withColumn("value", 
    //     concat_ws("|",col("Name"),col("Date"),col("High"),col("Low"),col("Open"),col("Close"))
    //   )
    //   //.withColumn("key", col("Name"))
    //   // .drop("Name")
    //   // .drop("Date")
    //   // .drop("high")
    //   // .drop("Low")
    //   // .drop("Open")
    //   // .drop("Close")
    //   // .drop("Volume")
    // resultDf.printSchema()
    
    // // println("Output to console...")
    // // resultDf.selectExpr("CAST(Name AS STRING) AS key", "CAST(value AS STRING) AS value")
    // //   .writeStream
    // //   .outputMode("update") 
    // //   .option("truncate", false)
    // //   .option("numRows", 3)
    // //   .format("console")
    // //   .start()
    // //   .awaitTermination()
    
    // resultDf.selectExpr("CAST(Name AS STRING) AS key", "CAST(value AS STRING) AS value")
    //   .writeStream
    //   .format("kafka")
    //   .outputMode("append")
    //   //.option("kafka.bootstrap.servers", "localhost:9092")
    //   .option("kafka.bootstrap.servers", "sandbox-hdp.hortonworks.com:6667")
    //   .option("topic", "testConsumer1")
    //   .option("checkpointLocation", "file:///home/maria_dev/output/checkpoint/filesink_checkpoint")
    //   .start()
    //   .awaitTermination()

    //
    // // Output to foreachBatch sink.
    // // Does not work on Hortonworks VM. works with Spark 2.4.0+
    // println("Output to foreachBatch sink...")
    // val resultDf = initDF.select("Name", "Date", "open")
    // def saveToMySql = (df: Dataset[Row], batchId: Long) => {
    //   val url = """jdbc:mysql://localhost:3306/training"""
    //    
    //   df
    //       .withColumn("batchId", lit(batchId))
    //       .write.format("jdbc")
    //       .option("url", url)
    //       .option("dbtable", "test")
    //       .option("user", "root")
    //       .option("password", "<mySQL_PASSWORD>")
    //       .mode("append")
    //       .save()
    // }

    // // Use the savetoMySQL() function to save our streaming DataFrame to MySQL.
    // resultDf
    //   .writeStream
    //   .outputMode("append")
    //   .foreachBatch(saveToMySql)
    //   .start()
    //   .awaitTermination()
    
    // // Outpit sink - foreach.
    // import org.apache.spark.sql.ForeachWriter
    // println("Output to foreach sink...")
    // val resultDf = initDF.select("Name", "Date", "open")
    // val customWriter = new ForeachWriter[Row] {
    
    //   override def open(partitionId: Long, version: Long) = true

    //   override def process(value: Row) = {
    //     println("Name : "  + value.getAs("Name"))
    //     println("Open : "  + value.getAs("Open"))
    //     println("Size : " + value.size)
    //     println("Values as Seq : " + value.toSeq)
    //   }
    
    //   override def close(errorOrNull: Throwable) = {}
    // }
    //
    // // Use the ForeachWriter instance defined above to write data using the foreach sink.
    // initDF
    //   .writeStream
    //   .outputMode("append")
    //   .foreach(customWriter)
    //   .start()
    //   .awaitTermination()
  }
}
