// union.scala
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.UnionExample
package example

import org.apache.spark.sql.SparkSession

object UnionExample {// extends App{

  val spark: SparkSession = SparkSession.builder()
    .master("local[1]")
    .appName("AjaySingala.com")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  import spark.implicits._

  println("Defining the Emp 1 data...")
  val simpleData = Seq(("James","Sales","NY",90000,34,10000),
    ("Michael","Sales","NY",86000,56,20000),
    ("Robert","Sales","CA",81000,30,23000),
    ("Maria","Finance","CA",90000,24,23000)
  )
  println("Defining the schema for Emp 1...")
  val df = simpleData.toDF("employee_name","department","state","salary","age","bonus")
  df.printSchema()
  df.show()

  println("Defining the Emp 2 data...")
  val simpleData2 = Seq(("James","Sales","NY",90000,34,10000),
    ("Maria","Finance","CA",90000,24,23000),
    ("Jen","Finance","NY",79000,53,15000),
    ("Jeff","Marketing","CA",80000,25,18000),
    ("Kumar","Marketing","NY",91000,50,21000)
  )
  println("Defining the schema for Emp 2...")
  val df2 = simpleData2.toDF("employee_name","department","state","salary","age","bonus")
  df2.show(false)

  println("combine with union()...")
  val df3 = df.union(df2)
  df3.show(false)
  println("Show only distinct rows...")
  df3.distinct().show(false)

  println("combine with unionAll()...")
  val df4 = df.unionAll(df2)
  df4.show(false)
}
