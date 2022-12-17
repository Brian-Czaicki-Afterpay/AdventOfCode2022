import java.io.File

const val MAX_DIR_SIZE_TO_DELETE = 100_000

fun main() {
//    val fileName = "Day7ExampleInput.txt"
    val fileName = "Day7Input.txt"
    val lines = File(fileName).readLines()
    val rootDirectory = parseInputFile(lines)

    val rootDirectorySize = rootDirectory.find { it.calcTotalSize() <= MAX_DIR_SIZE_TO_DELETE }.sumOf { it.calcTotalSize() }
    println(rootDirectorySize)
    val directoriesToSum = rootDirectory.find { it.calcTotalSize() <= MAX_DIR_SIZE_TO_DELETE }
    directoriesToSum.map {
        println("Adding Directory: ${it.name} Size: ${it.calcTotalSize()}")
    }
    val sum = directoriesToSum?.sumOf { it.calcTotalSize() } ?: 0
    println("Valid dir size: $sum")

    val unusedSpace = 70_000_000 - rootDirectory.calcTotalSize()
    val deficit = 30_000_000 - unusedSpace
    val part2 =  rootDirectory.find { it.calcTotalSize() >= deficit }.minBy { it.calcTotalSize() }.calcTotalSize()
    println("Part 2: $part2")
}



// create a directory class that has a name,  list of children directories
// and a size
class Dir(val name: String, val children: MutableList<Dir>, val parentNode: Dir?) {

    constructor (
        name: String,
        children: MutableList<Dir>,
        parentNode: Dir,
        fileSize: Int
    ): this(name, children, parentNode) {
        this.fileSize = fileSize
    }

    var fileSize: Int = 0
    fun addSubDir(dir: Dir) {
        val dirName = this.name
        val dirToAdd = dir.name
        children.add(dir)
    }

    fun addFileSize(size: Int) {
        val dirName = this.name
        fileSize += size
    }

    fun calcTotalSize(): Int {
        var childDirSize = children
            .sumOf { it.calcTotalSize() }
        return fileSize + childDirSize
    }

    fun find(predicate: (Dir) -> Boolean): List<Dir> {
        return children.filter(predicate) +
                children.flatMap { it.find(predicate) }
    }
}

// create a function to parse the input file line by line and return the root directory with all subdirectories and
// file sizes for the directories
fun parseInputFile(lines: List<String>): Dir {
    val rootDirectory = Dir(lines[0].split(" ")[2], mutableListOf<Dir>(), null)
    var currentDirectory = rootDirectory
    val numbersOnlyRegex = Regex("[0-9]+")

    lines.map { it ->
        val lineSplit = it.split(" ")
        if (lineSplit[0] == "$") {
            println("executing command")
            // Execute CLI command
            if (lineSplit[1] == "cd") {
                println("changing directory")
                val curDirName = currentDirectory.name
                println("current directory: $curDirName")
                val dirToFind = lineSplit[2]
                println("current directory: $dirToFind")
                // Change directory
                if (lineSplit[2] == "..") {
                    // Go up one directory if not at root
                    if (currentDirectory.parentNode != null) {
                        currentDirectory = currentDirectory.parentNode!!
                    }
                } else if(lineSplit[2] == "/") {
                    currentDirectory = rootDirectory
                }else {
                    // Go down to a subdirectory
                    val dirToGoTo = currentDirectory.children.filter { childDir -> childDir.name == lineSplit[2] }
                    println("dir to go to: $dirToGoTo")
                    if (dirToGoTo.isNotEmpty()) {
                        currentDirectory = dirToGoTo[0]
                    }
                }
            }
        } else if (numbersOnlyRegex.matches(lineSplit[0])) {
            // Add file size to current directory
            currentDirectory.addFileSize(lineSplit[0].toInt())
        } else if(lineSplit[0] == "dir") {
            // Add a child directory to the current directory
            currentDirectory.addSubDir(Dir(lineSplit[1], mutableListOf<Dir>(), currentDirectory))
        }
    }

    return rootDirectory
}
