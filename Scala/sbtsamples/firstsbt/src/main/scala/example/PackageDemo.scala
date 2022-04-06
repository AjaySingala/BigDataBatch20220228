package example

// Either import individually:
// import ecommerce.admin.customers.CustomersTrait
// import ecommerce.admin.customers.AddCustomers
//
// OR Import everything:
import ecommerce.admin.customers._
// 
// Using alias:
// import ecommerce.admin.customers.{CustomersTrait => ct}
// import ecommerce.admin.customers.{AddCustomers => ac}


class CustomersClass extends CustomersTrait {
//class CustomersClass extends ct {           // When using alias.
    def fullName(): Unit = {
        println("Cuystomer's fullname...")
    }
}

object PackageDemo {
    def main(args: Array[String]) {
        val addCust = new AddCustomers
        //val addCust = new ac                // When using alias.
    }
}