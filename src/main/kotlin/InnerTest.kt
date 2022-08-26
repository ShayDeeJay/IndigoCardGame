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
    val cardsOnTable = mutableListOf<String>()
    val playerDeck = mutableListOf<String>()

//    fun reset() {
//        this.mainDeck.clear()
//        this.mainDeck.addAll(mainDeckOfCards)
//    }

    fun shuffle() = this.mainDeck.shuffle()

    fun deal(): String = this.mainDeck[0]

    fun removeCard(): String = this.mainDeck.removeAt(0)

    fun cardsOnTableUpdater(): String {
        return "${cardsOnTable.size} cards on the table, and the top card is ${cardsOnTable.last()}"
    }


    fun playerMove(){
        if (playerDeck.size < 1) {
            repeat(6) {
                playerDeck.add(deal())
                removeCard()
            }
        }
        println()
        println(cardsOnTableUpdater())
        print("Cards in hand: ")
        for (x in playerDeck) {
            print("${playerDeck.indexOf(x) + 1})$x ")
        }
        println()
    }

    fun playerMove2(): Boolean{
        while(true){
            val playerInput = readln()
            if(playerInput == "exit") {
                return true
            }
            try{
                println("Choose a card to play (1 - ${playerDeck.size}):")
                val nextMove = playerInput.toInt()-1
                if(nextMove !in 0 until playerDeck.size) {
                    continue
                }
                cardsOnTable.add(playerDeck[nextMove])
                playerDeck.removeAt(nextMove)
                return false
            } catch (_:NumberFormatException) { }
        }
    }

    fun computerMove() {
        println(
            """

                ${cardsOnTableUpdater()}
                Computer plays ${deal()}
            """.trimIndent()
        )
        cardsOnTable.add(deal())
        removeCard()
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

    while (deck.cardsOnTable.size < 52) {
        when(startDecision) {
            "yes" -> {
                deck.playerMove()
                if(deck.playerMove2()) break
                deck.computerMove()
            }
            "no" -> {
                deck.computerMove()
                deck.playerMove()
                if(deck.playerMove2()) break
            }
        }
        if(deck.cardsOnTable.size == 52) {
            println("""
                
                ${deck.cardsOnTableUpdater()}
                Game Over
            """.trimIndent())
        }
    }
    print("hi")
    print("hello")
}