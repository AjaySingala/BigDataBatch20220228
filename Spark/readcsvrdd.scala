// readcsvrdd.scala
package com.examples.spark.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object ReadMultipleCSVFiles extends App {

  val spark:SparkSession = SparkSession.builder()
    .master("local[1]")
    .appName("ajaysingala.com")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  println("spark read csv files from a directory into RDD")
  val rddFromFile = spark.sparkContext.textFile("file:///home/maria_dev/SparkSamples/resources/csv/text01.csv")
  println(rddFromFile.getClass)

  val rdd = rddFromFile.map(f=>{
    f.split(",")
  })

  println("Iterate RDD")
  rdd.foreach(f=>{
    println("Col1:"+f(0)+",Col2:"+f(1))
  })
  println("Print the RDD...")
  println(rdd)

  println("Get data Using collect")
  rdd.collect().foreach(f=>{
    println("Col1:"+f(0)+",Col2:"+f(1))
  })

  println("read all csv files from a directory to single RDD")
  val rdd2 = spark.sparkContext.textFile("file:///home/maria_dev/SparkSamples/resources/csv/*")
  rdd2.foreach(f=>{
    println(f)
  })

  println("read csv files base on wildcard character")
  val rdd3 = spark.sparkContext.textFile("file:///home/maria_dev/SparkSamples/resources/csv/text*.csv")
  rdd3.foreach(f=>{
    println(f)
  })

  println("read multiple csv files into a RDD")
  val rdd4 = spark.sparkContext.textFile("file:///home/maria_dev/SparkSamples/resources/csv/text01.csv,file:///home/maria_dev/SparkSamples/resources/csv/text02.csv")
  rdd4.foreach(f=>{
    println(f)
  })

  println("spark read zipcodes.csv into RDD...")
  val rddZips = spark.sparkContext.textFile("file:///home/maria_dev/SparkSamples/resources/zipcodes.csv")

  val rddZips1 = rddZips.map(f=>{
    f.split(",")
  })

  println("Iterate ZipCodes RDD")
  rddZips1.foreach(f=>{
    println("RecordNumber: "+f(0)+", ZipCode: "+f(1)+" Type: " + f(2))
  })

}
