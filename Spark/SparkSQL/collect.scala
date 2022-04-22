// collect.scala
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.CollectExample

package example

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}

object CollectExample { // extends App {

  val spark: SparkSession = SparkSession
    .builder()
    .master("local[1]")
    .appName("AjaySingala.com")
    .getOrCreate()
  
  spark.sparkContext.setLogLevel("ERROR")
  
  println("Defining the data...")
  val data = Seq(
    Row(Row("James ", "", "Smith"), "36636", "M", 3000),
    Row(Row("Michael ", "Rose", ""), "40288", "M", 4000),
    Row(Row("Robert ", "", "Williams"), "42114", "M", 4000),
    Row(Row("Maria ", "Anne", "Jones"), "39192", "F", 4000),
    Row(Row("Jen", "Mary", "Brown"), "", "F", -1)
  )

  println("Defining the schema...")
  val schema = new StructType()
    .add(
      "name",
      new StructType()
        .add("firstname", StringType)
        .add("middlename", StringType)
        .add("lastname", StringType)
    )
    .add("id", StringType)
    .add("gender", StringType)
    .add("salary", IntegerType)

  println("Creating the DF...")
  val df = spark.createDataFrame(spark.sparkContext.parallelize(data), schema)
  df.printSchema()
  df.show(false)

  println("Collecting the data with df.collect()...")
  val colData = df.collect()

  println("Print the salary using index of column...")
  colData.foreach(row => {
    val salary = row.getInt(3) //Index starts from zero
    println(salary)
  })

  //Retrieving data from Struct column
  println("Print data using getStruct()...")
  colData.foreach(row => {
    val salary = row.getInt(3)
    val fullName: Row = row.getStruct(0) //Index starts from zero
    val firstName = fullName.getString(0) //In struct row, again index starts from zero
    val middleName = fullName.get(1).toString
    val lastName = fullName.getAs[String]("lastname")
    println(firstName + "," + middleName + "," + lastName + "," + salary)
  })
}
