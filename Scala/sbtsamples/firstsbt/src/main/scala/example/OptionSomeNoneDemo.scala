package example

object OptionSomeNoneDemo {
    def main(args: Array[String]) {
        val result1 = divideWithOption(10,2)
        println(result1)
        val result2 = divideWithOption(10,0)
        if(result2 == None) { 
            println("You are trying to divide by Zero!") 
        }
        println(result2)
    }

    def divideWithOption(x: Int, y: Int): Option[Int] = {
        if(y == 0) {
            None
        }
        else {
            Some(x/y)
        }
    }
}