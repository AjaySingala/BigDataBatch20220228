package example

case class Person(firstName: String, lastName: String, title: String)

object FilterDemo {
    def main(args: Array[String]) {
        val people = Seq(
            Person("John", "Smith", "Mr."),
            Person("Mary", "Jane", "Ms."),
            Person("Joe", "Caleb", "Mr.")
        )

        val labels = people.map( person => s"${person.title} ${person.firstName} ${person.lastName}")
        println(labels)

        val peopleThatStateWithJ =people.filter(_.firstName.startsWith("J"))
        println(peopleThatStateWithJ)
    }
}