import java.io.File

fun main() {
    val fileName = "Day6Input.txt"

    // read lines from file with trailing blank space
    val lines = File(fileName).readLines()
    val line = lines[0]

    // Part 1
    // Given a string find the first sequence of four characters that are all distinct
    // and return the index of the first character in that sequence
    val part1 = findFirstDistinctSequence(line, 4)

    println(part1)

    val part2 = findFirstDistinctSequence(line, 14)

    println(part2)


}

private fun findFirstDistinctSequence(line: String, sequenceLength: Int): Int {
    val distinctSequences = line.windowed(sequenceLength, 1).filter { it.toSet().size == sequenceLength }
    return line.indexOf(distinctSequences.first()) + sequenceLength
}

