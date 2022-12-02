import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    val fileName = "Day1Puzzle1Input.txt"
    val mostCals = mostCals(fileName)
    println("Most calories: $mostCals")


    val topThreeCals = topThreeCals(fileName)
    topThreeCals.mapIndexed { index, value -> println("Top three at $index: $value") }
    println("Total calories: ${topThreeCals.sum()}")
}

fun mostCals(fileName: String): Int  {
    var maxCals = 0
    var currentElfCals = 0
    try {
        Files.lines(Paths.get(fileName)).use { stream -> stream.forEach {
            if(it == "") {
               if(currentElfCals > maxCals) {
                   maxCals = currentElfCals
               }
                currentElfCals = 0
            }
            else {
                currentElfCals += it.toInt()
            }
        }}
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return maxCals
}

fun topThreeCals(fileName: String): ArrayList<Int>  {
    var topThreeCals = arrayListOf<Int>(0,0,0)
    var currentElfCals = 0
    try {
        Files.lines(Paths.get(fileName)).use { stream -> stream.forEach {
            if(it == "") {
                if(currentElfCals > topThreeCals[0]) {
                    topThreeCals[0] = currentElfCals
                }
                topThreeCals.sort()
                currentElfCals = 0
            }
            else {
                currentElfCals += it.toInt()
            }
        }}
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return topThreeCals
}