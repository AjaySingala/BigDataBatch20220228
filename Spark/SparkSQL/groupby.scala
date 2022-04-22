// groupby.scala
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.GroupbyExample
package example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object GroupbyExample { //extends App {
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
  df.show()

  //Group By on single column
  println("Group by department, then count...")  
  df.groupBy("department").count().show(false)
  println("Group by department, then avg(salary)...")  
  df.groupBy("department").avg("salary").show(false)
  println("Group by department, then sum(salary)...")  
  df.groupBy("department").sum("salary").show(false)
  println("Group by department, then min(salary)...")  
  df.groupBy("department").min("salary").show(false)
  println("Group by department, then max(salary)...")  
  df.groupBy("department").max("salary").show(false)
  println("Group by department, then mean(salary)...")  
  df.groupBy("department").mean("salary").show(false)

  //GroupBy on multiple columns
  println("Group by department, state, then sum(salary, bonus)...")  
  df.groupBy("department","state")
    .sum("salary","bonus")
    .show(false)
  println("Group by department, state, then avg(salary, bonus)...")  
  df.groupBy("department","state")
    .avg("salary","bonus")
    .show(false)
  println("Group by department, state, then max(salary, bonus)...")  
  df.groupBy("department","state")
    .max("salary","bonus")
    .show(false)
  println("Group by department, state, then min(salary, bonus)...")  
  df.groupBy("department","state")
    .min("salary","bonus")
    .show(false)
  println("Group by department, state, then mean(salary, bonus)...")  
  df.groupBy("department","state")
    .mean("salary","bonus")
    .show(false)

  println("Group by department, state, then sum(salary, bonus) again...")  
  df.groupBy("department","state")
    .sum("salary","bonus")
    .show(false)

  //using agg function
  println("Group by department using agg()...")  
  df.groupBy("department")
    .agg(
      sum("salary").as("sum_salary"),
      avg("salary").as("avg_salary"),
      sum("bonus").as("sum_bonus"),
      max("bonus").as("max_bonus"))
    .show(false)

  println("Group by department using agg() and where()...")  
  df.groupBy("department")
    .agg(
      sum("salary").as("sum_salary"),
      avg("salary").as("avg_salary"),
      sum("bonus").as("sum_bonus"),
      stddev("bonus").as("stddev_bonus"))
    .where(col("sum_bonus") > 50000)
    .show(false)
}
