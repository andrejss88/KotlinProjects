package test.cinema

import cinema.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.stream.Stream

class CinemaManagerTest {

    @Test
    fun whenSeatingPlanCreated_allSeatsAreFree() {
        val seatingPlan = buildSeatingPlan(9, 7)
        val seatNotFree = seatingPlan.flatten().any { it != FREE_SEAT }
        assertFalse(seatNotFree)
    }

    @ParameterizedTest
    @MethodSource("seatingPlanAndTotalProvider")
    fun totalCorrect_WhenSeatCountNoMoreThan60(expectedTotal: Int, seatingPlan: List<MutableList<Char>>) {
        assertEquals(expectedTotal, total(seatingPlan))
    }

    private companion object {
        @JvmStatic
        fun seatingPlanAndTotalProvider(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(10, buildSeatingPlan(1, 1)),
                Arguments.of(360, buildSeatingPlan(6, 6)),

                // border value of 60 seats
                Arguments.of(600, buildSeatingPlan(10, 6)),
                Arguments.of(600, buildSeatingPlan(6, 10))
            )
        }
    }

    @Test
    fun totalCorrect_WhenSeatCountMoreThan60_evenRowCount() {
        val seatingPlan = buildSeatingPlan(10, 7)
        val expectedTotal = 630 // half rows 10$ per seat, half 8$ per seat
        assertEquals(expectedTotal, total(seatingPlan))
    }

    @Test
    fun totalCorrect_WhenSeatCountMoreThan60_oddRowCount() {
        val seatingPlan = buildSeatingPlan(9, 7)
        val expectedTotal = 560 // 4 rows 10$ per seat, 5 rows 8$ per seat
        assertEquals(expectedTotal, total(seatingPlan))
    }

    @Test
    fun ticketPriceAlways10_whenSeatCountNoMoreThan60() {
        val seatingPlan = buildSeatingPlan(5, 5)
        val expectedPrice = 10
        assertEquals(expectedPrice, getTicketPrice(seatingPlan, 1))
        assertEquals(expectedPrice, getTicketPrice(seatingPlan, 5))
    }

    @Test
    fun ticketPrice10_whenSeatCountMoreThan60_and_selectedRowInFrontHalf() {
        val seatingPlan = buildSeatingPlan(8, 9)
        val expectedPrice = 10
        assertEquals(expectedPrice, getTicketPrice(seatingPlan, 1))
        assertEquals(expectedPrice, getTicketPrice(seatingPlan, 4))
    }

    @Test
    fun ticketPrice8_whenSeatCountMoreThan60_and_selectedRowInBackHalf() {
        val seatingPlan = buildSeatingPlan(9, 8)
        val expectedPrice = 8
        assertEquals(expectedPrice, getTicketPrice(seatingPlan, 6))
        assertEquals(expectedPrice, getTicketPrice(seatingPlan, 9))
    }

    @Test
    fun printedStatsForNewSeatingPlan_areCorrect() {
        // redirect output
        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        printStats(buildSeatingPlan(5, 5))

        val out = outContent.toString().trimMargin()
        val expected = """Number of purchased tickets: 0
Percentage: 0.00%
Current income: $0
Total income: $250
        """.trimMargin()
        assertEquals(expected, out)
    }
}