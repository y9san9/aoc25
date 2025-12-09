import java.io.File
import kotlin.math.abs

fun solve(file: File): Long {
    val input = parse(file)
    val pairs = combinations(input.coordinates)
    val maxArea = pairs.maxOf { (first, second) ->
        val width = abs(second.x - first.x) + 1
        val height = abs(second.y - first.y) + 1
        width * height
    }
    return maxArea
}

fun parse(file: File): Grid {
    val coordinates = file.readLines()
        .map { line ->
            val (x, y) = line.split(",").map(String::toLong)
            Coordinate(x, y)
        }
        .toSet()
    return Grid(coordinates)
}

fun combinations(coordinates: Set<Coordinate>) = buildSet {
    for (coordinate in coordinates) {
        for (other in coordinates) {
            if (coordinate == other) continue
            val comparator = compareBy(Coordinate::x, Coordinate::y)
            val (first, second) = listOf(coordinate, other)
                .sortedWith(comparator)
            add(first to second)
        }
    }
}

data class Coordinate(val x: Long, val y: Long)

data class Grid(val coordinates: Set<Coordinate>)

val example = 50L
val exampleFile = File("day9.example")
val file = File("day9.data")

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
