import generator.PriceGenerator
import model.ItemPrice
import java.awt.EventQueue
import kotlin.math.absoluteValue
import kotlin.system.exitProcess

class App {
    private val items = PriceGenerator.getItems()
    private var inputPlayerOne = 0
    private var inputPlayerTwo = 0
    private lateinit var selectedItemPrice: ItemPrice

    companion object {
        const val PRICE_EQUAL = 0
        const val PRICE_MORE_THAN = 1
        const val PRICE_LESS_THAN = 2


        @JvmStatic
        fun main(args: Array<String>) {
            App().runGame()
        }
    }

    fun runGame() {
        println("=========================================")
        println("Game Tebak Harga")
        println("=========================================")
        println("Start Game ? (Y/N)")
        if (readLine().equals("Y", ignoreCase = true)) {
            startGame()
        } else {
            println("Game Closed")
            exitProcess(0)
        }
    }

    private fun startGame() {
        selectedItemPrice = items[(0 until items.size).random()]
        printInfoItems(selectedItemPrice)
        inputPriceUser()
        checkWinner(
            checkPrice(selectedItemPrice, inputPlayerOne),
            checkPrice(selectedItemPrice, inputPlayerTwo)
        )
    }

    private fun printInfoItems(selectedItemPrice: ItemPrice) {
        println("*************************")
        println("Tebaklah Harga dari = ${selectedItemPrice.itemName}")
        println("*************************")
    }

    private fun inputPriceUser() {
        println("Masukan Harga Pemain Pertama = ")
        readLine()?.toInt()?.let {
            inputPlayerOne = it
        }
        println("Masukan Harga Pemain Kedua = ")
        readLine()?.toInt()?.let {
            inputPlayerTwo = it
        }
    }

    private fun checkPrice(itemPrice: ItemPrice, userInput: Int): Int {
        return when {
            userInput == itemPrice.price -> {
                PRICE_EQUAL
            }
            userInput > itemPrice.price -> {
                PRICE_MORE_THAN
            }
            else -> {
                PRICE_LESS_THAN
            }
        }
    }


    private fun checkWinner(resultPlayerOne: Int, resultPlayerTwo: Int) {
        if (resultPlayerOne == PRICE_EQUAL) {
            if (resultPlayerTwo == PRICE_EQUAL) {
                println("Keduanya Benar")
            } else {
                println("Player 1 Benar, Player 1 Menang")
            }
        } else {
            if (resultPlayerTwo == PRICE_EQUAL) {
                println("Player 2 Benar, Player 2 Menang")
            } else {
                when {
                    (inputPlayerOne - selectedItemPrice.price).absoluteValue < (inputPlayerTwo - selectedItemPrice.price).absoluteValue -> {
                        println("Player 1 Mendekati Benar, Player 1 Menang")
                    }
                    (inputPlayerOne - selectedItemPrice.price).absoluteValue == (inputPlayerTwo - selectedItemPrice.price).absoluteValue -> {
                        println("Keduanya Hampir Benar, Seri")
                    }
                    else -> {
                        println("Player 2 Mendekati Benar, Player 2 Menang")
                    }
                }
            }
        }
    }
}