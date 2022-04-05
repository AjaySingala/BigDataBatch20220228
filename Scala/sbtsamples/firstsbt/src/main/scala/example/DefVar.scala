package example

object DefVar {
    def main(args: Array[String]) {
        val var1: Int = 25
        val message: String = "This is my first variable declaration in Scala."

        println(var1); 
        println(message)

        val i = 10
        val j = 20
        val k = add(i, j)
        println(k)   
    }

    def add(x: Int, y: Int): Int = {
        return x  + y
    }
}
