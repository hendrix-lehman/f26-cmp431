fun main() {

  val numbers = listOf(1, 2, 2, 2, 3, 3, 3, 4, 5)
  // numbers.add(7) // compile error: Unresolved reference: add

  println(numbers[0]) // read first element by using the index

  // numbers[0] = 7 // compile error: Val cannot be reassigned
  // println(numbers[-5]) // runtime error: IndexOutOfBoundsException

  val mutableNumbers = mutableListOf(1, 2, 3, 4, 5)
  mutableNumbers.add(7) // add an element to the list
  println(mutableNumbers[5]) // read first element by using the index

  mutableNumbers[0] = 8 // reassign the first element
  println(mutableNumbers[0]) // read first element by using the index

  val operators = setOf("+", "-", "*", "/", "+", "*") // duplicates will be silently removed

  // operators.add("%") // compile error: Unresolved reference: add
  //
  println(operators.elementAt(0)) // read first element by using the index
  println(operators.first()) // read first element by using the index
  // println(operators.elementAt(10)) // runtime error: IndexOutOfBoundsException
  println(operators.elementAtOrNull(10)) // returns null if the index is out of bounds

  // println(operators.distinct()) // returns a set with distinct elements

  val seen = mutableSetOf<Int>()
  val duplicates = numbers.filter { !seen.add(it) } // filter the elements that are duplicates
    .toSet()

  println(duplicates) // print the duplicates

  operators.forEach { println(it) } // iterate over the elements of the set

  // Maps
  val prices = mapOf("Juice" to 1.99, "Banana" to 0.49, "Milk" to 3.19)

  println(prices["Juice"]) // read the value by using the key
  println(prices.getValue("Banana")) // read the value by using the key
  

}
