import java.io.File

fun solve(file: File): Int {
    val field = parse(file)
    return field.accessible().size
}

fun parse(file: File): Field {
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
    return Field(rows)
}

data class Field(val rolls: Set<Coordinate>) {
    fun accessible() = rolls.filter { coordinate ->
        coordinate.neighbors8().count { neighbor -> neighbor in rolls } < 4
    }
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

val example = 13
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
