package test.coffee

import coffee.CoffeeMachine
import coffee.Drink
import org.junit.jupiter.api.Assertions
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

class CoffeeMakerTest {

    @Test
    fun `default values of coffee machines are set`() {
        val machine = CoffeeMachine()

        assertEquals(400, machine.waterMl)
        assertEquals(540, machine.milkMl)
        assertEquals(120, machine.coffeeG)
        assertEquals(9, machine.cups)
        assertEquals(550, machine.money)
    }

    @Test
    fun `default values can change`() {
        val machine = CoffeeMachine()
        machine.waterMl = 10
        assertEquals(10, machine.waterMl)
    }

    @Test
    fun `cappuccino changes machine state correctly`() {
        val machine = CoffeeMachine()
        machine.acceptChoice(Drink.CAPPUCCINO)

        assertEquals(200, machine.waterMl)
        assertEquals(440, machine.milkMl)
        assertEquals(108, machine.coffeeG)
        assertEquals(8, machine.cups)
        assertEquals(556, machine.money)
    }

    @Test
    fun `latte changes machine state correctly`() {
        val machine = CoffeeMachine()
        machine.acceptChoice(Drink.LATTE)

        assertEquals(50, machine.waterMl)
        assertEquals(465, machine.milkMl)
        assertEquals(100, machine.coffeeG)
        assertEquals(8, machine.cups)
        assertEquals(557, machine.money)
    }

    @Test
    fun `fill works correctly`() {
        val machine = CoffeeMachine()
        machine.fill(2000, 500, 100, 10)

        assertEquals(2400, machine.waterMl)
        assertEquals(1040, machine.milkMl)
        assertEquals(220, machine.coffeeG)
        assertEquals(19, machine.cups)
        assertEquals(550, machine.money)
    }
}