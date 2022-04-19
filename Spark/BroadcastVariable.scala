// BroadcastVariable.scala
import org.apache.spark.sql.SparkSession

// object RDDBroadcast extends App {

//   val spark = SparkSession.builder()
//     .appName("ajaysingala.com")
//     .master("local")
//     .getOrCreate()

  val states = Map(("NY","New York"),("CA","California"),("FL","Florida"), ("NSW", "New South Wales"))
  val countries = Map(("USA","United States of America"),("AU","Australia"))

  val broadcastStates = spark.sparkContext.broadcast(states)
  val broadcastCountries = spark.sparkContext.broadcast(countries)

  val data = Seq(("James","Smith","USA","CA"),
    ("Michael","Rose","USA","NY"),
    ("Robert","Williams","USA","CA"),
    ("David","Warner","AU","NSW"),
    ("Maria","Jones","USA","FL")
  )

  val rdd = spark.sparkContext.parallelize(data)  // sc.parallelize(data)

  val rdd2 = rdd.map(f=>{
    val country = f._3
    val state = f._4
    val fullCountry = broadcastCountries.value.get(country).get
    val fullState = broadcastStates.value.get(state).get
    (f._1,f._2,fullCountry,fullState)
  })

  println("\nRaw data...")
  rdd.foreach(println)

  println("\nProcessed with Broadcast Variables...")
  println(rdd2.collect().mkString("\n"))

//}
