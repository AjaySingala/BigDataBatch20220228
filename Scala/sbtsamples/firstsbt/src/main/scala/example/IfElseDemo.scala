package example

object IfElseDemo {
    def main(args: Array[String]) {
        printStatus("New")
        printStatus("Approved")
        printStatus("Unknown Status")

        printStatusUsingMatchExpression("New")
        printStatusUsingMatchExpression("Approved")
        printStatusUsingMatchExpression("Unknown Status")
    
        chkDOW("MON")
        chkDOW("FRI")
        chkDOW("SAT")
    }

    def chkDOW(day: String) {
        println(s"\nChecking Day of the week $day...")
        day match {
            case "MON" | "TUE" | "WED" | "THU" |"FRI" => println("Weekday!")
            case "SAT" | "SUN" => println("Weekend!!!")
        }
    }

    def printStatusUsingMatchExpression(status: String) {
        println()
        println(s"Checking status $status using Match Expression...")
        status match {
            case "New" => {
                println("A new document...")
            }
            case "In Progress" => println("The document is being reviewed...")
            case "Approved" => println("The document is Approved...")
            case "Rejected" => println("The document is Rejected...")
            case _ => println("Status unknown!!!")
        }
    }

    def printStatus(status:String ) {
        println()
        println(s"Checking status $status...")
        if(status == "New") {
            println("Just created...")
        }
        else if(status == "In Progress") {
            println("In review....")
        }
        else if(status == "Approved") {
            println("Approved")
        }
        else if(status == "Rejected") {
            println("Rejected")
        }
        else {
            println("Invalid status")
        }        
    }
}