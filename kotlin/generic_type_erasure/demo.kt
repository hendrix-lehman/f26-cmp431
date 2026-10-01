class Box<T>(val value: T)

// Compilation error: Cannot check for instance of erased type: T
// cannot check for instance of erased type 'T (of fun <T> checkType)'.
// fun <T> checkType(value: Any) {
//   if (value is T) {
//     println("Is of type ${T::class.simpleName}")
//   }
// }

inline fun <reified T> checkTypeReified(value: Any) {
  if (value is T) {
    println("Is of type ${T::class.simpleName}")
  }
}

inline fun <reified T> List<Any>.filterIsInstance(): List<T> {
  val filteredList = mutableListOf<T>()
  for (element in this) {
    if (element is T) {
      filteredList.add(element)
    }
  }
  return filteredList
}

inline fun <reified T> getTypeName(): String {
  return T::class.simpleName ?: "Unknown"
}

fun main() {
  val box1: Box<Int> = Box(42)
  val box2: Box<String> = Box("Hello, World!")

  println("Box1 contains: ${box1.value}")
  println("Box2 contains: ${box2.value}")

  checkTypeReified<Int>(box1.value) // Output: Is of type Int
  checkTypeReified<String>(box2.value) // Output: Is of type String
  checkTypeReified<Boolean>(false) // Output: Is of type Double

  val mixedList: List<Any> = listOf("Kotlin", 1, "Compiler", 2.0, "Generics", 3, true, box1, box2)

  val stringList: List<String> = mixedList.filterIsInstance<String>()
  println("Filtered String List: $stringList") // Output: Filtered String List: [Kotlin, Compiler, Generics]

  val intList: List<Int> = mixedList.filterIsInstance<Int>()
  println("Filtered Int List: $intList") // Output: Filtered Int List: [1, 3]
  
  val boxList: List<Box<*>> = mixedList.filterIsInstance<Box<*>>()
  println("Filtered Box List: $boxList") // Output: Filtered Box List: [Box(value=42), Box(value=Hello, World!)]

  println("Type name of Int: ${getTypeName<Int>()}") // Output: Type name of Int: Int
  println("Type name of String: ${getTypeName<String>()}") // Output: Type name of String: String
  println("Type name of Box: ${getTypeName<Box<*>>()}") // Output: Type name of Box: Box

}
