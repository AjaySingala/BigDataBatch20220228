package example

object add {
  def main(args: Array[String]) {
    val result = addNumbers(10, 20)
    println(result)
  }

  def addNumbers(x: Int, y: Int): Int = {
    return x + y
  }
}
