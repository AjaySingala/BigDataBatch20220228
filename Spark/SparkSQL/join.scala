// join.scala
// spark-submit sparkscala_2.11-0.1.0-SNAPSHOT.jar  --class example.JoinExample
package example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
object JoinExample { //extends App {

  val spark: SparkSession = SparkSession.builder()
    .master("local[1]")
    .appName("AjaySingala.com")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  println("Defining emp data...")
  val emp = Seq((1,"Smith",-1,"2018","10","M",3000),
    (2,"Rose",1,"2010","20","M",4000),
    (3,"Williams",1,"2010","10","M",1000),
    (4,"Jones",2,"2005","10","F",2000),
    (5,"Brown",2,"2010","40","",-1),
      (6,"Brown",2,"2010","50","",-1)
  )
  println("Defining the emp schema...")
  val empColumns = Seq("emp_id","name","superior_emp_id","year_joined","emp_dept_id","gender","salary")

  println("Creating the emp DF...")
  import spark.sqlContext.implicits._
  val empDF = emp.toDF(empColumns:_*)
  empDF.show(false)

  println("Defining dept data...")
  val dept = Seq(("Finance",10),
    ("Marketing",20),
    ("Sales",30),
    ("IT",40)
  )

  println("Defining the dept schema...")
  val deptColumns = Seq("dept_name","dept_id")
  println("Creating the dept DF...")
  val deptDF = dept.toDF(deptColumns:_*)
  deptDF.show(false)

  // SELECT ... FROM Emp INNER JOIN DEPT ON EMP.emp_dept_id = DEPT.dept_id
  println("Inner join")
  empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"),"inner")
    .show(false)

  println("Outer join")
  empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"),"outer")
    .show(false)
  println("full join")
  empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"),"full")
    .show(false)
  println("fullouter join")
  empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"),"fullouter")
    .show(false)

  println("right join")
  empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"),"right")
    .show(false)
  println("rightouter join")
  empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"),"rightouter")
    .show(false)

  println("left join")
  empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"),"left")
    .show(false)
  println("leftouter join")
  empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"),"leftouter")
    .show(false)

  // Similar to INNER JOIN, returns all columns from the left DF/DS for matching records 
  //and ignores all columns from the right DF/DS.
  println("leftsemi join")
  empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"),"leftsemi")
    .show(false)

  // Returns only columns from the left DF/DS for non-matched records.
  println("leftanti join")
  empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"),"leftanti")
    .show(false)

  println("cross join")
  empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"),"cross")
    .show(false)

  println("Using crossJoin()")
  empDF.crossJoin(deptDF).show(false)

  println("self join")
  empDF.as("emp1").join(empDF.as("emp2"),
    col("emp1.superior_emp_id") === col("emp2.emp_id"),"inner")
    .select(col("emp1.emp_id"),col("emp1.name"),
      col("emp2.emp_id").as("superior_emp_id"),
      col("emp2.name").as("superior_emp_name"))
      .show(false)

  //SQL JOIN
  empDF.createOrReplaceTempView("EMP")
  deptDF.createOrReplaceTempView("DEPT")

  println("Join using SQL 'WHERE'...")
  val joinDF = spark.sql("select * from EMP e, DEPT d WHERE e.emp_dept_id == d.dept_id")
  joinDF.show(false)

  println("Join using SQL 'INNER JOIN'...")
  val joinDF2 = spark.sql("select * from EMP e INNER JOIN DEPT d ON e.emp_dept_id == d.dept_id")
  joinDF2.show(false)

}
