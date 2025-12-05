import java.io.File
import kotlin.math.max
import kotlin.math.min

fun solve(file: File): Long {
    val input = parse(file)
    val fresh = input.fresh.sortedBy(Fresh::start)
    return sum(fresh)
}

tailrec fun sum(
    list: List<Fresh>,
    i: Int = 0,
    startId: Long = 0,
    acc: Long = 0
): Long {
    val fresh = list.getOrNull(i) ?: return acc
    val start = fresh.start.coerceAtLeast(startId)
    val newIds = rangeLength(start, fresh.end).coerceAtLeast(0)
    val nextStartId = (fresh.end + 1).coerceAtLeast(startId)
    return sum(
        list = list,
        i = i + 1,
        startId = nextStartId,
        acc = acc + newIds,
    )
}

fun rangeLength(start: Long, end: Long): Long {
    return end - start + 1
}

fun parse(file: File): Inventory {
    val (rawFresh) = file.readText().split("\n\n")
    val fresh = rawFresh.split("\n")
        .map { fresh ->
            val (start, end) = fresh.split("-").map(String::toLong)
            Fresh(start, end)
        }
    return Inventory(fresh)
}

data class Inventory(val fresh: List<Fresh>)
data class Fresh(var start: Long, var end: Long)

val example = 14L
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
