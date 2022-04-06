package example

import scala.util.control._
import scala.util.control.Breaks._

// Singleton "object".
object BreakDemo {
  def main(args: Array[String]) {
    demo1()
    demo2()
    demo3()
    println()
  }

  def demo3() {
    println("\nDemo 3 - Breaking Nested Loops...")
    val nums1 = List(1,2,3,4,5)
    val nums2 = List(11,12,13)

    val outer = new Breaks
    val inner = new Breaks

    outer.breakable {
        for(a <- nums1) {
            println(s"$a")

            inner.breakable {
                for(b <- nums2) {
                    println(s"\t$b")

                    if( b % 2 == 0) {
                        inner.break
                    }
                }
            }
        }
    }
  }

  def demo2() {
    println("\nDemo 2...")
    // This requires: import scala.util.control.Breaks._
    breakable {
      for (i <- 1 to 10) {
        if (i == 6) {
          break
        }
        print(s"$i: ")
      }
    }
  }

  def demo1() {
    println("\nDemo 1...")
    var a = 0
    val nums = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    var myLoop = new Breaks

    myLoop.breakable {
      for (a <- nums) {
        print(s"$a: ")

        if (a > 5) {
          myLoop.break
        }
      }
    }
  }
}
