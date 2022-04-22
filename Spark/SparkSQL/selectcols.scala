// selectcols.scala
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.SelectCols

package example

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._

object SelectCols {
  def main_SelectCols(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("AjaySingala")
      .getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    val sc = spark.sparkContext

    println("Defining the data...")
    val data = Seq(
      ("James", "Smith", "USA", "CA"),
      ("Michael", "Rose", "USA", "NY"),
      ("Robert", "Williams", "USA", "CA"),
      ("Maria", "Jones", "USA", "FL")
    )

    println("Defining the columns...")
    val columns = Seq("firstname", "lastname", "country", "state")

    import spark.implicits._
    println("Creating the DF...")
    val df = data.toDF(columns: _*)
    df.show(false)

    // select single or multiple columns from DataFrame.
    println("select single / multiple columns from DF...")
    df.select("firstname", "lastname").show()

    //Using Dataframe object name
    println("select columns using DF object...")
    df.select(df("firstname"), df("lastname")).show()

    //Using col function, use alias() to get alias name
    import org.apache.spark.sql.functions.col
    println("select using col() and alias()...")
    df.select(col("firstname").alias("fname"), col("lastname")).show()

    // Select All Columns.
    //Show all columns from DataFrame
    println("select all columns...")
    df.select("*").show()
    println("select all columns using map() and col()...")
    val columnsAll = df.columns.map(m => col(m))
    println("...using columnsAll:_*...")
    df.select(columnsAll: _*).show()
    println("...using map() and col() directly...")
    df.select(columns.map(m => col(m)): _*).show()

    // Select Columns from List.
    println("select columns from a list...")
    val listCols = List("lastname", "country")
    df.select(listCols.map(m => col(m)): _*).show()

    // Select First N Columns.
    // Select Column By Position or Index//Select first 3 columns.
    println("select first 'N' columns using slice(0,3) : upto 3, not including 3...")
    df.select(df.columns.slice(0, 3).map(m => col(m)): _*).show()

    // Select Column By Position or Index//Select first 3 columns.
    //Selects 4th column (index starts from zero)
    println("select column by position, the 3rd column (0-based index)...")
    df.select(df.columns(3)).show()
    //Selects columns from index 2 to 4
    println("select columns 2 to 4 using slice() (upto 4, not including 4)...")
    df.select(df.columns.slice(2, 4).map(m => col(m)): _*).show()

    // Select Nested Struct Columns.
    //Show Nested columns
    println("select nested columns...")
    import org.apache.spark.sql.types.{StringType, StructType}
    println("Defining new data (nested)...")
    val data2 = Seq(
      Row(Row("James", "", "Smith"), "OH", "M"),
      Row(Row("Anna", "Rose", ""), "NY", "F"),
      Row(Row("Julia", "", "Williams"), "OH", "F"),
      Row(Row("Maria", "Anne", "Jones"), "NY", "M"),
      Row(Row("Jen", "Mary", "Brown"), "NY", "M"),
      Row(Row("Mike", "Mary", "Williams"), "OH", "M")
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
      .add("state", StringType)
      .add("gender", StringType)

    println("Creating the DF...")
    val df2 =
      spark.createDataFrame(spark.sparkContext.parallelize(data2), schema)
    df2.printSchema()
    df2.show(false)

    println("select name nested column...")
    df2.select("name").show(false)
    // to get the specific column from a struct, you need to explicitly qualify.
    println("Select specific nested column from struct...")
    df2.select("name.firstname","name.lastname").show(false)
    // to get all columns from struct column.
    println("Select all sub-columns from a nested column...")
    df2.select("name.*").show(false)
  }
}
