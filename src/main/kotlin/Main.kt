//import kotlin.reflect.KMutableProperty0
//
//class Deck {
//    companion object {
//        private val mainDeckOfCards = listOf(
//            "A♠", "2♠", "3♠", "4♠", "5♠", "6♠", "7♠", "8♠", "9♠", "10♠", "J♠", "Q♠", "K♠",
//            "A♥", "2♥", "3♥", "4♥", "5♥", "6♥", "7♥", "8♥", "9♥", "10♥", "J♥", "Q♥", "K♥",
//            "A♦", "2♦", "3♦", "4♦", "5♦", "6♦", "7♦", "8♦", "9♦", "10♦", "J♦", "Q♦", "K♦",
//            "A♣", "2♣", "3♣", "4♣", "5♣", "6♣", "7♣", "8♣", "9♣", "10♣", "J♣", "Q♣", "K♣"
//        )
//    }
//
//    val mainDeck = ArrayList(mainDeckOfCards)
//    val cardsOnTable = ArrayList<String>()
//    val playerDeck = ArrayList<String>()
//    val computerDeck = ArrayList<String>()
//    var playerScoreCount = 0
//    var playerCardsCount = 0
//    var computerScoreCount = 0
//    var computerCardsCount = 0
//    var startDecision = ""
//
//    fun shuffle() = mainDeck.shuffle()
//
//    fun deal(): String = mainDeck[0]
//
//    fun removeCard(): String = mainDeck.removeAt(0)
//
//    fun cardsOnTableUpdater() {
//        return when (cardsOnTable.size) {
//            0 -> println("No cards on the table")
//            else -> println("${cardsOnTable.size} cards on the table, and the top card is ${cardsOnTable.last()}")
//        }
//    }
//
//    fun startDecision(): Boolean {
//        when (startDecision) {
//            "yes" -> {
//                if (playerMove()) {
//                    return true
//                }
//                computerMove()
//            }
//            "no" -> {
//                computerMove()
//                if (playerMove()) {
//                    return true
//                }
//            }
//        }
//        return false
//    }
//
//    fun beforeStringCheck(): Int {
//        var y = 0
//        for (x in cardsOnTable) {
//            when (x.first()) {
//                '1', 'A', 'J', 'Q', 'K' -> y++
//            }
//        }
//        return y
//    }
//
//    fun scoreBoard() {
//        println(
//            """
//            Score: Player $playerScoreCount - Computer $computerScoreCount
//            Cards: Player $playerCardsCount - Computer $computerCardsCount
//            """.trimIndent()
//        )
//    }
//
//    fun addAndRemove(a: ArrayList<String>, b: String, c: Int) {
//        cardsOnTable.add(b)
//        a.removeAt(c)
//    }
//
//    fun emptyDeckDeal(deck: ArrayList<String>) {
//        if (deck.size < 1) {
//            repeat(6) {
//                deck.add(deal())
//                removeCard()
//            }
//        }
//    }
//
//
//    fun computerMoveLogic() {
//        val suitCheck = ArrayList<String>()
//        val rankCheck = ArrayList<String>()
//        if (computerDeck.size == 1) {
//            addAndRemove(computerDeck,cardsOnTable[0],0)
//        }
//        for (i in cardsOnTable) {
//            for(j in computerDeck) {
//                if(j.last() == i.last())
//                    suitCheck.add(j.last().toString() + computerDeck.indexOf(j))
//            }
//        }
//        for (i in cardsOnTable) {
//            for(j in computerDeck) {
//                if(j.first() == i.first())
//                    rankCheck.add(j.first().toString() + computerDeck.indexOf(j))
//            }
//        }
//        when {
//            suitCheck.size + rankCheck.size == 1 -> {
//                val matchingCardSuit = suitCheck[0].last().digitToInt()
//                val matchingCardRank = rankCheck[0].last().digitToInt()
//                if(suitCheck.size == 1) {
//                    addAndRemove(computerDeck,cardsOnTable[matchingCardSuit],matchingCardSuit)
//                } else {
//                    addAndRemove(computerDeck,cardsOnTable[matchingCardRank],matchingCardRank)
//                }
//            }
//            suitCheck.size + rankCheck.size == 0 -> {
//                computerDeck.sortBy { it.last() }
//                for(dupe in 0 until computerDeck.size -1) {
//                    if(computerDeck[dupe].last() == computerDeck[dupe + 1].last()) {
//                        addAndRemove(computerDeck,cardsOnTable[dupe],dupe)
//                    }
//                }
//                computerDeck.sortBy { it.first() }
//                for(dupe in 0 until computerDeck.size -1) {
//                    if(computerDeck[dupe].first() == computerDeck[dupe + 1].first()) {
//                        addAndRemove(computerDeck,cardsOnTable[dupe],dupe)
//                    }
//                }
//            }
//            cardsOnTable.size == 0 -> {
//                computerDeck.sortBy { it.last() }
//                for(dupe in 0 until computerDeck.size -1) {
//                    if(computerDeck[dupe].last() == computerDeck[dupe + 1].last()) {
//                        addAndRemove(computerDeck,cardsOnTable[dupe],dupe)
//                    }
//                }
//                computerDeck.sortBy { it.first() }
//                for(dupe in 0 until computerDeck.size -1) {
//                    if(computerDeck[dupe].first() == computerDeck[dupe + 1].first()) {
//                        addAndRemove(computerDeck,cardsOnTable[dupe],dupe)
//                    }
//                }
//            }
//            suitCheck.size > 1 -> {
//                addAndRemove(computerDeck,cardsOnTable[suitCheck[0].last().digitToInt()],suitCheck[0].last().digitToInt())
//            }
//            rankCheck.size > 1 -> {
//                addAndRemove(computerDeck,cardsOnTable[rankCheck[0].last().digitToInt()],rankCheck[0].last().digitToInt())
//            }
//            //implement a throw any candidate card at random here.
//        }
//    }
//    fun playerMove(): Boolean {
//        while (true) {
//            emptyDeckDeal(playerDeck)
//            println()
//            cardsOnTableUpdater()
//            println("Cards in hand: ")
//            playerDeck.forEachIndexed {  index, card ->
//                print("${index + 1})$card ")
//            }
//            println()
//            println("Choose a card to play (1-${playerDeck.size}):")
//            val playerInput = readln()
//            if (playerInput == "exit") {
//                return true
//            }
//            try {
//                val nextMove = playerInput.toInt() - 1
//                if (nextMove !in 0 until playerDeck.size) {
//                    continue
//                }
//                dealOrNoDeal("Player", playerDeck, nextMove, this::playerCardsCount, this::playerScoreCount)
//                return false
//            } catch (_: NumberFormatException) {
//            }
//        }
//    }
//
//    fun computerMove() {
//        emptyDeckDeal(computerDeck)
//        val randomCard = computerDeck.random()
//        println()
//        cardsOnTableUpdater()
//        println(computerDeck.joinToString (separator = " "))
//        println("Computer plays $randomCard")
//        dealOrNoDeal("Computer", computerDeck, computerDeck.indexOf(randomCard), this::computerCardsCount, this::computerScoreCount)
//    }
//
//    private fun dealOrNoDeal(
//        type: String,
//        cards: ArrayList<String>,
//        index: Int,
//        totalCards: KMutableProperty0<Int>,
//        totalScore: KMutableProperty0<Int>
//    ) {
//        val currentCard = cards[index]
//        if (cardsOnTable.size > 0) {
//            if (currentCard.first() == cardsOnTable.last().first() || currentCard.last() == cardsOnTable.last().last()) {
//                addAndRemove(cards, currentCard, index)
//                totalCards.set(totalCards.get() + cardsOnTable.size)
//                totalScore.set(totalScore.get() + beforeStringCheck())
//                cardsOnTable.clear()
//                println("$type wins cards")
//                scoreBoard()
//            } else {
//                addAndRemove(cards, currentCard, index)
//            }
//        } else {
//            addAndRemove(cards, currentCard, index)
//        }
//    }
//}
//
//fun main() {
//    val deck = Deck()
//    println("Indigo Card Game")
//
//    while (true) {
//        println("Play first?")
//        val playerChoice = readln()
//
//        if (playerChoice == "yes" || playerChoice == "no") {
//            deck.startDecision = playerChoice
//            deck.shuffle()
//            repeat(4) {
//                deck.cardsOnTable.add(deck.deal())
//                deck.removeCard()
//            }
//            println("Initial cards on the table: ${deck.cardsOnTable.joinToString(separator = " ")}")
//            break
//        }
//    }
//
//    while (deck.mainDeck.size > 0) {
//        deck.startDecision
//        if (deck.startDecision()) {
//            println("Game Over")
//            break
//        }
//        if (deck.mainDeck.size == 0) {
//            when {
//                deck.playerCardsCount > deck.computerCardsCount -> {
//                    deck.playerScoreCount += deck.beforeStringCheck() + 3
//                    deck.playerCardsCount += deck.cardsOnTable.size
//                }
//                deck.computerCardsCount > deck.playerCardsCount -> {
//                    deck.computerScoreCount += deck.beforeStringCheck() + 3
//                    deck.computerCardsCount += deck.cardsOnTable.size
//                }
//                else -> {
//                    if (deck.startDecision == "yes") {
//                        deck.playerScoreCount += 3
//                    } else {
//                        deck.computerScoreCount += 3
//                    }
//                }
//            }
//            println()
//            deck.cardsOnTableUpdater()
//            deck.scoreBoard()
//            println("Game Over")
//        }
//    }
//}
//
