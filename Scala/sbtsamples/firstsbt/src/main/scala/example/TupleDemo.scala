package example

// This is a single line comment.

/*
    This demo source file shows
    how to return multiple values
    from a function using a Tuple.
*/

object TupleDemo {
    def main(args: Array[String]) {
        val result = foo()
        result.productIterator.foreach(println)
    }

    /**
      * This method returns a tuple
      * that can have multiple values.
      * @return Tuple.
      */
    def foo() = {
        val data = (25, "John Smith", "Dallas", "TX")

        data
    }
}