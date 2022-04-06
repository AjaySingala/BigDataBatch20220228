package example

/**
  * This is the class definition of a Car class
  * that can be extended further, if required.
  * @param make
  * @param year
  * @param reserved
  */
class Car(val make: String, val year: Int, var reserved: Boolean = false) {
    def reserve(status: Boolean): Unit = {
        reserved = status
    }
}

object CarDemo {
  def main(args: Array[String]) {
      val car1 = new Car("Honda", 2019)
      val car2 = new Car("Audi", 2020)
      val car3 = new Car("BMW", 2021)
      
      println(s"${car1.make} from the year ${car1.year}")
      println(s"${car2.make} from the year ${car2.year}")
      println(s"${car3.make} from the year ${car3.year}")

      val car4 = new Car(year=2020, make="Toyota")      // using named params (keyword identifiers))
      println(s"${car4.make} from the year ${car4.year}")

      val car5 = new Car(year=2020, make="Toyota", reserved = false)      // using named params (keyword identifiers))
      println(s"${car5.make} from the year ${car5.year} is reserved? ${car5.reserved}")
      car5.reserve(true)
      println(s"${car5.make} from the year ${car5.year} is reserved? ${car5.reserved}")

  }
}