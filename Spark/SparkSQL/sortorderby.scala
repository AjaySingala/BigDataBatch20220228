// sortorderby.scala
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.SortExample
package example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
object SortExample { //extends App {

  val spark: SparkSession = SparkSession.builder()
    .master("local[1]")
    .appName("AjaySingala.com")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  import spark.implicits._

  println("Defining the data...")
  val simpleData = Seq(("James","Sales","NY",90000,34,10000),
    ("Michael","Sales","NY",86000,56,20000),
    ("Robert","Sales","CA",81000,30,23000),
    ("Maria","Finance","CA",90000,24,23000),
    ("Raman","Finance","CA",99000,40,24000),
    ("Scott","Finance","NY",83000,36,19000),
    ("Jen","Finance","NY",79000,53,15000),
    ("Jeff","Marketing","CA",80000,25,18000),
    ("Kumar","Marketing","NY",91000,50,21000)
  )
  println("Creating the DF...")
  val df = simpleData.toDF("employee_name","department","state","salary","age","bonus")
  df.printSchema()
  df.show()

  println("Sort by department, state...")
  df.sort("department","state").show(false)
  println("Sort by department, state using col()...")
  df.sort(col("department"),col("state")).show(false)

  println("Order by department, state...")
  df.orderBy("department","state").show(false)
  println("Order by department, state using col()...")
  df.orderBy(col("department"),col("state")).show(false)

  println("Sort by department, state using col() and .asc...")
  df.sort(col("department").asc,col("state").asc).show(false)
  println("Order by department, state using col() and .asc...")
  df.orderBy(col("department").asc,col("state").asc).show(false)

  println("Sort by department, state using col() and .asc and .desc...")
  df.sort(col("department").asc,col("state").desc).show(false)
  println("Order by department, state using col() and .asc and .desc...")
  df.orderBy(col("department").asc,col("state").desc).show(false)
 
  println("Creating temp view 'EMP'...")
  df.createOrReplaceTempView("EMP")
  println("Sort using temp view 'EMP'...")
  spark.sql(" select employee_name,department, state,salary,age,bonus from EMP ORDER BY department ASC, state DESC").show(false)

}
