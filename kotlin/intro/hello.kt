fun main() {
  // in java
  // final int number = 42;
  val number = 42 // DATA TYPE: is inferred as Int
  // 42 int in bytes
  // unsigned 8-bit integer (0-255)
  // 0000 0000 -> 0
  // 0000 0001 -> 1
  // 0000 0010 -> 2
  // ...
  // 1111 1111 -> 255
  //
  // signed 8-bit integer (-128 to 127)
  // 0000 0000 -> 0
  // 0000 0001 -> 1
  // ...
  // 1111 1111 -> -1
  // 1000 0000 -> -128

  // number = 10 PROHIBITED: 'val' cannot be reassigned
  //
  var age :Int = 30
  age = 31 // ALLOWED: 'var' can be reassigned
  println("Hello, World!")

  // numeric operations & explicit conversion
  val n :Byte = 0b01111111 // 127
  val m :Byte = 0b00000001 // 1
  val total :Int = n + m // ERROR: Type mismatch: inferred type is Int but Byte was expected
  println("Total: $total")

  greeUser(greeting = "Hi")

}

fun greeUser(name :String = "Alice", greeting :String = "Hello") {
  println("$greeting, $name!")
}

// variable: a way to store data in memory 
// what "data"? text, number, condition (true/false), object, etc.
