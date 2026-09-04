import java.util.Scanner

const val PASSING_GRADE = 70

fun main() {
  val scanner = Scanner(System.`in`)
  println("=== Student Grade Calculator ===")

  print("Enter the student's name: ")
  val name = scanner.nextLine().trim()
  if (name.isEmpty()) {
    println("Error: Student name cannot be empty.")
    return
  }

  var score = -1
  while (score !in 0..100) {
    print("Enter the student's score (0-100): ")
    val input = scanner.nextLine().trim()
    try {
      score = input.toInt()
      if (score !in 0..100) {
        println("Error: Score must be between 0 and 100.")
      }
    } catch (e: NumberFormatException) {
      println("Error: Invalid input. Please enter a numeric value.")
    }
  }

  val letterGrade = when (score) {
    in 92..100 -> "A"
    in 85..91 -> "B"
    in 70..84 -> "C"
    in 60..69 -> "D"
    else -> "F"
  }

  println("=== Grade Report ===")                                                    
  println("Student: $name")                                             
  println("Score: $score")                                             
  println("Grade: $letterGrade")                                        
  println("Status: ${if (score >= PASSING_GRADE ) "PASS" else "FAIL"}")

}
