package coffee

fun main() {

    val machine = CoffeeMachine()
    machine.promptUser()
}


class CoffeeMachine(
    var waterMl: Int = 400,
    var milkMl: Int = 540,
    var coffeeG: Int = 120,
    var cups: Int = 9,
    var money: Int = 550
) {

    fun promptUser() {

        while (true) {
            println("Write action (buy, fill, take, remaining, exit):")
            when (readln()) {
                "buy" -> {
                    println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
                    val choice = readln()
                    if (choice == "back") continue
                    acceptChoice(Drink.fromInt(choice.toInt()))
                }

                "fill" -> {
                    println("Write how many ml of water you want to add:")
                    val water = readln().toInt()

                    println("Write how many ml of milk you want to add:")
                    val milk = readln().toInt()

                    println("Write how many grams of coffee beans you want to add:")
                    val coffee = readln().toInt()

                    println("Write how many disposable cups you want to add: ")
                    val cups = readln().toInt()

                    fill(water, milk, coffee, cups)
                }

                "take" -> emptyCashRegister()

                "remaining" -> {
                    printState()
                }

                "exit" -> {
                    break
                }
            }
        }
    }

    fun acceptChoice(drink: Drink) {
        val waterDiff = this.waterMl - drink.waterMl
        val milkDiff = this.milkMl - drink.milkMl
        val coffeeDiff = this.coffeeG - drink.coffeeG

        when {
            waterDiff < 0 -> {
                println("Sorry, not enough water!"); return
            }

            milkDiff < 0 -> {
                println("Sorry, not enough milk!"); return
            }

            coffeeDiff < 0 -> {
                println("Sorry, not enough coffee!"); return
            }
        }

        println("I have enough resources, making you a coffee!")
        this.waterMl = this.waterMl - drink.waterMl
        this.milkMl = this.milkMl - drink.milkMl
        this.coffeeG = this.coffeeG - drink.coffeeG
        this.cups = this.cups - 1
        this.money = this.money + drink.money
    }

    fun fill(water: Int, milk: Int, coffee: Int, cups: Int) {
        this.waterMl = this.waterMl + water
        this.milkMl = this.milkMl + milk
        this.coffeeG = this.coffeeG + coffee
        this.cups = this.cups + cups
    }

    private fun emptyCashRegister() {
        println("I gave you $${money}")
        this.money = 0
    }

    private fun printState() {
        println("The coffee machine has:")
        println("$waterMl ml of water")
        println("$milkMl ml of milk")
        println("$coffeeG g of coffee beans")
        println("$cups disposable cups")
        println("$$money of money")
    }
}

enum class Drink(
    val option: Int,
    val waterMl: Int,
    val milkMl: Int,
    val coffeeG: Int,
    val money: Int
) {
    ESPRESSO(1, 250, 0, 16, 4),
    LATTE(2, 350, 75, 20, 7),
    CAPPUCCINO(3, 200, 100, 12, 6);

    companion object {
        fun fromInt(value: Int) = Drink.values().first { it.option == value }
    }
}