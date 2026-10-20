interface Engine {
  fun start()
}

class EngineA : Engine {
  override fun start() {
    println("EngineA is starting...")
  }
} 

class EngineB : Engine {
  override fun start() {
    println("EngineB is starting...")
  }
}

class Car(val engine: Engine) {

  fun start() {
    println("Car is starting...")
    engine.start()
  }

  companion object {
    fun create(engineType: String): Car {
      val engine = when (engineType) {
        "A" -> EngineA()
        "B" -> EngineB()
        else -> throw IllegalArgumentException("Unknown engine type")
      }
      return Car(engine)
    }
  }
}



fun main() {

  // val car = Car(EngineB())
  val car = Car.create("X")
  car.start()

}
