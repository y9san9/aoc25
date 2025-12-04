import java.io.File

fun solve(file: File): Int {
    val field = parse(file)
    var accessibleAcc = 0
    while (true) {
        val accessible = field.accessible()
        accessibleAcc += accessible.size
        if (accessible.size == 0) break
        for (coordinate in accessible) {
            field.remove(coordinate)
        }
    }
    return accessibleAcc
}

fun parse(file: File): MutableField {
    val rows = buildSet {
        file.readLines().flatMapIndexed { y, row ->
            row.mapIndexed { x, char ->
                if (char == '@') {
                    val coordinate = Coordinate(x, y)
                    add(coordinate)
                }
            }
        }
    }
    return MutableField(rows.toMutableSet())
}

data class MutableField(val rolls: MutableSet<Coordinate>) {
    fun accessible() = rolls.filter { coordinate ->
        coordinate.neighbors8().count { neighbor -> neighbor in rolls } < 4
    }
    fun remove(coordinate: Coordinate) = rolls.remove(coordinate)
    operator fun contains(coordinate: Coordinate) = coordinate in rolls
}

data class Coordinate(val x: Int, val y: Int) {
    fun neighbors8() = buildList {
        for (dX in -1..1) {
            for (dY in -1..1) {
                if (dX == 0 && dY == 0) continue
                val coordinate = Coordinate(
                    x = x + dX,
                    y = y + dY,
                )
                add(coordinate)
            }
        }
    }
}

sealed interface Cell {
    data class Roll(val coordinate: Coordinate) : Cell
    data class Empty(val coordinate: Coordinate) : Cell
}

val example = 43
val exampleFile = File("day4.example")
val file = File("day4.data")

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
