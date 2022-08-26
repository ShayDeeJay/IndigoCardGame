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
            try{
                println("Choose a card to play (${playerDeck.indexOf(playerDeck.first()) + 1}-${playerDeck.indexOf(playerDeck.last()) + 1}):")
                val index = readln().toInt() - 1
                cardsOnTable.add(playerDeck[index])
                playerDeck.removeAt(index)
                break
            }catch (e:NumberFormatException) {
                if (e.message == "For input string: \"exit\"") {
                    println("Game Over")
                    return true
                    break
                }
            }catch (_:IndexOutOfBoundsException) {
            }
        }
        return false
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
}