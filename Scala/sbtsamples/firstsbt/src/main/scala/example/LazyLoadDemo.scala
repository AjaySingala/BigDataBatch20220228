package example

object LazyLoadDemo {
    def main(args: Array[String]) {
        lazy val foo = {
            println("This is initialization for the first time...")
            200
        }

        // Round 1
        println("This is the first print statement...")
        println(foo)

        // Round 2
        println()
        println("This is the second print statement...")
        println(foo)

    }
}