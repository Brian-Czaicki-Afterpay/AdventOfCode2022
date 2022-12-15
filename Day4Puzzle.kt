import java.io.File

fun main () {
    val fileName = "Day4Input.txt"
    val lines = File(fileName).readLines()

    val numberSets = lines.map { parseLine(it)}

    val total = numberSets.sumOf {
        doNumberSetsCompletelyOverlap(it.first, it.second)
    }

    println("Total: $total")


    println("Part II")
    val part2Total = numberSets.sumOf {
        doNumberSetsIntersect(it.first, it.second)
    }

    println("Total: $part2Total")
}

fun doNumberSetsCompletelyOverlap(numberSet1: Set<Int>, numberSet2: Set<Int>): Int {
    val numberSet1MinusNumberSet2 = numberSet1.minus(numberSet2)
    val numberSet2MinusNumberSet1 = numberSet2.minus(numberSet1)

    return if(numberSet1MinusNumberSet2.isEmpty() || numberSet2MinusNumberSet1.isEmpty()) {
        1
    }else {
        0
    }
}

fun doNumberSetsIntersect(numberSet1: Set<Int>, numberSet2: Set<Int>): Int {
    return if(numberSet1.intersect(numberSet2).isEmpty()) {
        0
    }else {
        1
    }
}


fun parseLine(line: String): Pair<Set<Int>, Set<Int>> {
    val numbers = line
        .split(",")
        .map { it.split("-") }
        .map { it[0].toInt()..it[1].toInt() }
    return Pair(numbers[0].toSet(), numbers[1].toSet())
}