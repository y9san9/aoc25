import java.io.File

fun solve(input: File): Int {
    val instructions = readRotationsFrom(input)
    var dial = DialValue()
    var atZero = 0
    for (instruction in instructions) {
        dial = dial.apply(instruction)
        if (dial.value == 0) {
            atZero++
        }
    }
    return atZero
}

sealed interface Rotation {
    data class Left(val amount: Int) : Rotation
    data class Right(val amount: Int) : Rotation
}

fun readRotationsFrom(file: File): List<Rotation> {
    return file.readLines().map { line ->
        val command = line[0]
        val number = line.drop(1).toInt()
        when (command) {
            'L' -> Rotation.Left(number)
            'R' -> Rotation.Right(number)
            else -> error("Unknown command")
        }
    }
}

data class DialValue(val value: Int = 50) {
    init {
        require(value in 0..99)
    }

    fun apply(rotation: Rotation): DialValue {
        return when (rotation) {
            is Rotation.Left -> minus(rotation.amount)
            is Rotation.Right -> plus(rotation.amount)
        }
    }

    operator fun plus(value: Int): DialValue {
        require(value > 0)
        val ranged = value % 100
        val value = (this.value + ranged) % 100
        return DialValue(value)
    }

    operator fun minus(value: Int): DialValue {
        require(value > 0)
        val ranged = value % 100
        val value = (100 + this.value - ranged) % 100
        return DialValue(value)
    }
}

val example = 3
val exampleInput = File("day1.example")
val input = File("day1.data")

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
