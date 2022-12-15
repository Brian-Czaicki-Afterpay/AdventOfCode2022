import java.io.File

fun main() {
    val fileName = "Day5Input.txt"

    // read lines from file with trailing blank space
    val lines = File(fileName).readLines()

    val stacks = createStacks(lines)

    val instructions = getInstructions(lines)

//    val part1 = processInstructions(stacks, instructions, true)
//
//    println(part1)

    val part2 = processInstructions(stacks, instructions, false)
    println(part2)
}

private fun createStacks(input: List<String>): List<MutableList<Char>> {
    val stackRows = input.takeWhile { it.contains('[') }
    return (1..stackRows.last().length step 4).map { index ->
        stackRows
            .mapNotNull { it.getOrNull(index) }
            .filter { it.isUpperCase() }
            .toMutableList()
    }
}

private fun getInstructions(lines: List<String>): List<String> {
    val blankLineIndex = lines.indexOfFirst { it.isBlank() }
    return lines.subList(blankLineIndex + 1, lines.size)
}

private fun parseInstruction(instruction: String): Triple<Int, Int, Int> {
    val splitLine = instruction.split(" ")
    return Triple(
        splitLine[1].toInt(),
        splitLine[3].toInt(),
        splitLine[5].toInt()
    )
}

private fun processInstructions(stacks: List<MutableList<Char>>, instructions: List<String>, reversed: Boolean): String {

    instructions.map { instruction ->
        val (amount, startingStack, endingStack) = parseInstruction(instruction)
        val stackToMove = stacks[startingStack - 1]
        val stackToMoveTo = stacks[endingStack - 1]
        val itemsToMove = stackToMove.take(amount)
        repeat(amount) {stackToMove.removeFirst() }
        if(reversed) {
            stackToMoveTo.addAll(0,itemsToMove.reversed())
        }else {
            stackToMoveTo.addAll(0, itemsToMove)
        }
    }

    // Take the letter from the first element in each stack and join them together into a string
    return stacks.map { it.firstOrNull() }.joinToString("")
}
