import java.io.File

fun solve(input: File): Long {
    val ranges = input
        .readText().trim()
        .split(',')
        .map { range ->
            val (start, end) = range.split('-').map { id -> Id(id.toLong()) }
            Range(start, end)
        }
    return ranges.sumOf { range ->
        range.sumOf { id ->
            if (id.isValid) 0 else id.long
        }
    }
}

data class Id(val long: Long) {
    val isValid: Boolean
        get() {
            val string = long.toString()
            for (rangeLength in 1..string.length / 2) {
                if (string.length % rangeLength != 0) continue
                if (string.chunked(rangeLength).distinct().size == 1) {
                    return false
                }
            }
            return true
        }
}

data class Range(val start: Id, val end: Id) : Iterable<Id> {
    override operator fun iterator() = (start.long..end.long)
        .map(::Id)
        .iterator()
}

val example = 4174379265L
val exampleInput = File("day2.example")
val input = File("day2.data")

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
