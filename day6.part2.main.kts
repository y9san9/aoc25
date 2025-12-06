import java.io.File

fun solve(file: File): Long {
    val input = parse(file)
    val result = input.sumOf { problem -> problem.solve() }
    return result
}

fun parse(file: File): List<Problem> {
    val lines = file.readLines()
    val numbers = lines
        .dropLast(1)
        .map(String::toList)
        .transpose()
        .map { chars -> chars.joinToString(separator = "") }
        .split(String::isBlank)
    val operators = lines.last().splitWords().map { operator ->
        when (operator) {
            "+" -> Operator.Plus
            "*" -> Operator.Times
            else -> error("Unknown")
        }
    }
    return numbers.zip(operators) { numbers, operator ->
        Problem(
            numbers = numbers.map(String::trim).map(String::toLong),
            operator = operator,
        )
    }
}

fun String.splitWords(): List<String> {
    return split(" ").filter(String::isNotEmpty)
}

fun <T> List<List<T>>.transpose(): List<List<T>> {
    return List(first().size) { x ->
        List(size) { y ->
            this[y][x]
        }
    }
}

inline fun <T> List<T>.split(where: (T) -> Boolean): List<List<T>> {
    val list = this
    return buildList {
        var chunk = mutableListOf<T>()
        for (element in list) {
            if (where(element)) {
                add(chunk)
                chunk = mutableListOf()
            } else {
                chunk += element
            }
        }
        add(chunk)
    }
}

data class Problem(
    val numbers: List<Long>,
    val operator: Operator
) {
    fun solve(): Long = numbers.reduce { acc, number ->
        when (operator) {
            is Operator.Plus -> acc + number
            is Operator.Times -> acc * number
        }
    }
}

sealed interface Operator {
    data object Plus : Operator
    data object Times : Operator
}

val example = 3263827L
val exampleFile = File("day6.example")
val file = File("day6.data")

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
