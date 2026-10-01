fun String.encrypt(): String = "*${this}*" 

fun main() {
  val original = "Hello, World!"
  val encrypted = original.encrypt()
  println("Original: $original")
  println("Encrypted: $encrypted")
}
