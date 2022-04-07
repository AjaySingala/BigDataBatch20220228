package example

import scala.io.Source

object FileCSVDemo {
    def main(args: Array[String]) {
        println("Month, Income, Expense, Profit")

        val filename = "C:\\temp\\finance.csv"
        val file = io.Source.fromFile(filename)

        // for(line <- file.getLines()) {
        //     val columns = line.split(",")
        //     println(s"${columns(0)} | ${columns(1)} | ${columns(2)} | ${columns(3)}")
        // }

        for(line <- file.getLines()) {
            val Array(month, income, expense, profit) = line.split(",")
            println(s"${month} | ${income} | ${expense} | ${profit}")
        }

        file.close()
    }
}