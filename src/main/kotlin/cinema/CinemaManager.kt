package cinema

import java.lang.RuntimeException
import java.util.*

const val FREE_SEAT = 'S'
const val BOOKED_SEAT = 'B'

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsPerRow = readln().toInt()

    var seatingPlan = buildSeatingPlan(rows, seatsPerRow)

    printOptions()
    while (true) {
        when (readln().toInt()) {
            1 -> printPlan(seatingPlan)
            2 -> {
                seatingPlan = bookTicket(seatingPlan)
            }

            3 -> printStats(seatingPlan)
            0 -> break
        }
        printOptions()
    }
}

var currentIncome = 0

fun buildSeatingPlan(rows: Int, seatsPerRow:Int): List<MutableList<Char>> {
    return List(rows) { MutableList(seatsPerRow) { FREE_SEAT } }
}

fun printStats(seatingPlan: List<MutableList<Char>>) {
    val purchasedTickets = countBookedSeats(seatingPlan)
    val asPercent = purchasedTickets.toDouble() / seatingPlan.flatten().size.toDouble() * 100

    val totalIncome = total(seatingPlan)

    println("Number of purchased tickets: $purchasedTickets")
    println("Percentage: ${"%.2f".format(Locale.ENGLISH, asPercent)}%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")
}

fun countBookedSeats(seatingPlan: List<MutableList<Char>>): Int {
    return seatingPlan.flatten().count { it == BOOKED_SEAT }
}

fun total(seatingPlan: List<MutableList<Char>>): Int {
    val rows = seatingPlan.size
    val seats = seatingPlan[0].size
    val totalSeats = seatingPlan.flatten().size

    return when {
        totalSeats <= 60 -> totalSeats * 10
        else -> calculatePrice(rows, seats)
    }
}

private fun calculatePrice(rows: Int, seats: Int): Int {
    val isEven = rows % 2
    return if (isEven == 0) {
        val half = rows / 2
        half * seats * 10 + half * seats * 8
    } else {
        val firstHalf = rows / 2
        val secondHalf = rows - firstHalf
        firstHalf * seats * 10 + secondHalf * seats * 8
    }
}


fun bookTicket(seatingPlan: List<MutableList<Char>>): List<MutableList<Char>> {
    var place: Char
    var selectedRow: Int
    var selectedSeat: Int

    while (true) {
        println("Enter a row number:")
        selectedRow = readln().toInt()
        println("Enter a seat number in that row:")
        selectedSeat = readln().toInt()

        try {
            place = seatingPlan[selectedRow - 1][selectedSeat - 1]
        } catch (e: RuntimeException) {
            println("Wrong input!")
            continue
        }

        if (place == BOOKED_SEAT) {
            println("That ticket has already been purchased!")
            continue
        } else {
            println("Ticket price: $${getTicketPrice(seatingPlan, selectedRow)}")
            // book the seat
            seatingPlan[selectedRow - 1][selectedSeat - 1] = BOOKED_SEAT
            break
        }
    }

    return seatingPlan
}

fun printOptions() {
    println()
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}

fun printPlan(seatingPlan: List<MutableList<Char>>) {
    println("Cinema:")
    print("  ")

    // print header
    val rowSize = seatingPlan[0].size
    var seatNum = 1
    repeat(rowSize) {
        print("${seatNum++} ")
    }

    println()
    for ((index, value) in seatingPlan.withIndex()) {
        println("${index + 1} ${value.joinToString(" ")}")
    }
}

fun getTicketPrice(seatingPlan: List<MutableList<Char>>, selectedRow: Int): Int {
    val rowCount = seatingPlan.size
    val seatsPerRow = seatingPlan[0].size
    if (rowCount * seatsPerRow <= 60) {
        currentIncome += 10
        return 10
    }
    return if (rowCount - selectedRow >= rowCount / 2) {
        currentIncome += 10
        10
    } else {
        currentIncome += 8
        8
    }
}