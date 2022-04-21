// sparksql_first.scala
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.SparkSqlFirst
package example

import org.apache.spark.sql.SparkSession
// For implicit conversions from RDDs to DataFrames
import org.apache.spark.sql.Row
import org.apache.spark.sql.types._

object SparkSqlFirst {
  case class Person(name: String, age: Long)

  def main_first(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
//   .config("spark.some.config.option", "some-value")
      .getOrCreate()
    
    spark.sparkContext.setLogLevel("ERROR")
    
    println("Creating DF from people.json...")
    val df = spark.read.json(
      "file:///home/maria_dev/SparkSamples/resources/spark_examples/people.json"
    )
    
    import spark.implicits._

    // Displays the content of the DataFrame to stdout
    println("Display content of DF...")
    df.show()

    //Untyped Dataset Operations (aka DataFrame Operations)

    // This import is needed to use the $-notation
    // import spark.implicits._
    // Print the schema in a tree format
    println("Print schema of df....")
    df.printSchema()

    // Select only the "name" column
    println("Show only name...")
    df.select("name").show()

    // Select everybody, but increment the age by 1
    println("Select all, but increment age by 1...")
    df.select($"name", $"age" + 1).show()

    // Select people older than 21
    println("Select people older than 21...")
    df.filter($"age" > 21).show()

    // Count people by age
    println("Count people by age...")
    df.groupBy("age").count().show()

    // Running SQL Queries Programmatically.
    // Register the DataFrame as a SQL temporary view
    println("Creating View 'people'...")
    df.createOrReplaceTempView("people")

    println("SELECT * FROM people (view).....")
    val sqlDF = spark.sql("SELECT * FROM people")
    sqlDF.show()

    // Global Temporary View.
    // Register the DataFrame as a global temporary view
    println("Creating global view people....")
    df.createGlobalTempView("people")

    // Global temporary view is tied to a system preserved database `global_temp`
    println("SELECT * FROM global_temp.people...")
    spark.sql("SELECT * FROM global_temp.people").show()

    // Global temporary view is cross-session
    println("use global_temp.people across sessions...")
    spark.newSession().sql("SELECT * FROM global_temp.people").show()
    //spark.newSession().sql("SELECT * FROM people").show()     // Does not work.

    // Creating Datasets.
    // Encoders are created for case classes
    println("Creating DS for case classes...")
    val caseClassDS = Seq(Person("Andy", 32)).toDS()
    caseClassDS.show()

    // Encoders for most common types are automatically provided by importing spark.implicits._
    val primitiveDS = Seq(1, 2, 3).toDS()
    primitiveDS.map(_ + 1).collect().foreach(println) // Returns: Array(2, 3, 4)

    // DataFrames can be converted to a Dataset by providing a class. Mapping will be done by name
    println("Convert DF to DS using a class...")
    val path = "file:///home/maria_dev/SparkSamples/resources/spark_examples/people.json"
    val peopleDS = spark.read.json(path).as[Person]
    peopleDS.show()

    // Interoperating with RDDs.
    // Create an RDD of Person objects from a text file, convert it to a Dataframe
    println("Creating an RDD of Person from text file and coverting it to a DF named peopleDF...")
    val peopleDF = spark.sparkContext.textFile("file:///home/maria_dev/SparkSamples/resources/spark_examples/people.txt")
      .map(_.split(","))
      .map(attributes => Person(attributes(0), attributes(1).trim.toInt))
      .toDF()
    // Register the DataFrame as a temporary view
    println("Creating temp view 'people' for peopleDF......")
    peopleDF.createOrReplaceTempView("people")

    // SQL statements can be run by using the sql methods provided by Spark
    println("Creating teenagersDF (people age between 13 and 19)...")
    val teenagersDF = spark.sql("SELECT name, age FROM people WHERE age BETWEEN 13 AND 19")
    teenagersDF.show()
    teenagersDF.printSchema()

    // The columns of a row in the result can be accessed by field index
    println("Showing names of teenagers accessed by field index...")
    teenagersDF.map(teenager => "Name: " + teenager(0)).show()

    // or by field name
    println("Showing names of teenagers accessed by field name...")
    teenagersDF.map(teenager => "Name: " + teenager.getAs[String]("name")).show()
    teenagersDF.map(teenager => "Age: " + teenager.getAs[Int]("age")).show()

    // Programmatically Specifying the Schema.
    // Create an RDD
    println("Creating peopleRDD...")
    val peopleRDD = spark.sparkContext.textFile("file:///home/maria_dev/SparkSamples/resources/spark_examples/people.txt")

    // The schema is encoded in a string
    val schemaString = "name age"

    // Generate the schema based on the string of schema
    println("Specifying schema to peopleRDD...")
    val fields = schemaString.split(" ")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))
    val schema = StructType(fields)

    // Convert records of the RDD (people) to Rows
    println("Converting records of RDD (people) to Rows...")
    val rowRDD = peopleRDD
      .map(_.split(","))
      .map(attributes => Row(attributes(0), attributes(1).trim))

    // Apply the schema to the RDD
    println("Applying schema to RDD peopleDF2...")
    val peopleDF2 = spark.createDataFrame(rowRDD, schema)

    // Creates a temporary view using the DataFrame
    println("Creating temp view 'people' for peopleDF2...")
    peopleDF2.createOrReplaceTempView("people")

    // SQL can be run over a temporary view created using DataFrames
    println("Displayinmg names from view 'people'...")
    val results = spark.sql("SELECT name FROM people")

    // The results of SQL queries are DataFrames and support all the normal RDD operations
    // The columns of a row in the result can be accessed by field index or by field name
    println("Display name accessed by field index on results of SQL query...")
    results.map(attributes => "Name: " + attributes(0)).show()

  }
}
