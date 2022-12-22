import java.io.File
import java.lang.Integer.min

fun main() {
    val lines = File("Day8Input.txt").readLines()
    val treeMatrix = parseLinesToMatrix(lines)
    var visibilityMatrix :List<MutableList<Int>> = Array(treeMatrix.size) { Array(treeMatrix[0].size) { 0 }.toMutableList() }.toMutableList()

    val updatedVisibilityMatrix = getMinHeightForVisibility(treeMatrix, visibilityMatrix)

    // find the total of all values where the index of the tree matrix is greater or equal to the value of the visibility matrix
    var total = 0
    for(i in treeMatrix.indices) {
        for (j in treeMatrix[i].indices) {
            if (treeMatrix[i][j] > updatedVisibilityMatrix[i][j]) {
                total += 1
            }
        }
    }
    println("Total: $total")

    // Part II
    // Loop through the matrix and get the score of each index
    var scoreMatrix = Array(treeMatrix.size) { Array(treeMatrix[0].size) { 0 }.toMutableList() }.toMutableList()
    for(i in treeMatrix.indices) {
        for (j in treeMatrix[i].indices) {
            scoreMatrix[i][j] = getScoreForIndex(treeMatrix, i, j)
        }
    }

    // Find the max score
    scoreMatrix.map { it.max() }.max()?.let { println("Max score: $it") }

}

fun parseLinesToMatrix(lines: List<String>): List<List<Int>> {
    val matrix = mutableListOf<List<Int>>()
    for (line in lines) {
        val row = line.toCharArray().map { it.toString().toInt() }
        matrix.add(row)
    }
    return matrix
}

fun getMinHeightForVisibility(treeMatrix: List<List<Int>>, visibilityMatrix: List<MutableList<Int>>): List<List<Int>> {

    for(i in treeMatrix.indices) {
        var rowMax: Int = -1
        for(j in treeMatrix[i].indices) {
            val currentTree = treeMatrix[i][j]
            visibilityMatrix[i][j] = rowMax
            if(currentTree > rowMax) {
                rowMax = currentTree
            }
        }
    }

    for(i in treeMatrix.indices) {
        var colMax: Int = -1
        for(j in treeMatrix[i].indices) {
            val currentTree = treeMatrix[j][i]
            visibilityMatrix[j][i] = min(visibilityMatrix[j][i], colMax)
            if(currentTree > colMax) {
                colMax = currentTree
            }
        }
    }

    for(i in treeMatrix.indices) {
        var colMax: Int = -1
        for(j in treeMatrix[i].indices.reversed()) {
            val currentTree = treeMatrix[j][i]
            visibilityMatrix[j][i] = min(visibilityMatrix[j][i], colMax)
            if(currentTree > colMax) {
                colMax = currentTree
            }
        }
    }

    for(i in treeMatrix.indices) {
        var rowMax: Int = -1
        for(j in treeMatrix[i].indices.reversed()) {
            val currentTree = treeMatrix[i][j]
            visibilityMatrix[i][j] = min(visibilityMatrix[i][j], rowMax)
            if(currentTree > rowMax) {
                rowMax = currentTree
            }
        }
    }

//    println("Right through Visibility matrix: $visibilityMatrix")

    return visibilityMatrix
}

fun getScoreForIndex(treeMatrix: List<List<Int>>, i: Int, j: Int): Int {
    val tree = treeMatrix[i][j]

    var rightScore = 0
    var col = j + 1
    // check right
    while (col < treeMatrix[i].size) {
        rightScore += 1
        if (treeMatrix[i][col] >= tree) {
            break
        }
        col++
    }

    col = j - 1
    var leftScore = 0
    // check left
    while (col >= 0) {
        leftScore += 1
        if (treeMatrix[i][col] >= tree) {
            break
        }
        col--
    }

    var row = i + 1
    var downScore = 0
    // check down
    while (row < treeMatrix.size) {
        downScore += 1
        if (treeMatrix[row][j] >= tree) {
            break
        }
        row++
    }

    row = i - 1
    var upScore = 0
    // check up
    while (row >= 0) {
        upScore += 1
        if (treeMatrix[row][j] >= tree) {
            break
        }
        row--
    }
//    println("Score for ($i, $j)==> $rightScore + $leftScore + $downScore + $upScore")
    return rightScore * leftScore * downScore * upScore
}