package example

import java.io.IOException

object ExceptionDemo {
    def main(args: Array[String]) {

        println(divide(10,2))
        println(divide(10,0))
    }

    def divide(x: Int, y: Int): Any = {
        try {
            println(s"Dividing $x by $y...")
            var result = x / y

            //throw new Exception("This is a manual excecption for testing...")
            result
        }
        catch {
            case ex: ArithmeticException => {
                println(s"ERROR! Divide operation failed!!! ($ex)")
            }
            case fex: IOException => {
                println(s"ERROR! File operation failed!!! ($fex)")
            }
            case a: Throwable => println(s"Some exception happened!!! ($a)")
        }
        finally {
            println("This block will always execute.")
        }
    }
}