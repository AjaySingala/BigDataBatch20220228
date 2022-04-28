// stream.scala

// $KAFKA_HOME/bin/kafka-console-producer.sh --broker-list sandbox-hdp.hortonworks.com:6667 --topic json_topic
// $KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server sandbox-hdp.hortonworks.com:6667 --topic json_topic
// $KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server sandbox-hdp.hortonworks.com:6667 --topic json_output_topic
// spark-shell --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.3.0.
// Sample data:
// {"id":1,"firstname":"James ","middlename":"","lastname":"Smith","dob_year":2018,"dob_month":1,"gender":"M","salary":3000}
// {"id":2,"firstname":"Michael ","middlename":"Rose","lastname":"","dob_year":2010,"dob_month":3,"gender":"M","salary":4000}
// {"id":3,"firstname":"Robert ","middlename":"","lastname":"Williams","dob_year":2010,"dob_month":3,"gender":"M","salary":4000}
// {"id":4,"firstname":"Maria ","middlename":"Anne","lastname":"Jones","dob_year":2005,"dob_month":5,"gender":"F","salary":4000}
// {"id":5,"firstname":"Jen","middlename":"Mary","lastname":"Brown","dob_year":2010,"dob_month":7,"gender":"","salary":-1}

import org.apache.spark.sql.SparkSession
//import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}

//object SparkStreamingConsumerKafkaJson {

//  def main(args: Array[String]): Unit = {

   //val spark: SparkSession = SparkSession.builder().master("local[3]").appName("ajaysingala").getOrCreate()
   
   import spark.implicits._
   
   spark.sparkContext.setLogLevel("ERROR")

   // Read from start.
   // // Custom Ubuntu VM.
   // val df = spark.readStream.format("kafka").option("kafka.bootstrap.servers", "localhost:9092").option("subscribe", "json_topic").option("startingOffsets", "earliest").load()
   // Hortonworks HDP VM.
   val df = spark.readStream.format("kafka").option("kafka.bootstrap.servers", "sandbox-hdp.hortonworks.com:6667").option("subscribe", "json_topic").option("startingOffsets", "earliest").option("failOnDataLoss", false).load()

   //df.printSchema()

   val schema = new StructType().add("id",IntegerType).add("firstname",StringType).add("middlename",StringType).add("lastname",StringType).add("dob_year",IntegerType).add("dob_month",IntegerType).add("gender",StringType).add("salary",IntegerType)

   // Since the value is in binary, first we need to convert the binary value to String using selectExpr().
   // Also apply the schema.
   val personDF = df.selectExpr("CAST(value AS STRING)").select(from_json(col("value"), schema).as("data")).select("data.*")
   personDF.printSchema()
   
   /**
    *uncomment below code if you want to write it to console for testing.
   */
   // val query = personDF.writeStream.format("console").outputMode("append").option("truncate", "false").start().awaitTermination()


   /**
    * uncomment below code if you want to write it to kafka topic.
   */
   val dfOut = personDF.selectExpr("CAST(id AS STRING) AS key", "to_json(struct(*)) AS value").alias("value")
   dfOut.printSchema()
   // // // Custom Ubuntu VM.
   // // dfOut
   // //    .writeStream.format("kafka")
   // //    .outputMode("append")
   // //    .option("kafka.bootstrap.servers", "localhost:9092")
   // //    .option("checkpointLocation", "/tmp/kafka_checkpoints")
   // //    .option("topic", "json_output_topic")
   // //    .start()
   // //    //.awaitTermination()
   // Hortonworks HDP VM.
   dfOut.writeStream.format("kafka").outputMode("append").option("kafka.bootstrap.servers", "sandbox-hdp.hortonworks.com:6667").option("checkpointLocation", "/tmp/kafka_checkpoints").option("topic", "json_output_topic").start().awaitTermination()

   // dfOut.createOrReplaceTempView("Person")
   // val dfSQL =  spark.sql("SELECT * FROM PERSON")
   // val dfJSON = dfSQL.toJSON
   // dfJSON.writeStream.format("console").outputMode("append").option("truncate", "false").start().awaitTermination()
//  }
//}
