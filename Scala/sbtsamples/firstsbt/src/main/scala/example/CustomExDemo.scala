package example

case class DivideByZero(msg: String) extends Exception(msg)

object CustomExDemo {
    def main(args: Array[String]) {
        try {
            println(divide(10,2))
            println(divide(10,0))
        }
        catch {
            case dbzex: DivideByZero => println(s"You are trying to divide a number by zero! $dbzex")
            case _: Throwable => println("Something went wrong!")
        }
    }

    def divide(x: Int, y: Int) : Int = {
        println(s"\nDividing $x by $y...")
        if(y == 0) {
            throw new DivideByZero("Cannot divide by Zero.")
        }

        x / y
    }
}