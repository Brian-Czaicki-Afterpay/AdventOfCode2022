import java.io.File

// make a hashmap where a: rock, b: paper, c: scissors
val inputToRockPaperScissors = hashMapOf<String, String>("A" to "ROCK", "B" to "PAPER", "C" to "SCISSORS")

// make a hashmap where x: rock, y: paper, z: scissors
val outputToRockPaperScissors = hashMapOf<String, String>("X" to "ROCK", "Y" to "PAPER", "Z" to "SCISSORS")

// make a hashmap where rock: 1, paper: 2, scissors: 3
val rockPaperScissorsToNumber = hashMapOf<String, Int>("ROCK" to 1, "PAPER" to 2, "SCISSORS" to 3)

// make a hashmap where rock: paper, paper: scissors, scissors: rock
val rockPaperScissorsToLoser = hashMapOf<String, String>("ROCK" to "PAPER", "PAPER" to "SCISSORS", "SCISSORS" to "ROCK")

// make a hashmap where rock: paper, paper: scissors, scissors: rock
val rockPaperScissorsToWinner = hashMapOf<String, String>("ROCK" to "SCISSORS", "PAPER" to "ROCK", "SCISSORS" to "PAPER")

// make a hashmap where win:6, tie:3, lose:0
val resultToScore = hashMapOf<String, Int>("WIN" to 6, "TIE" to 3, "LOSE" to 0)

val outputToWinLoseTie = hashMapOf<String, String>("X" to "LOSE", "Y" to "TIE", "Z" to "WIN")

// Write a function to determine the winner of a game of rock paper scissors
fun determineWinner(input: String, output: String): String {
    // get the input and output as rock paper scissors
    val inputAsRockPaperScissors = inputToRockPaperScissors[input]
    val outputAsRockPaperScissors = outputToRockPaperScissors[output]

    // get the input and output as numbers
    val outputAsNumber = rockPaperScissorsToNumber[outputAsRockPaperScissors]

    // determine the winner
    if(inputAsRockPaperScissors == outputAsRockPaperScissors) {
        return "TIE"
    }
    else if(rockPaperScissorsToLoser[inputAsRockPaperScissors] == outputAsRockPaperScissors) {
        return "WIN"
    }
    else {
        return "LOSE"
    }
}

// Write a function to determine the score of a game of rock paper scissors
fun calcScore(output: String, result: String): Int {
    val playScore = rockPaperScissorsToNumber[outputToRockPaperScissors[output]]
    val resultScore = resultToScore[result]

    return resultScore!! + playScore!!;
}


fun calcScoreFromResult(input: String, output: String): Int {
    val result = outputToWinLoseTie[output]

    val resultScore = resultToScore[result!!]

    if(result == "TIE"){
        return resultScore!! + rockPaperScissorsToNumber[inputToRockPaperScissors[input]]!!
    }else if(result == "WIN") {
        return resultScore!! + rockPaperScissorsToNumber[rockPaperScissorsToLoser[inputToRockPaperScissors[input]]]!!
    }else {
        return resultScore!! + rockPaperScissorsToNumber[rockPaperScissorsToWinner[inputToRockPaperScissors[input]]]!!
    }
}

fun main(args: Array<String>) {
    // Read the input file
    val fileName = "Day2Input.txt"
    val lines = File(fileName).readLines()

    // Create a list of the input and output
    val input = lines.map { it.split(" ")[0] }
    val output = lines.map { it.split(" ")[1] }

    // Create a list of the results
    val results = input
        .zip(output)
        .map { determineWinner(it.first, it.second) }

    // Create a list of the scores
    val totalScore = output
        .zip(results)
        .map { calcScore(it.first, it.second) }
        .sum()

    println("Total score: ${totalScore}")


    println("Part II")
    val totalScore2 = input
        .zip(output)
        .map { calcScoreFromResult(it.first, it.second) }
        .sum()

    println("Total score: ${totalScore2}")
}

