package example

import scala.util.{Try, Success, Failure}

case class DivideByZeroTSF(msg: String) extends Exception(msg)

object TrySuccessFailureDemo {
    def main(args: Array[String]) {
        // val res = divideWithTry(10,0)
        // println(res)

        val result1 = divideWithTry(10,2) match {
            case Success(i) => println(s"Result is $i")
            case Failure(DivideByZeroTSF(_)) => None
        }
        
        val result2 = divideWithTry(10,0) match {
            case Success(i) => println(s"Result is $i")
            //case Failure(DivideByZeroTSF(_)) => println("You are trying to divide by zero. Not allowed!")
            case Failure(s) => println(s"You are trying to divide by zero. Not allowed! $s")
        }
    }

    def divideWithTry(x: Int, y: Int): Try[Int] = {
        Try(divide(x,y))
    }
    
    def divide(x: Int, y: Int) : Int = {
        println(s"\nDividing $x by $y...")
        if(y == 0) {
            throw new DivideByZeroTSF("Cannot divide by Zero.")
        }

        x / y
    }
}