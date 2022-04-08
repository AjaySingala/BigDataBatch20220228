package example

object EitherLeftRightDemo {
    def main(args: Array[String]) {
        val result1 = divideWithEither(10,2)
        println(result1)
        val result2 = divideWithEither(10,0)
        println(result2)
    }

    def divideWithEither(x: Int, y: Int): Either[String,Int] = {
        if(y == 0) {
            Left("Cannot divide by Zero!")
        }
        else {
            Right(x/y)
        }
    }
}