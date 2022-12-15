import java.nio.file.Files.readAllLines
import java.nio.file.Paths

// Day 3
// Puzzle Problem:
// --- Day 3: Rucksack Reorganization ---


fun main() {
    val fileName = "Day3Input.txt"
    val rucksacksInput = readAllLines(Paths.get(fileName))
    var ruckSackScoreTotal = 0

    val total =rucksacksInput.map() { rucksackLine: String ->
        val rucksackPair = parseRucksackLine(rucksackLine)

        val ruckSackLetterCountHashMaps: Pair<HashMap<Char, Int>, HashMap<Char, Int>> =
            letterCountHashMaps(rucksackPair.first, rucksackPair.second)

        val sackScore = calculateScore(ruckSackLetterCountHashMaps.first, ruckSackLetterCountHashMaps.second)
        ruckSackScoreTotal += sackScore
    }

    println(total)

    // Part II
    println("Part II")
    var partIIScoreTotal = 0
    for(i in 0 until rucksacksInput.size step 3) {
        val commonLetter = findCommonLetter(rucksacksInput[i], rucksacksInput[i+1], rucksacksInput[i+2])
        println("inputs: ${rucksacksInput[i]} ${rucksacksInput[i+1]} ${rucksacksInput[i+2]}")
        println(commonLetter)
        val letterScore = caseToScore(commonLetter)
        println(letterScore)
        println(partIIScoreTotal)
        partIIScoreTotal += letterScore
    }

    println("Part II Score: $partIIScoreTotal")

}



private fun caseToScore(input: Char): Int {
    // create a regex from a-z
    val regex = Regex("[a-z]")

    return if(regex.matches(input.toString())) {
        addLowercaseLetter(input)
    }else {
        addUppercaseLetter(input)
    }
}

fun parseRucksackLine(input: String): Pair<String, String> {
    val half = input.length / 2
    val firstHalf = input.substring(0, half)
    val secondHalf = input.substring(half, input.length)
    return Pair(firstHalf, secondHalf)
}

private fun addLowercaseLetter(input: Char): Int {
    val result = input.code - 'a'.code + 1
    return result
}

private fun addUppercaseLetter(input: Char): Int {
    val result = input.code - 'A'.code + 27
    return result
}

private fun letterCountHashMaps(first: String, second: String): Pair<HashMap<Char, Int>, HashMap<Char, Int>> {
    val firstMap = HashMap<Char, Int>()
    val secondMap = HashMap<Char, Int>()

    first.forEach { letter ->
        if(secondMap.containsKey(letter)) {
            secondMap[letter] = secondMap[letter]!! + 1
        }else {
            secondMap[letter] = 1
        }
    }

    second.forEach { letter ->
        if(firstMap.containsKey(letter)) {
            firstMap[letter] = firstMap[letter]!! + 1
        }else {
            firstMap[letter] = 1
        }
    }

    return Pair(firstMap, secondMap)
}

// Write a function named findCommonLetter that takes three strings as input and returns a string containing the common letter
// that appears in all three strings. If there is no common letter, return an empty string.
fun findCommonLetter(first: String, second: String, third: String): Char {
    val letterMap = HashMap<Char, Int>()

    // turn strings into a set and find intersection of the three sets
    val firstSet = first.toSet()
    val secondSet = second.toSet()
    val thirdSet = third.toSet()

    val intersection = firstSet.intersect(secondSet).intersect(thirdSet)
    return intersection.first()
}

// Calculate score from two hashmaps
private fun calculateScore(firstMap: HashMap<Char, Int>, secondMap: HashMap<Char, Int>): Int {
    var score = 0
    firstMap.forEach { (key, value) ->
        if(secondMap.containsKey(key)) {
            val overlapValue = minOf(value, secondMap[key]!!)
            println("Overlap value: $overlapValue")
            val scoreForKey = caseToScore(key)
            println("Score for key: $scoreForKey")
            score +=  scoreForKey
            println(score)
        }
    }
    return score
}