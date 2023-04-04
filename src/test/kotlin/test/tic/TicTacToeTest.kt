package test.tic

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tic.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.stream.Stream

class TicTacToeTest {

    private val completeGame = "\n1 1\n2 1\n1 2\n2 2\n1 3"

    @Test
    fun whenBoardCreated_allSlotsEmpty() {
        val board = createBoard()
        val notEmptySlot = board.flatten().any { it != BLANK }
        assertFalse(notEmptySlot)
    }

    @Test
    fun inputMoreThanTwoNumbers_rejected() {

        simulateInput("1 1 2$completeGame")
        val output = captureOutput()
        ticTacToe()
        assertTrue(output.toString().contains("You should enter two numbers separated by a space"))
    }

    @Test
    fun inputNonNumbers_rejected() {

        simulateInput("two two$completeGame")
        val output = captureOutput()
        ticTacToe()
        assertTrue(output.toString().contains("You should enter numbers!"))
    }

    @ParameterizedTest
    @MethodSource("inputOutsideRange")
    fun inputOutsideRange_rejected(x: String, y: String) {

        simulateInput("$x $y$completeGame")
        val output = captureOutput()
        ticTacToe()
        assertTrue(output.toString().contains("Coordinates should be from 1 to 3!"))
    }

    @Test
    fun inputToOccupiedSlot_Rejected() {

        simulateInput("1 1\n1 1$completeGame")
        val output = captureOutput()
        ticTacToe()
        assertTrue(output.toString().contains("This cell is occupied! Choose another one!"))
    }

    @ParameterizedTest
    @MethodSource("boardWithRowComplete")
    fun whenRowComplete_PlayerWins(board: List<MutableList<Char>>, result: String) {
        assertEquals(result, result)
    }

    @ParameterizedTest
    @MethodSource("boardWithColumnComplete")
    fun whenColumnComplete_PlayerWins(board: List<MutableList<Char>>, result: String) {
        assertEquals(result, result)
    }

    @ParameterizedTest
    @MethodSource("boardWithDiagonalComplete")
    fun whenDiagonalComplete_PlayerWins(board: List<MutableList<Char>>, result: String) {
        assertEquals(result, result)
    }

    @ParameterizedTest
    @MethodSource("boardWithDraw")
    fun whenBoardComplete_butNoWin_resultIsDraw(board: List<MutableList<Char>>, result: String) {
        assertEquals(result, result)
    }

    @ParameterizedTest
    @MethodSource("boardWithTurnsLeft")
    fun whenBoardNotComplete_resultIsContinue(board: List<MutableList<Char>>, result: String) {
        assertEquals(result, result)
    }

    private companion object {
        @JvmStatic
        fun boardWithRowComplete(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    listOf(
                        mutableListOf('X', 'X', 'X'),
                        mutableListOf(' ', 'O', 'O'),
                        mutableListOf(' ', 'O', ' ')
                    ),
                    X_WINS
                ),

                Arguments.of(
                    listOf(
                        mutableListOf('X', 'X', ' '),
                        mutableListOf('O', 'O', 'O'),
                        mutableListOf(' ', 'X', ' ')
                    ),
                    O_WINS
                ),

                Arguments.of(
                    listOf(
                        mutableListOf('X', 'X', ' '),
                        mutableListOf('O', 'X', 'X'),
                        mutableListOf('O', 'O', 'O')
                    ),
                    O_WINS
                )
            )
        }

        @JvmStatic
        fun boardWithColumnComplete(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    listOf(
                        mutableListOf('X', ' ', ' '),
                        mutableListOf('X', 'O', 'O'),
                        mutableListOf('X', 'O', ' ')
                    ),
                    X_WINS
                ),

                Arguments.of(
                    listOf(
                        mutableListOf('X', 'X', ' '),
                        mutableListOf('O', 'X', 'O'),
                        mutableListOf(' ', 'X', 'O')
                    ),
                    X_WINS
                ),

                Arguments.of(
                    listOf(
                        mutableListOf('X', 'X', 'O'),
                        mutableListOf('X', 'X', 'O'),
                        mutableListOf(' ', ' ', 'O')
                    ),
                    O_WINS
                )
            )
        }

        @JvmStatic
        fun boardWithDiagonalComplete(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    listOf(
                        mutableListOf('X', ' ', ' '),
                        mutableListOf('X', 'X', 'O'),
                        mutableListOf('O', 'O', 'X')
                    ),
                    X_WINS
                ),

                Arguments.of(
                    listOf(
                        mutableListOf('X', 'X', 'O'),
                        mutableListOf('X', 'O', ' '),
                        mutableListOf('O', ' ', ' ')
                    ),
                    O_WINS
                )
            )
        }

        @JvmStatic
        fun boardWithDraw(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    listOf(
                        mutableListOf('X', 'X', 'O'),
                        mutableListOf('X', 'X', 'O'),
                        mutableListOf('O', 'O', 'X')
                    ),
                    DRAW
                ),

                Arguments.of(
                    listOf(
                        mutableListOf('X', 'O', 'O'),
                        mutableListOf('O', 'O', 'X'),
                        mutableListOf('X', 'X', 'O')
                    ),
                    DRAW
                )
            )
        }

        @JvmStatic
        fun boardWithTurnsLeft(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    listOf(
                        mutableListOf('X', ' ', 'O'),
                        mutableListOf('X', 'X', 'O'),
                        mutableListOf('O', 'O', 'X')
                    ),
                    CONTINUE
                ),

                Arguments.of(
                    listOf(
                        mutableListOf('X', 'O', 'O'),
                        mutableListOf(' ', 'O', 'X'),
                        mutableListOf('X', ' ', 'O')
                    ),
                    CONTINUE
                )
            )
        }

        @JvmStatic
        fun inputOutsideRange(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("0", "1"),
                Arguments.of("4", "1"),

                Arguments.of("1", "0"),
                Arguments.of("1", "4"),
            )
        }
    }

    private fun captureOutput(): ByteArrayOutputStream {
        val output = ByteArrayOutputStream()
        System.setOut(PrintStream(output))
        return output
    }

    private fun simulateInput(input: String) {
        System.setIn(ByteArrayInputStream(input.toByteArray()))
    }
}