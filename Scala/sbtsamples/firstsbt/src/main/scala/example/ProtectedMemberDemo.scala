package example

class Super {
	protected def foo() {
		println("This is Super.foo()...")
	}

    def printFoo() {
        foo()
    }
}

class SubClassA extends Super {
	foo()
}


// class Outsider {
// 	(new Super).foo()       // Will not work.
// }

object ProtectedMemberDemo {
    def main(args: Array[String]) {
        val sup = new Super()
        println(sup.printFoo)
        //println(sup.foo)      // Will not work.

        val sub = new SubClassA()
    }
}
