import java.io.File

fun solve(file: File): Int {
    val lines = parse(file)
    val result = lines.sumOf { line ->
        val leftBank = line.banks.dropLast(1).maxBy(Bank::joltage)
        val index = line.banks.indexOf(leftBank)
        val rightBank = line.banks.drop(index + 1).maxBy(Bank::joltage)
        (leftBank.joltage + rightBank.joltage).int
    }
    return result
}

fun parse(file: File) = file.readLines().map { line ->
    val banks = line
        .map { digit -> Joltage(digit.digitToInt()) }
        .map(::Bank)
    BankLine(banks)
}

data class Bank(val joltage: Joltage)

data class BankLine(val banks: List<Bank>)

data class Joltage(val int: Int) : Comparable<Joltage> {
    operator fun plus(other: Joltage) = Joltage(int * 10 + other.int)
    override fun compareTo(other: Joltage) = int.compareTo(other.int)
}


val example = 357
val exampleInput = File("day3.example")
val input = File("day3.data")

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
