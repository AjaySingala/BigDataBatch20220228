// show.scala
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.show

package example

import org.apache.spark.sql.SparkSession
// For implicit conversions from RDDs to DataFrames
import org.apache.spark.sql.Row
import org.apache.spark.sql.types._

object show {
  def main_show(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[1]")
      .appName("Spark SQL - Functions")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    println("show()...")
    println("Defining initial data and DF...")
    import spark.implicits._
    val columns = Seq("Seqno","Quote")
    val data = Seq(("1", "Be the change that you wish to see in the world"),
        ("2", "Everyone thinks of changing the world, but no one thinks of changing himself."),
        ("3", "The purpose of our lives is to be happy."),
        ("4", "Be cool."))
    val df = data.toDF(columns:_*)
    df.show()

    println("Display full column contents by saying 'truncate=false'...")
    df.show(false)    // df.show(truncate=false)

    // By default, show() displays 20 rows.
    println("Display 2 rows and full column contents...")
    df.show(2, false) // df.show(numRows=2, truncate=false)
    
    println("Display 2 rows & column values 25 characters...")
    df.show(2, 25)

    println("Display DataFrame rows & columns vertically...")
    df.show(3, 25, true)

    println("Done!")

  }
}
