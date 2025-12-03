import java.io.File

val numberOfBanks = 12

fun solve(file: File): Long {
    val lines = parse(file)
    val result = lines.sumOf { line ->
        findMaxJoltage(numberOfBanks, line.banks).long
    }
    return result
}

tailrec fun findMaxJoltage(
    numberOfBanks: Int,
    banks: List<Bank>,
    acc: Joltage = Joltage.Zero,
): Joltage {
    val max = banks
        .dropLast(numberOfBanks - 1)
        .maxBy(Bank::joltage)
    val nextAcc = acc + max.joltage
    if (numberOfBanks == 1) {
        return nextAcc
    }
    val index = banks.indexOf(max)
    return findMaxJoltage(
        numberOfBanks = numberOfBanks - 1,
        banks = banks.drop(index + 1),
        acc = nextAcc,
    )
}

fun parse(file: File) = file.readLines().map { line ->
    val banks = line
        .map { digit -> Joltage(digit.digitToInt().toLong()) }
        .map(::Bank)
    BankLine(banks)
}

data class Bank(val joltage: Joltage)

data class BankLine(val banks: List<Bank>)

data class Joltage(val long: Long) : Comparable<Joltage> {
    operator fun plus(other: Joltage) = Joltage(long * 10 + other.long)
    override fun compareTo(other: Joltage) = long.compareTo(other.long)
    companion object {
        val Zero: Joltage = Joltage(0)
    }
}

val example = 3121910778619L
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
