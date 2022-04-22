// HiveContextDemp.scala
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.HiveContextDemo
package example

//file:///home/maria_dev/SparkSamples/resources/employee_hive.txt

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

object HiveContextDemo {
  def main_Hive(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[3]")
      .appName("AjaySingala")
      .getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    val sc = spark.sparkContext

    println("Create SQLContext Object...")
    val sqlContext = new org.apache.spark.sql.hive.HiveContext(sc)

    println("Drop the table if it exists...")
    sqlContext.sql("DROP TABLE IF EXISTS employee_hive") 

    println("Create Table using HiveQL...")
    sqlContext.sql("CREATE TABLE IF NOT EXISTS employee_hive(id INT, name STRING, age INT) " + 
      "ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n'")

    println("Load Data into Table using HiveQL...")
    sqlContext.sql("LOAD DATA LOCAL INPATH 'file:///home/maria_dev/SparkSamples/resources/employee_hive.txt' INTO TABLE employee_hive")

    println("Select Fields from the Table...")
    //val result = sqlContext.sql("SELECT id, name, age FROM employee_hive")
    val result = sqlContext.sql("FROM employee_hive SELECT id, name, age")

    println("Display the results...")
    result.printSchema()
    result.show()

  }
}
