package example

trait TraitA {
	def showA(): Unit
}

trait TraitB {
	def showB(): Unit
}

class MyClass extends TraitA with TraitB {
	def showA(): Unit = {
		println("This is the showA() method...")
	}

	def showB(): Unit = {
		println("This is the showB() method...")
	}

    def display(): Unit = {
        println("This is the display() method of MyClass...")
    }
}

// Singleton!!!
object Traits {
    def main(args: Array[String]) {
        val objClass = new MyClass()
        objClass.showA()
        objClass.showB()
        objClass.display()
    }
}
