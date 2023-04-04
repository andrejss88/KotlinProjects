package tic

const val BLANK = ' '
// turn outcomes
const val CONTINUE = ""
const val X_WINS = "X wins"
const val O_WINS = "O wins"
const val DRAW = "Draw"

fun main() {
    ticTacToe()
}

fun ticTacToe() {

    val board = createBoard()
    val assembledBoard = assembleBoard(board)

    println(assembledBoard)

    var coordinates: List<Int?>
    var playerXPlays = true
    while (true) {
        coordinates = readln().split(" ").map { it.toIntOrNull() }

        if (coordinates.size != 2) {
            println("You should enter two numbers separated by a space, e.g. 2 2")
            continue
        }

        val (x, y) = coordinates
        if (x == null || y == null) {
            println("You should enter numbers!")
            continue
        }

        val validRange = 1..3
        if (x !in validRange || y !in validRange) {
            println("Coordinates should be from 1 to 3!")
            continue
        }
        val adjustedX = x.toInt() - 1
        val adjustedY = y.toInt() - 1
        val boardValue = board[adjustedX][adjustedY]
        if (boardValue == 'X' || boardValue == 'O') {
            println("This cell is occupied! Choose another one!")
        } else {
            val charToSet = if(playerXPlays) 'X' else 'O'
            playerXPlays = !playerXPlays    // flip between X and Y

            board[adjustedX][adjustedY] = charToSet
            println(assembleBoard(board))
            val outcome = evaluateBoard(board)
            if (outcome != CONTINUE) {
                println(outcome)
                return
            }
        }
    }
}

fun createBoard(): List<MutableList<Char>> {
    return List(3) { MutableList(3) { BLANK } }
}

fun assembleBoard(input: List<MutableList<Char>>): String {
    val horizontalBorder = "---------"
    val pipe = "|"

    val rows = listOf(input[0], input[1], input[2])

    var boardString = ""
    for (row in rows) {
        val outputRow = "$pipe ${row[0]} ${row[1]} ${row[2]} $pipe"
        boardString += outputRow + "\n"
    }

    return "$horizontalBorder\n$boardString$horizontalBorder"
}

fun evaluateBoard(board: List<MutableList<Char>>): String {
    var outcome = ""

    outcome = checkRows(board)
    if (outcome != CONTINUE) return outcome

    outcome = checkColumns(board)
    if (outcome != CONTINUE) return outcome

    outcome = checkDiagonals(board)
    if (outcome != CONTINUE) return outcome

    outcome = checkEmptyCells(board)
    if (outcome != CONTINUE) return outcome

    return CONTINUE
}

private fun checkEmptyCells(board: List<List<Char>>): String {
    val foundEmptyCell = board.any { it.contains(BLANK) }
    return if(foundEmptyCell) CONTINUE else DRAW
}

private fun checkRows(board: List<List<Char>>): String {
    return checkSequenceOfThree(board)
}

private fun checkColumns(board: List<List<Char>>): String {
    val invertedBoard = invertBoard(board)
    return checkSequenceOfThree(invertedBoard)
}

private fun invertBoard(board: List<List<Char>>): List<MutableList<Char>> {
    val firstColumn = mutableListOf<Char>()
    val secondColumn = mutableListOf<Char>()
    val thirdColumn = mutableListOf<Char>()

    for (row in board) {
        firstColumn.add(row[0])
        secondColumn.add(row[1])
        thirdColumn.add(row[2])
    }
    return listOf(firstColumn, secondColumn, thirdColumn)
}

fun checkDiagonals(board: List<List<Char>>): String {
    val diagonal1 = listOf(board[0][0], board[1][1], board[2][2])
    val diagonal2 = listOf(board[0][2], board[1][1], board[2][0])
    return checkSequenceOfThree(listOf(diagonal1, diagonal2))
}

private fun checkSequenceOfThree(board: List<List<Char>>): String {
    for (row in board) {
        if (row.all { it == 'X' }) {
            return X_WINS
        } else if (row.all { it == 'O' }) {
            return O_WINS
        }
    }
    return CONTINUE
}