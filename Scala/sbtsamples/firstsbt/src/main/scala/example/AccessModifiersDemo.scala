package example

class Outer {
    private def foo() {
        println("This is Inner.foo()...")
    }

    private val name: String = "John Smith"

    def printFoo = {
        foo
    }

    def printName() =  {
        s"My name is $name"
    }
}

object AccessModifiersDemo {
    def main(args: Array[String]) {
        val out = new Outer()
        println(out.printFoo)
        println(out.printName)
    }
}