// drop.scala
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.DropColumn

package example

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}
import org.apache.spark.sql.functions.col
object DropColumn { // extends App {

  val spark:SparkSession = SparkSession.builder()
    .master("local[5]")
    .appName("AjaySingala.com")
    .getOrCreate()
  spark.sparkContext.setLogLevel("ERROR")

  println("Defining the data...")  
  val data = Seq(
    Row("James","","Smith","36636","NewYork",3100),
    Row("Michael","Rose","","40288","California",4300),
    Row("Robert","","Williams","42114","Florida",1400),
    Row("Maria","Anne","Jones","39192","Florida",5500),
    Row("Jen","Mary","Brown","34561","NewYork",3000)
  )

  println("Defining the schema...")  
  val schema = new StructType()
    .add("firstname",StringType)
    .add("middlename",StringType)
    .add("lastname",StringType)
    .add("id",StringType)
    .add("location",StringType)
    .add("salary",IntegerType)

  println("Creating the DF...")  
  val df = spark.createDataFrame(
    spark.sparkContext.parallelize(data),schema)
  df.printSchema()
  df.show(false)

  println("Drop the firstname using drop(df())...")  
  df.drop(df("firstname"))
    .printSchema()

  println("Drop firstname using drop(col())...")  
  df.drop(col("firstname"))
    .printSchema()

  println("Drop firstname using drop()...")  
  val df2 = df.drop("firstname")
  df2.printSchema()

  println("Drop firstname, middlename and lastname...")  
  df.drop("firstname","middlename","lastname")
    .printSchema()

  println("Drop firstname, middlename and lastname using cols:_*...")  
  val cols = Seq("firstname","middlename","lastname")
  df.drop(cols:_*)
    .printSchema()
}
