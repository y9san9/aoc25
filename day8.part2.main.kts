import java.io.File
import kotlin.math.sqrt

fun solve(
    file: File,
    example: Boolean,
): Int {
    val boxes = parse(file)
    val pairs = boxes
        .flatMapTo(mutableSetOf()) { box ->
            (boxes - box).map { other ->
                val comparator = Comparator<Box> { first, second ->
                    first.x.compareTo(second.x)
                }.thenComparing { first, second ->
                    first.y.compareTo(second.y)
                }.thenComparing { first, second ->
                    first.z.compareTo(second.z)
                }
                val (first, second) = listOf(box, other).sortedWith(comparator)
                first to second
            }
        }
        .sortedBy { (box, other) -> box.distanceSquared(other) }
    val limit = if (example) 10 else 1_000
    return connect(
        pairs = pairs.toMutableList(),
        circuits = boxes.map(::Circuit).toMutableSet(),
    )
}

tailrec fun connect(
    pairs: MutableList<Pair<Box, Box>>,
    circuits: MutableSet<Circuit>,
): Int {
    if (pairs.isEmpty()) error("Should not happen.")

    val (box, nearestBox) = pairs.removeFirst()

    val boxCircuit = circuits.first { circuit -> box in circuit }
    val nearestBoxCircuit = circuits.first { circuit -> nearestBox in circuit }

    val finalCircuit = if (boxCircuit !== nearestBoxCircuit) {
        Circuit(boxes = boxCircuit.boxes + nearestBoxCircuit.boxes)
    } else {
        boxCircuit
    }
    circuits -= boxCircuit
    circuits -= nearestBoxCircuit
    circuits += finalCircuit

    if (circuits.size == 1) {
        return box.x * nearestBox.x
    }

    return connect(
        pairs = pairs,
        circuits = circuits,
    )
}

fun parse(file: File): Set<Box> {
    return file.readLines()
        .map { line ->
            val (x, y, z) = line.split(",").map(String::toInt)
            Box(x, y, z)
        }
        .toSet()
}

data class CircuitId(val int: Int)

fun Circuit(box: Box): Circuit {
    return Circuit(setOf(box))
}

data class Circuit(
    val boxes: Set<Box>,
) {
    operator fun contains(box: Box) = box in boxes
}

data class Box(val x: Int, val y: Int, val z: Int) {
    fun distanceSquared(box: Box): Long {
        val xSquared = (x - box.x).toLong() * (x - box.x).toLong()
        val ySquared = (y - box.y).toLong() * (y - box.y).toLong()
        val zSquared = (z - box.z).toLong() * (z - box.z).toLong()
        return xSquared + ySquared + zSquared
    }
}

val example = 25272
val exampleFile = File("day8.example")
val file = File("day8.data")

val exampleAnswer = solve(exampleFile, example = true)
check(exampleAnswer == example) {
    "Expected $example, but was $exampleAnswer"
}
println("Passed example")

if (file.exists()) {
    println("Answer: ${solve(file, example = false)}")
} else {
    println("Create ${file.name} for solving...")
}
