// readtextdd.scala
package com.examples.spark.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object ReadTextFiles extends App {

  val spark:SparkSession = SparkSession.builder()
    .master("local[1]")
    .appName("ajaysingala.com")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  println("##spark read text files from a directory into RDD")
  val rddFromFile = spark.sparkContext.textFile("file:///home/maria_dev/SparkSamples/resources/csv/text01.txt")
  println(rddFromFile.getClass)

  println("##Get data Using collect")
  rddFromFile.collect().foreach(f=>{
    println(f)
  })

  println("##read multiple text files into a RDD")
  val rdd4 = spark.sparkContext.textFile("file:///home/maria_dev/SparkSamples/resources/csv/text01.txt," +
    "file:///home/maria_dev/SparkSamples/resources/csv/text02.txt")
  rdd4.foreach(f=>{
    println(f)
  })

  println("##read text files base on wildcard character")
  val rdd3 = spark.sparkContext.textFile("file:///home/maria_dev/SparkSamples/resources/csv/text*.txt")
  rdd3.foreach(f=>{
    println(f)
  })

  println("##read all text files from a directory to single RDD")
  val rdd2 = spark.sparkContext.textFile("file:///home/maria_dev/SparkSamples/resources/csv/*")
  rdd2.foreach(f=>{
    println(f)
  })

  println("##read whole text files")
  val rddWhole:RDD[(String,String)] = spark.sparkContext.wholeTextFiles("file:///home/maria_dev/SparkSamples/resources/csv/*.txt")
  println(rddWhole.getClass)
  rddWhole.foreach(f=>{
    println(f._1+"=>"+f._2)
  })
}
