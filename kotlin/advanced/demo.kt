interface Factory<T> {
  fun create(name: String): T
}

data class Product(val code: String, var price: Double) {
  var quantity: Int = 0
}

class User private constructor(val name: String) {
  companion object : Factory<User> {
    override fun create(name: String): User {
      return User(name)
    }
  }
}

fun main() {
  val product = Product("A123", 10.0)
  product.quantity = -15
  println("Product code: ${product.code}, Price: ${product.price}, Quantity: ${product.quantity}")

  val (code, price) = product
  println("Destructured Product - Code: $code, Price: $price")

  val code1 = product.component1()
  val price1 = product.component2()
  println("Component functions - Code: $code1, Price: $price1")

  val quantity = product.quantity
  println("Quantity: $quantity")
  val positiveQty = quantity.let { it > 0 } ?: 0
  println("Is quantity positive? $positiveQty")

  // do not compile because of private constructor
  // val user = User("Alice")

  // to create an User object, we can use the companion object factory method
  val user = User.create("Alice")
  println("User name: ${user.name}")
}
