// dfways.scala
// spark-submit ./sparkscala_2.11-0.1.0-SNAPSHOT.jar --class example.dfways
package example

import org.apache.spark.sql.SparkSession
// For implicit conversions from RDDs to DataFrames
import org.apache.spark.sql.Row
import org.apache.spark.sql.types._

object dfways {
  case class Name(firstName: String, lastName: String, middleName: String)

  def main_dfways(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[1]")
      .appName("Spark SQL - Different ways to create DataFrames")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    println("Defining data to be used...")
    import spark.implicits._
    val columns = Seq("language", "users_count")
    val data = Seq(("Java", "20000"), ("Python", "100000"), ("Scala", "3000"))

    println("Create rdd of the data...")
    val rdd = spark.sparkContext.parallelize(data)

    println("Using toDF() to create a DF...")
    val dfFromRDD1 = rdd.toDF()
    println("Print schema of DF...")
    dfFromRDD1.printSchema()
    dfFromRDD1.show()

    println("Create DF using a schema...")
    //val dfFromRDD2 = rdd.toDF("language","users_count")
    val dfFromRDD2 = rdd.toDF(columns(0), columns(1))
    dfFromRDD2.printSchema()
    dfFromRDD2.show()

    println("Create DF using Spark createDataFrame() from SparkSession...")
    val dfFromRDD2_1 = spark.createDataFrame(rdd).toDF(columns: _*)
    dfFromRDD2_1.printSchema()
    dfFromRDD2_1.show()

    println("Using createDataFrame() with the Row type...")
    import org.apache.spark.sql.types.{StringType, StructField, StructType}
    import org.apache.spark.sql.Row
    val schema = StructType(
      Array(
        StructField("language", StringType, true),
        StructField("users_count", StringType, true)
      )
    )
    val rowRDD = rdd.map(attributes => Row(attributes._1, attributes._2))
    val dfFromRDD3 = spark.createDataFrame(rowRDD, schema)
    dfFromRDD3.printSchema()
    dfFromRDD3.show()

    println("Using toDF() on List or Seq collection...")
    import spark.implicits._
    val dfFromData1 = data.toDF() // data is a Seq defined above.
    dfFromData1.printSchema()
    dfFromData1.show()

    println(
      "Using createDataFrame() from SparkSession on List or Seq collection..."
    )
    var dfFromData2 = spark.createDataFrame(data).toDF(columns: _*)
    dfFromData2.printSchema()
    dfFromData2.show()

    println(
      "Using createDataFrame() with the Row type on List or Seq collection..."
    )
    import scala.collection.JavaConversions._
    //From Data (USING createDataFrame and Adding schema using StructType)
    val rowData =
      Seq(Row("Java", "20000"), Row("Python", "100000"), Row("Scala", "3000"))
    var dfFromData3 = spark.createDataFrame(rowData, schema)
    dfFromData3.printSchema()
    dfFromData3.show()

    println("Create Spark DataFrame from CSV...")
    val df2 = spark.read.csv(
      "file:///home/maria_dev/SparkSamples/resources/spark_examples/people.csv"
    )
    df2.printSchema()
    df2.show()

    println("Creating from text (TXT) file...")
    val df2txt = spark.read.text(
      "file:///home/maria_dev/SparkSamples/resources/spark_examples/people.txt"
    )
    df2txt.printSchema()
    df2txt.show()

    println("Creating from JSON file...")
    val df2json = spark.read.json(
      "file:///home/maria_dev/SparkSamples/resources/spark_examples/people.json"
    )
    df2json.printSchema()
    df2json.show()

    println("Creating empty DF (Spark 2.x and above)...")
    val dfEmpty = spark.emptyDataFrame
    dfEmpty.printSchema()

    println("Create empty DataFrame with schema (StructType)...")
    import spark.implicits._

    val schemaEmptyDF = StructType(
      StructField("firstName", StringType, true) ::
        StructField("lastName", IntegerType, false) ::
        StructField("middleName", IntegerType, false) :: Nil
    )

    val dfEmpty2 =
      spark.createDataFrame(spark.sparkContext.emptyRDD[Row], schemaEmptyDF)
    dfEmpty2.printSchema()

    println("Create an empty DF using implicit encoder (from a collection)...")
    val colSeq = Seq("firstName", "lastName", "middleName")
    val dfEmpty3 = Seq.empty[(String, String, String)].toDF(colSeq: _*)
    dfEmpty3.printSchema()

    println("Create an empty DF using case class...")
    val dfEmpty4 = Seq.empty[Name].toDF()       // case class defined outside of "main".
    dfEmpty4.printSchema()

    println("Convert Spark RDD to DataFrame | Dataset...")
    println("Create Spark RDD...")
    import spark.implicits._
    val data2 = Seq(("Java", "20000"), ("Python", "100000"), ("Scala", "3000"))
    val rdd3 = spark.sparkContext.parallelize(data)

    // These are duplicate:
    // // println("Convert Spark RDD to DataFrame...")
    // // println("Convert Spark RDD to DataFrame using .toDF()...")
    // // val dfFromRDD4 = rdd3.toDF()
    // // dfFromRDD4.printSchema()
    // // dfFromRDD4.show()

    // // println("Convert Spark RDD to DF and define column names...")
    // // val dfFromRDD5 = rdd3.toDF("language","users_count")
    // // dfFromRDD5.printSchema()

    // // val columns2 = Seq("language","users_count")
    // // println("Convert RDD to DataFrame – Using createDataFrame()...")
    // // val dfFromRDD6 = spark.createDataFrame(rdd3).toDF(columns2:_*)
    // // dfFromRDD6.printSchema()

    // // println("Convert RDD to DataFrame – Using RDD Row type RDD[Row] to DataFrame...")
    // // //From RDD (USING createDataFrame and Adding schema using StructType)
    // // val schema2 = StructType(columns
    // //     .map(fieldName => StructField(fieldName, StringType, nullable = true)))
    // // //convert RDD[T] to RDD[Row]
    // // val rowRDD2 = rdd.map(attributes => Row(attributes._1, attributes._2))
    // // val dfFromRDD7 = spark.createDataFrame(rowRDD2,schema2)
    // // dfFromRDD7.printSchema()

    println("Convert Spark RDD to Dataset...")
    val ds = spark.createDataset(rdd3)
    ds.printSchema()

    println("Done...")
  }
}
