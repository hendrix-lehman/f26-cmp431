// java version
// class House {
//   private String address;
//   private int numRooms;

//   public House(String address, int numRooms) {
//     this.address = address;
//     this.numRooms = numRooms;
//     System.out.println("House created: " + address + " with " + numRooms + " rooms.");
//   }

//   public String getAddress() {
//     return address;
//   }

//   public int getNumRooms() {
//     return numRooms;
//   }

//   public void setAddress(String address) {
//     this.address = address;
//   }

//   public void setNumRooms(int numRooms) {
//     this.numRooms = numRooms;
//   }
// }
//
// Typical case of a class is to hold data.
// for example, a Person class holds the person's name and age
// class Person {
  // private String name;
  // private int age;
  //
  // public Person(String name, int age) {
    // this.name = name;
    // this.age = age;
    // }
    // public String getName() {
      // return name;
      // }
      // ...
      // ...
//
//
data class Person(val name: String, val age: Int)

interface Door {
  fun open()
  fun close() = println("Closing the door")
  // fun add(a: Int, b: Int) = a + b
}

open class House(val address: String, val numRooms: Int) {
  // secondary constructor
  constructor(address: String) : this(address, 1) {
    println("Secondary constructor called for House: $address")
  }

  fun add(a :Int, b :Int) = a + b
}

// sub class of our open class House
class ColonialHouse(address: String, numRooms: Int) : House(address, numRooms), Door {
  init {
    println("ColonialHouse created: $address with $numRooms rooms.")

    val sum = add(5, 10)
    println("Sum of 5 and 10 is: $sum")
  }
  override fun open() = println("Opening the colonial house door")
}

// Notice that 'size' has no 'val' or 'var' keyword
class Room(val name: String, size: Int) {
  var size: Int = size
    set(value) {
      require(value > 0) { "Size must be greater than 0" }
      field = value
    }

  init {
    println("Room created: $name with size $size")
  }

  init {
    require(size > 0) { "Size must be greater than 0" }
    println("Another init block for Room: $name")
  }
}

sealed class BuildState
data class UnderConstruction(val progress: Int) : BuildState()
data class Completed(val completionDate: String) : BuildState()

fun main() {
  // val and var
  // val is immutable, var is mutable
  val house = House("123 Main St", 4)
  println(house.address)

  val room = Room("Living Room", 10)
  // room.size = 0 // This will throw an exception because of the require check
  println(room.name)

  val house2 = House("456 Elm St")
  println(house2.numRooms)

  var rooms :Int = 5

  var greeting :String? = "Hello"
  greeting = null
  println(greeting?.uppercase() ?: "Greeting is null")

  var nullableName: String? = "Hendrix"                                     
  nullableName = null // Allowed                                            
                                                                              
  // Direct access on nullable types is prohibited:                         
  // val len = nullableName.length // COMPILE ERROR!
  // println("Length of nullableName: $len") // This will print "Length of nullableName: null"

  val colonialHouse = ColonialHouse("789 Oak St", rooms)
  colonialHouse.open()
}
