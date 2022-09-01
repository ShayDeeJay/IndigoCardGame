import kotlin.reflect.KMutableProperty0

class Deck {
    companion object {
        private val mainDeckOfCards = listOf(
            "A♠", "2♠", "3♠", "4♠", "5♠", "6♠", "7♠", "8♠", "9♠", "10♠", "J♠", "Q♠", "K♠",
            "A♥", "2♥", "3♥", "4♥", "5♥", "6♥", "7♥", "8♥", "9♥", "10♥", "J♥", "Q♥", "K♥",
            "A♦", "2♦", "3♦", "4♦", "5♦", "6♦", "7♦", "8♦", "9♦", "10♦", "J♦", "Q♦", "K♦",
            "A♣", "2♣", "3♣", "4♣", "5♣", "6♣", "7♣", "8♣", "9♣", "10♣", "J♣", "Q♣", "K♣"
        )
    }

    val mainDeck = ArrayList(mainDeckOfCards)
    val cardsOnTable = ArrayList<String>()
    val playerDeck = ArrayList<String>()
    var playerScoreCount = 0
    var playerCardsCount = 0
    var computerScoreCount = 0
    var computerCardsCount = 0
    var startDecision = ""

    fun shuffle() = mainDeck.shuffle()

    fun deal(): String = mainDeck[0]

    fun removeCard(): String = mainDeck.removeAt(0)

    fun cardsOnTableUpdater() {
        return when (cardsOnTable.size) {
            0 -> println("\nNo cards on the table")
            else -> println("${cardsOnTable.size} cards on the table, and the top card is ${cardsOnTable.last()}")
        }
    }

    fun startDecision(): Boolean {
        when (startDecision) {
            "yes" -> {
                showCardsToPlayer()
                if (playerMove()) {
                    return true
                }
                computerMove()
            }
            "no" -> {
                computerMove()
                showCardsToPlayer()
                if (playerMove()) {
                    return true
                }
            }
        }
        return false
    }

    fun beforeStringCheck(): Int {
        var y = 0
        for (x in cardsOnTable) {
            when (x.first()) {
                '1', 'A', 'J', 'Q', 'K' -> y++
            }
        }
        return y
    }

    fun scoreBoard() {
        println(
            """
            Score: Player $playerScoreCount - Computer $computerScoreCount
            Cards: Player $playerCardsCount - Computer $computerCardsCount
            """.trimIndent()
        )
    }

    fun addAndRemove(a: ArrayList<String>, b: String, c: Int) {
        cardsOnTable.add(b)
        a.removeAt(c)
    }

    fun showCardsToPlayer() {
        if (playerDeck.size < 1) {
            val deal = deal()
            repeat(6) {
                playerDeck.add(deal)
                removeCard()
            }
        }
        println()
        cardsOnTableUpdater()
        print("Cards in hand: ")
        playerDeck.forEachIndexed { card, index ->
            print("${index + 1})$card ")
        }
        println()
    }
//add for no reason
    fun playerMove(): Boolean {
        while (true) {
            println("Choose a card to play (1-${playerDeck.size}):")
            val playerInput = readln()
            println()
            if (playerInput == "exit") {
                return true
            }
            try {
                val nextMove = playerInput.toInt() - 1
                if (nextMove !in 0 until playerDeck.size) {
                    continue
                }
                dealOrNoDeal("Player", playerDeck, nextMove, this::playerCardsCount, this::playerScoreCount)
                return false
            } catch (_: NumberFormatException) {
            }
        }
    }

    fun computerMove() {
        cardsOnTableUpdater()
        println("Computer plays ${deal()}")
        dealOrNoDeal("Computer", mainDeck, 0, this::computerCardsCount, this::computerScoreCount)
    }

    private fun dealOrNoDeal(
        type: String,
        cards: ArrayList<String>,
        index: Int,
        totalCards: KMutableProperty0<Int>,
        totalScore: KMutableProperty0<Int>
    ) {
        val currentCard = cards[index]
        if (cardsOnTable.size > 0) {
            if (currentCard.first() == cardsOnTable.last().first() || currentCard.last() == cardsOnTable.last().last()) {
                addAndRemove(cards, currentCard, index)
                totalCards.set(totalCards.get() + cardsOnTable.size)
                totalScore.set(totalScore.get() + beforeStringCheck())
                cardsOnTable.clear()
                println("$type wins cards")
                scoreBoard()
            } else {
                addAndRemove(cards, currentCard, index)
            }
        } else {
            addAndRemove(cards, currentCard, index)
        }
    }
}

fun main() {
    val deck = Deck()
    println("Indigo Card Game")

    while (true) {
        println("Play first?")
        val playerChoice = readln()

        if (playerChoice == "yes" || playerChoice == "no") {
            deck.shuffle()
            val deal = deck.deal()
            repeat(4) {
                deck.cardsOnTable.add(deal)
                deck.removeCard()
            }
            println("Initial cards on the table: ${deck.cardsOnTable.joinToString(separator = " ")}")
            break
        }
    }

    while (deck.mainDeck.size > 0) {
        deck.startDecision
        if (deck.startDecision()) {
            println("Game Over")
            break
        }
        if (deck.mainDeck.size == 0) {
            when {
                deck.playerCardsCount > deck.computerCardsCount -> {
                    deck.playerScoreCount += deck.beforeStringCheck() + 3
                    deck.playerCardsCount += deck.cardsOnTable.size
                }
                deck.computerCardsCount > deck.playerCardsCount -> {
                    deck.computerScoreCount += deck.beforeStringCheck() + 3
                    deck.computerCardsCount += deck.cardsOnTable.size
                }
                else -> {
                    if (deck.startDecision == "yes") {
                        deck.playerScoreCount += 3
                    } else {
                        deck.computerScoreCount += 3
                    }
                }
            }
            println()
            deck.cardsOnTableUpdater()
            deck.scoreBoard()
            println("Game Over")
        }
    }
}
