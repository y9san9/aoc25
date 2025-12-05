import java.io.File

fun solve(file: File): Int {
    val input = parse(file)
    val result = input.ingridients.count { ingridient ->
        input.fresh.any { fresh -> ingridient in fresh }
    }
    return result
}

fun parse(file: File): Inventory {
    val (rawFresh, rawIngridients) = file.readText().split("\n\n")
    val fresh = rawFresh.split("\n")
        .map { fresh ->
            val (start, end) = fresh.split("-").map(String::toLong)
            Fresh(start..end)
        }
    val ingridients = rawIngridients.split("\n")
        .filter(String::isNotEmpty)
        .map { ingridient -> Ingridient(ingridient.toLong()) }
    return Inventory(fresh, ingridients)
}

data class Inventory(val fresh: List<Fresh>, val ingridients: List<Ingridient>)

data class Fresh(val range: LongRange) {
    operator fun contains(ingridient: Ingridient) = ingridient.id in range
}

data class Ingridient(val id: Long)

val example = 3
val exampleFile = File("day5.example")
val file = File("day5.data")

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
