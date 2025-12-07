import java.io.File

fun solve(file: File): Int {
    val tachyons = parse(file)
    val result = tachyonTravel(tachyons)
    return result
}

tailrec fun tachyonTravel(
    tachyons: TachyonManifold,
    line: Int = 0,
    beams: Set<Int> = setOf(tachyons.initialPosition),
    splits: Int = 0,
): Int {
    if (line == tachyons.splitters.size) {
        return splits
    }
    val splitters = tachyons.splitters[line]
    val nextBeams = mutableSetOf<Int>()
    val nextSplits = buildNextBeams(
        iterator = beams.iterator(),
        splitters = tachyons.splitters[line],
        nextBeams = nextBeams,
        splits = splits,
    )
    return tachyonTravel(
        tachyons = tachyons,
        line = line + 1,
        beams = nextBeams,
        splits = nextSplits,
    )
}

tailrec fun buildNextBeams(
    iterator: Iterator<Int>,
    splitters: Splitters,
    nextBeams: MutableSet<Int>,
    splits: Int,
): Int {
    if (!iterator.hasNext()) return splits
    val beam = iterator.next()
    val split = beam in splitters.positions
    if (split) {
        nextBeams += beam - 1
        nextBeams += beam + 1
    } else {
        nextBeams += beam
    }
    return buildNextBeams(
        iterator = iterator,
        splitters = splitters,
        nextBeams = nextBeams,
        splits = if (split) splits + 1 else splits,
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
data class Splitters(val positions: Set<Int>)

val example = 21
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
