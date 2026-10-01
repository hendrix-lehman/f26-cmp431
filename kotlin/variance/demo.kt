// T is covariant (out) or return subtype of T can be used in place of T
interface Producer<out T> {
  fun produce(): T 
}

// T is contravariant (in) or accept supertype of T can be used in place of T
interface Consumer<in T> {
  fun consume(item: T)
}

open class Animal

class Dog : Animal()

fun <T> copy(from: Array<out T>, to: Array<in T>) {
  for (i in from.indices) {
    to[i] = from[i]
  }
}

fun main() {

  val dogProducer: Producer<Dog> = object : Producer<Dog> {
    override fun produce(): Dog = Dog()
  }

  val animalProducer: Producer<Animal> = dogProducer // Covariance allows this assignment
  val animal: Animal = animalProducer.produce() // This works because produce() returns an Animal

  // show the type of animal
  println("Produced animal is of type: ${animal::class.simpleName}") // Output: Produced animal is of type: Dog

  val animalConsumer: Consumer<Animal> = object : Consumer<Animal> {
    override fun consume(item: Animal) {
      println("Consuming an animal of type: ${item::class.simpleName}")
    }
  }

  // Allowing a Consumer<Animal> to be assigned to a Consumer<Dog> variable
  val dogConsumer: Consumer<Dog> = animalConsumer // Contravariance allows this assignment
  dogConsumer.consume(Dog()) // This works because consume() accepts a Dog, which is a subtype of Animal

  val ints: Array<Int> = arrayOf(1, 2, 3, 4, 5)
  val objects: Array<Any> = arrayOf(Any(), "abc", true, 1, 2.0)

  println("Before: Original ints: ${ints.joinToString()}") // Output: Original ints: 1, 2, 3, 4, 5
  println("Before: Original objects: ${objects.joinToString()}") // Output: Original objects: java.lang.Object@<hashcode>, abc, true, 1, 2.0
  copy(ints, objects) // This works because Int is a subtype of Any
  println("After: Original objects: ${ints.joinToString()}") // Output: Original objects: 1, 2, 3, 4, 5
  println("After: Copied elements from ints to objects: ${objects.joinToString()}") // Output: Copied elements from ints to objects: 1, 2, 3, 4, 5

}
