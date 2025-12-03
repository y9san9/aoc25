import java.io.File

fun solve(file: File): Int {
    val input = parse(file)
    TODO("Solve this first")
}

fun parse(file: File): Nothing {
    TODO("No, actually, parse this first")
}

val example = TODO("Paste example")
val exampleFile = File("dayx.example")
val file = File("dayx.data")

val exampleAnswer = solve(exampleFile)
check(exampleAnswer == example) {
    "Expected $example, but was $exampleAnswer"
}
println("Passed example")

if (file.exists()) {
    println("Answer: ${solve(file)}")
} else {
    println("Create ${file.name} for solving...")
}
