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
    fun shuffle() = this.mainDeck.shuffle()
    fun deal(): String = mainDeck[0]
    fun removeCard(): String = this.mainDeck.removeAt(0)
    fun cardsOnTableUpdater(){
        return when (cardsOnTable.size) {
            0 -> println("\nNo cards on the table")
            else -> println("${cardsOnTable.size} cards on the table, and the top card is ${cardsOnTable.last()}")
        }
    }
    fun startDecision(): Boolean {
        when(startDecision) {
            "yes" -> {
                playerMove()
                if(playerMove2()) return true
                computerMove()
            }
            "no" -> {
                computerMove()
                playerMove()
                if(playerMove2()) return true
            }
        }
        return false
    }
    fun beforeStringCheck(): Int{
        var y = 0
        for (x in cardsOnTable) {
            when (x.first()) {
                '1', 'A', 'J', 'Q', 'K' -> y++
            }
        }
        return y
    }
    fun playerMove(){
        if (playerDeck.size < 1) {
            repeat(6) {
                playerDeck.add(deal())
                removeCard()
            }
        }
        println()
        cardsOnTableUpdater()
        print("Cards in hand: ")
        for (x in playerDeck) {
            print("${playerDeck.indexOf(x) + 1})$x ")
        }
        println()
    }
    fun scoreBoard() {
        println("""
            Score: Player $playerScoreCount - Computer $computerScoreCount
            Cards: Player $playerCardsCount - Computer $computerCardsCount
        """.trimIndent())
    }
    fun addAndRemove(a: ArrayList<String>, b: String, c: Int) {
        cardsOnTable.add(b)
        a.removeAt(c)
    }
    fun playerMove2(): Boolean{
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
                when{
                    cardsOnTable.size > 0 -> {
                        if(playerDeck[nextMove].first() == cardsOnTable.last().first() || playerDeck[nextMove].last() == cardsOnTable.last().last()) {
                            addAndRemove(playerDeck,playerDeck[nextMove],nextMove)
                            playerCardsCount += cardsOnTable.size
                            playerScoreCount += beforeStringCheck()
                            cardsOnTable.clear()
                            println("Player wins cards")
                            scoreBoard()
                        } else addAndRemove(playerDeck,playerDeck[nextMove],nextMove)
                    }
                    else -> addAndRemove(playerDeck,playerDeck[nextMove],nextMove)
                }
                return false
            } catch (_: NumberFormatException) { }
        }
    }

    fun computerMove() {
        cardsOnTableUpdater()
        println("Computer plays ${deal()}")
        when {
            cardsOnTable.size > 0 -> {
                if(deal().first() == cardsOnTable.last().first() || deal().last() == cardsOnTable.last().last()) {
                    addAndRemove(mainDeck,deal(),0)
                    computerCardsCount += cardsOnTable.size
                    computerScoreCount += beforeStringCheck()
                    cardsOnTable.clear()
                    println("Computer wins cards")
                    scoreBoard()
                } else addAndRemove(mainDeck,deal(),0)
            }
            else -> addAndRemove(mainDeck,deal(),0)
        }
    }
    fun calculateThis(a: String, b: ArrayList<String>,c: Int ) {
        var cards = cardsOnTable.size
        var score = beforeStringCheck()
        when{
            cardsOnTable.size > 0 -> {
                if(a.first() == cardsOnTable.last().first() || a.last() == cardsOnTable.last().last()) {
                    addAndRemove(b,a,c)
                    playerCardsCount += cardsOnTable.size
                    playerScoreCount += beforeStringCheck()
                    cardsOnTable.clear()
                    println("Player wins cards")
                    scoreBoard()
                } else addAndRemove(b,a,c)
            }
            else -> addAndRemove(b,a,c)
        }
    }

}
fun main() {
    val deck = Deck()
    var startDecision = ""
    println("Indigo Card Game")

    while (true) {
        println("Play first?")
        val playerChoice = readln()

        if (playerChoice == "yes" || playerChoice == "no") {
            startDecision = playerChoice
            deck.shuffle()
            repeat(4) {
                deck.cardsOnTable.add(deck.deal())
                deck.removeCard()
            }
            println("Initial cards on the table: ${deck.cardsOnTable.joinToString(separator = " ")}")
            break
        }
    }

    while (deck.mainDeck.size > 0) {
        deck.startDecision
        if(deck.startDecision()) {
            println("Game Over")
            break
        }
        if(deck.mainDeck.size == 0) {
            when{
                deck.playerCardsCount > deck.computerCardsCount -> {
                    deck.playerScoreCount += deck.beforeStringCheck()
                    deck.playerScoreCount += 3
                    deck.playerCardsCount += deck.cardsOnTable.size
                }
                deck.computerCardsCount > deck.playerCardsCount -> {
                    deck.computerScoreCount += deck.beforeStringCheck()
                    deck.computerScoreCount += 3
                    deck.computerCardsCount += deck.cardsOnTable.size
                }
                deck.playerCardsCount == deck.computerCardsCount -> {
                    if(deck.startDecision == "yes") {
                        deck.playerScoreCount += 3
                    } else deck. computerScoreCount += 3
                }
            }
            println()
            deck.cardsOnTableUpdater()
            deck.scoreBoard()
            println("Game Over")
        }
    }
}