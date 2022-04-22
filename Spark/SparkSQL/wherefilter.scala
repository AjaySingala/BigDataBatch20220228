// wherefilter.scala
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.FilterExample

package example

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{ArrayType, StringType, StructType}
import org.apache.spark.sql.functions.array_contains

object FilterExample { // extends App{
  val spark: SparkSession = SparkSession.builder()
    .master("local[1]")
    .appName("AjaySingala.com")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  println("Defining the data...")
  val arrayStructureData = Seq(
    Row(Row("James","","Smith"),List("Java","Scala","C++"),"OH","M"),
    Row(Row("Anna","Rose",""),List("Spark","Java","C++"),"NY","F"),
    Row(Row("Julia","","Williams"),List("CSharp","VB"),"OH","F"),
    Row(Row("Maria","Anne","Jones"),List("CSharp","VB"),"NY","M"),
    Row(Row("Jen","Mary","Brown"),List("CSharp","VB"),"NY","M"),
    Row(Row("Mike","Mary","Williams"),List("Python","VB"),"OH","M")
  )

  println("Defining the schema...")
  val arrayStructureSchema = new StructType()
    .add("name",new StructType()
      .add("firstname",StringType)
      .add("middlename",StringType)
      .add("lastname",StringType))
    .add("languages", ArrayType(StringType))
    .add("state", StringType)
    .add("gender", StringType)

  println("Creating the DF...")
  val df = spark.createDataFrame(
   spark.sparkContext.parallelize(arrayStructureData),arrayStructureSchema)
  df.printSchema()
  df.show(false)

  //Condition
  println("Show rows where state = 'OH' using the df object...")
  df.filter(df("state") === "OH")
    .show(false)

  //SQL Expression
  println("Show rows where gender = 'M' using SQL syntax...")
  df.filter("gender == 'M'")
    .show(false)

  //multiple condition
  println("Show rows where state = 'OH' AND gender = 'M'...")
  df.filter(df("state") === "OH" && df("gender") === "M")
    .show(false)

  //Array condition
  println("Show rows where languages has 'Java' using an array condition...")
  df.filter(array_contains(df("languages"),"Java"))
    .show(false)

  //Struct condition
  println("Show rows where lastname = 'Williams' using a struct (name.lastname)...")
  df.filter(df("name.lastname") === "Williams")
    .show(false)
}
