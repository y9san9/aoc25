import java.io.File

fun solve(file: File): Long {
    val tachyons = parse(file)
    val result = tachyonTravel(tachyons)
    return result
}

tailrec fun tachyonTravel(
    tachyons: TachyonManifold,
    line: Int = 0,
    beams: Beams = Beams(tachyons.initialPosition),
): Long {
    if (line == tachyons.splitters.size) {
        return beams.map.values.sumOf { beam -> beam.timelines }
    }
    val splitters = tachyons.splitters[line]
    for ((position, beam) in beams.map.toMap()) {
        val isSplit = position in splitters.positions
        if (isSplit) {
            beams.map.remove(position)
            val leftBeam = beams.map.getOrPut(position - 1) { Beam(0) }
            leftBeam.timelines += beam.timelines
            val rightBeam = beams.map.getOrPut(position + 1) { Beam(0) }
            rightBeam.timelines += beam.timelines
        }
    }
    return tachyonTravel(
        tachyons = tachyons,
        line = line + 1,
        beams = beams,
    )
}

fun parse(file: File): TachyonManifold {
    val lines = file.readLines()
    val initialPosition = lines.first().indexOf('S')
    val length = lines.first().length
    val splitters = lines
        .drop(1)
        .map { line ->
            line.mapIndexedNotNull { i, char ->
                if (char == '^') {
                    i
                } else {
                    null
                }
            }
            .toSet()
            .let(::Splitters)
        }
    return TachyonManifold(initialPosition, length, splitters)
}

data class TachyonManifold(
    val initialPosition: Int,
    val length: Int,
    val splitters: List<Splitters>,
)
data class Beam(
    var timelines: Long,
)

fun Beams(initialPosition: Int): Beams {
    val beam = Beam(timelines = 1)
    val map = mutableMapOf(initialPosition to beam)
    return Beams(map)
}
data class Beams(val map: MutableMap<Int, Beam>)
data class Splitters(val positions: Set<Int>)

val example = 40L
val exampleFile = File("day7.example")
val file = File("day7.data")

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
