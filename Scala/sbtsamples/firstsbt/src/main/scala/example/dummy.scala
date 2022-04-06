package example

trait TraitX {
	def showX(): Unit
}

trait TraitY {
	def showY(): Unit
}

class ClassXY extends TraitX with TraitY {
   	def showX(): Unit = {
		println("This is the showX() method...")
	}

	def showY(): Unit = {
		println("This is the showY() method...")
	}
 
}

object Dummy {
    def foo(): Unit = {
        println("foo")
    }
}