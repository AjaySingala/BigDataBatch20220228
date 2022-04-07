package example

import java.io._
import scala.io._

object FileDemo {
  def main(args: Array[String]) {
    // writeFile()
    // readCmdLine()
    readFile()
  }

  def readFile() {
    // Source.fromFile("foo.txt").foreach {
    //     print
    // }
    try {
    //   for (line <- Source.fromFile("foo.txt").getLines) {
    //     println(line)
    //   }

    val fileContents = Source.fromFile("foo.txt").getLines.mkString
    println(fileContents)
    
    //   var linesList = Source.fromFile("foo.txt").getLines.toList
    //   var linesArr = Source.fromFile("foo.txt").getLines.toArray

    //   println(linesList)
    //   println()
    //   println(linesArr)
    }
    catch {
        case fex: FileNotFoundException => println("Cannot read file as it is not found.")
        case ioex: IOException => println("Some IO Exception occured")
        case _: Throwable => println("Something went wrong.")
    }

  }

  def readCmdLine() {
    print("Please enter your name: ")
    val name = StdIn.readLine

    println(s"Welcome $name")
  }

  def writeFile() {
    val writer = new PrintWriter(new File("foo.txt"))

    writer.write("Welcome to Scala!")
    writer.close()
  }
}
