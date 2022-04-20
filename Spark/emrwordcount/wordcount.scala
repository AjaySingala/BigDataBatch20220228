// wordcount.scala
package example

import org.apache.spark._

object WordCount {
    def main(args: Array[String]) {
        // Create a SparkContext for EMR.
        val sc = new SparkContext()
        // // Create a SparkContext using every core of the local machine.
        // val sc = new SparkContext("local[*]", "WordCount")
        
        sc.setLogLevel("ERROR")

        // Read each line of the book into an RDD.
        // On EMR.
        val input = sc.textFile("s3://ajshdoop/wordcount/TheNotebooksofLeonardoDaVinci.txt")
        // // On local
        // val input = sc.textFile("file:///home/hdoop/Research/data/TheNotebooksofLeonardoDaVinci.txt")

        // Split  into words separated by a space character.
        val words = input.flatMap(w => w.split(" "))

        val wordCounts = words.map(x => (x,1))

        val final_count = wordCounts.reduceByKey((a,b) => a+b)

        val my_count = final_count.collect()
        my_count.foreach(println)
        //scala.io.StdIn.readLine()
    }
}