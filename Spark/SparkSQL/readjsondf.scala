// readjsondf.scala
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.FromJsonFile
package example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

object FromJsonFile {
  def main_FromJsonFile(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[3]")
      .appName("AjaySingala")
      .getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    val sc = spark.sparkContext

    //read json file into dataframe
    val df = spark.read.json("file:///home/maria_dev/SparkSamples/resources/employee.json")
    println("df.printSchema() of json file read into dataframe...")
    df.printSchema()
    println("Printing json file in dataframe...")
    df.show(false)

//     //// Read from local.
//     // val df = spark.read.json("file:///home/maria_dev/SparkSamples/resources/employee.json")

    //read multiline json file
    val multiline_df = spark.read.option("multiline", "true").json("file:///home/maria_dev/SparkSamples/resources/employee_m.json")
    println("read multiline json file...")
    multiline_df.show(false)

    //read multiple files
    val df2 = spark.read.json("file:///home/maria_dev/SparkSamples/resources/e1.json", "file:///home/maria_dev/SparkSamples/resources/e2.json")
    println("read multiple files...")
    df2.show(false)

//     // //read files from a folder.
//     // val df2 = spark.read.json("somefolder")
//     // df2.show(false)

//     //Define custom schema
//     println("\nCreating dataframe for zipcodes.json with custom schema...")
//     val schema = new StructType()
// .add("RecordNumber", IntegerType, true)
// .add("Zipcode", IntegerType, true)
// .add("ZipCodeType", StringType, true)
// .add("City", StringType, true)
// .add("State", StringType, true)
// .add("LocationType", StringType, true)
// .add("Lat", DoubleType, true)
// .add("Long", DoubleType, true)
// .add("Xaxis", IntegerType, true)
// .add("Yaxis", DoubleType, true)
// .add("Zaxis", DoubleType, true)
// .add("WorldRegion", StringType, true)
// .add("Country", StringType, true)
// .add("LocationText", StringType, true)
// .add("Location", StringType, true)
// .add("Decommisioned", BooleanType, true)
// // .add("TaxReturnsFiled", StringType, true)
// // .add("EstimatedPopulation", IntegerType, true)
// // .add("TotalWages", IntegerType, true)
// // .add("Notes", StringType, true)

//     val df_with_schema = spark.read.schema(schema).json("file:///home/maria_dev/SparkSamples/resources/zipcodes.json")

//     println("zipcodes.json with custom schema...")
//     df_with_schema.printSchema()
//     println("Printing zipcodes.json data...")
//     df_with_schema.show(false)

    // println("Dropping temp view zipcodes...")
    // spark.catalog.dropTempView("zipcodes")

     println("Creating temp view zipcodes...")
    // Read JSON file using Spark SQL.
    spark.sqlContext.sql(
      "CREATE TEMPORARY VIEW zipcodes USING json OPTIONS" + " (path 'file:///home/maria_dev/SparkSamples/resources/zipcodes.json')"
    )
    println("Querying temp view zipcodes...")
    spark.sqlContext.sql("select * from zipcodes").show(false)
    println("Querying temp view zipcodes for a specific record...")
    spark.sqlContext.sql("select * from zipcodes where RecordNumber = 61392").show(false)

    // Write Spark DataFrame to JSON file.
    // Will create part* files in the given folder.
    // Delete the given folder rm -rf /tmp/spark_output/zipcodes
    println("Writing to /tmp/spark_output/zipcodes...")
    df2.write.json("file:///tmp/spark_output/zipcodes")
  }
}
