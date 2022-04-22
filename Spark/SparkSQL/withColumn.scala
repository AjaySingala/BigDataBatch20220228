// withColumn.scala
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.WithColumn

package example

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructType}
import org.apache.spark.sql.functions._
object WithColumn {

  def main_WithColumn(args:Array[String]):Unit= {

    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("AjaySingala.com")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    println("Defining the data...")
    val dataRows = Seq(Row(Row("James;","","Smith"),"36636","M","3000"),
      Row(Row("Michael","Rose",""),"40288","M","4000"),
      Row(Row("Robert","","Williams"),"42114","M","4000"),
      Row(Row("Maria","Anne","Jones"),"39192","F","4000"),
      Row(Row("Jen","Mary","Brown"),"","F","-1")
    )

    println("Defining the schema...")
    val schema = new StructType()
      .add("name",new StructType()
        .add("firstname",StringType)
        .add("middlename",StringType)
        .add("lastname",StringType))
      .add("id",StringType)
      .add("gender",StringType)
      .add("salary",StringType)

    println("Creating the DF...")
    val df2 = spark.createDataFrame(spark.sparkContext.parallelize(dataRows),schema)
    df2.printSchema()
    df2.show()

    //Change the column data type
    println("Change data type of 'salary'...")
    val df3 = df2.withColumn("salary",df2("salary").cast("Integer"))
    df3.printSchema()
    df3.show()

    //Derive a new column from existing
    println("Derive a new column 'CopiedColumn'...")
    val df4=df2.withColumn("CopiedColumn",df2("salary")* -1)
    df4.printSchema()
    df4.show()

    //Transforming existing column
    println("Transform an existing column 'Salary'...")
    val df5 = df2.withColumn("salary",df2("salary")*100)
    df5.printSchema()
    df5.show()

    //You can also chain withColumn to change multiple columns

    //Renaming a column.
    println("Rename column 'gender' to 'sex'...")
    val dfRen=df2.withColumnRenamed("gender","sex")
    dfRen.printSchema()

    //Droping a column
    println("Drop column 'CopiedColumn'...")
    val df6=df4.drop("CopiedColumn")
    println(df6.columns.contains("CopiedColumn"))
    df6.printSchema()

    //Adding a literal value
    println("Adding literal value to 'Country'...")
    val dflit = df2.withColumn("Country", lit("USA"))
    dflit.printSchema()
    dflit.show()

    // //Retrieving
    // df2.show(false)
    // df2.select("name").show(false)
    // df2.select("name.firstname").show(false)
    // df2.select("name.*").show(false)

    println("Use 'SELECT' statement on tempo view 'PERSON' for df2...")
    df2.createOrReplaceTempView("PERSON")
    spark.sql("SELECT salary*100 as salary, salary*-1 as CopiedColumn, 'USA' as country FROM PERSON").show()

    import spark.implicits._

    println("Define new data for name and address...")
    val columns = Seq("name","address")
    val data = Seq(("Robert, Smith", "1 Main st, Newark, NJ, 92537"), ("Maria, Garcia","3456 Walnut st, Newark, NJ, 94732"))
    var dfFromData = spark.createDataFrame(data).toDF(columns:_*)
    dfFromData.printSchema()

    println("Split the names and addresses...")
    val newDF = dfFromData.map(f=>{
      val nameSplit = f.getAs[String](0).split(",")
      val addSplit = f.getAs[String](1).split(",")
      (nameSplit(0),nameSplit(1),addSplit(0),addSplit(1),addSplit(2),addSplit(3))
    })

    println("Create new DF from the split values with final schema...")
    val finalDF = newDF.toDF("First Name","Last Name","Address Line1","City","State","zipCode")
    finalDF.printSchema()
    finalDF.show(false)
  }
}

