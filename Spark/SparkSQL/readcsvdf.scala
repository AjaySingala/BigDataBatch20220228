// readcsvdf.scala or readcsv.scala
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.FromCSVFile

package example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

object FromCSVFile {
  def main_FromCSVFile(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[3]")
      .appName("AjaySingala")
      .getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")

    val sc = spark.sparkContext

// Read CSV file into DataFrame.
    println("\nRead csv file into a DF...")
    val df1 = spark.read.csv(
      "file:///home/maria_dev/SparkSamples/resources/zipcodes.csv"
    )
    df1.printSchema()

// Treat the first row as header. Use .load() or .csv()
    println("\nRead csv file into a DF specify 1st line as header and infer the schema...")
    val df2 = spark.read
      .option("header", true)
      .option("inferSchema", true)
      .csv("file:///home/maria_dev/SparkSamples/resources/zipcodes.csv")
    df2.printSchema()
    df2.show()

// // // Read multiple CSV files.
// // val df = spark.read.csv("path1,path2,path3")

// // // Read all CSV files in a directory.
// // val df3 = spark.read.csv("Folder path")

// Read CSV file with delimeter.
    println("\nRead csv file into a DF specifying a delimiter...")
    val df4 = spark.read
      .options(Map("delimiter" -> ","))
      .csv("file:///home/maria_dev/SparkSamples/resources/zipcodes.csv")
    df4.printSchema()
    df4.show(false)

// Automatically infers column types based on the data.
// Default value set to this option is false
    println("\nSpecifying options as K-V pairs...")
    val df5 = spark.read
      .options(Map("header" -> "true", "inferSchema" -> "true", "delimiter" -> ","))
      .csv("file:///home/maria_dev/SparkSamples/resources/zipcodes.csv")
    df5.printSchema()

// Reading CSV files with a user-specified custom schema.
    println("\nDefine custom schema...")
    val schema = new StructType()
      .add("RecordNumber", IntegerType, true)
      .add("Zipcode", IntegerType, true)
      .add("ZipCodeType", StringType, true)
      .add("City", StringType, true)
      .add("State", StringType, true)
      .add("LocationType", StringType, true)
      .add("Lat", DoubleType, true)
      .add("Long", DoubleType, true)
      .add("Xaxis", DoubleType, true)
      .add("Yaxis", DoubleType, true)
      .add("Zaxis", DoubleType, true)
      .add("WorldRegion", StringType, true)
      .add("Country", StringType, true)
      .add("LocationText", StringType, true)
      .add("Location", StringType, true)
      .add("Decommisioned", BooleanType, true)
      .add("TaxReturnsFiled", StringType, true)
      .add("EstimatedPopulation", IntegerType, true)
      .add("TotalWages", IntegerType, true)
      .add("Notes", StringType, true)

    println("Read csv file into a DF with custom schema...")
    val df_with_schema = spark.read
      .format("csv")
      .option("header", true)
      .schema(schema)
      .load("file:///home/maria_dev/SparkSamples/resources/zipcodes.csv")
    df_with_schema.printSchema()
    df_with_schema.show(false)

// Write Spark DataFrame to CSV file.
    df_with_schema.write
      .option("header", "true")
      .csv("file:///tmp/spark_output/zipcodes")

// // Write mode samples:
// // df2.write.mode(SaveMode.Append).csv("/tmp/spark_output/zipcodes")
// // df2.write.mode(SaveMode.Overwrite).csv("/tmp/spark_output/zipcodes")
// // df2.write.mode(SaveMode.Ignore).csv("/tmp/spark_output/zipcodes")
// // df2.write.mode(SaveMode.ErrorIfExists).csv("/tmp/spark_output/zipcodes")

    df4.write.mode("append").csv("file:///tmp/spark_output/zipcodes")
   }
}
