import java.io.File

fun solve(input: File): Int {
    TODO("Solve this first")
}

val example = TODO("Paste example")
val exampleInput = File("day1.example")
val input = File("day1.data")

val exampleAnswer = solve(exampleInput)
check(exampleAnswer == example) {
    "Expected $example, but was $exampleAnswer"
}
println("Passed example")

if (input.exists()) {
    println("Answer: ${solve(input)}")
} else {
    println("Create ${input.name} for solving...")
}
