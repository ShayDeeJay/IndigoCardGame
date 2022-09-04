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
//    fun computerMoveLogic(): Int {
//        var cardsPosition = 0
//        val suitCheck = ArrayList<String>()
//        val rankCheck = ArrayList<String>()
//        fun suitCheck() {
//            val tempList = computerDeck.toMutableList()
//            tempList.sortBy { it.last() }
//            for(dupe in 0 until tempList.size -1) {
//                if(tempList[dupe].last() == tempList[dupe + 1].last()) {
//                    cardsPosition = computerDeck.indexOf(tempList[dupe])
//                    return
//                }
//            }
//        }
//
//        fun rankCheck() {
//            val tempList = computerDeck.toMutableList()
//            tempList.sortBy { it.first() }
//            for(dupe in 0 until tempList.size -1) {
//                if(tempList[dupe].first() == tempList[dupe + 1].first()) {
//                    cardsPosition = computerDeck.indexOf(tempList[dupe])
//                    return
//                }
//            }
//        }
//
//        fun randomCard() {
//            cardsPosition = computerDeck.indexOf(computerDeck.random())
//        }
//
//        if (computerDeck.size == 1) {
//            return 0
//        }
//
//        for (i in cardsOnTable) {
//            for(j in computerDeck) {
//                if(j.last() == i.last())
//                    suitCheck.add(j.last().toString() + computerDeck.indexOf(j))
//            }
//        }
//
//        for (i in cardsOnTable) {
//            for(j in computerDeck) {
//                if(j.first() == i.first())
//                    rankCheck.add(j.first().toString() + computerDeck.indexOf(j))
//            }
//        }
//
//        val suitDistinct = suitCheck.distinct()
//        val rankDistinct = rankCheck.distinct()
//
//        when {
//            suitDistinct.size + rankDistinct.size == 1 -> {
//                cardsPosition = if(suitDistinct.size == 1) {
//                    suitDistinct[0].last().digitToInt()
//                } else {
//                    rankDistinct[0].last().digitToInt()
//                }
//            }
//            cardsOnTable.size == 0 -> {
//                suitCheck()
//                rankCheck()
//                randomCard()
//            }
//            suitDistinct.size + rankDistinct.size == 0 -> {
//                suitCheck()
//                rankCheck()
//                randomCard()
//            }
//            suitDistinct.size + rankDistinct.size >= 2 -> {
//                cardsPosition = if(suitDistinct.size >= 2) {
//                    suitDistinct[0].last().digitToInt()
//                } else {
//                    rankDistinct[0].last().digitToInt()
//                }
//                cardsPosition = when {
//                    suitDistinct.size >= 2 -> suitDistinct[0].last().digitToInt()
//                    rankDistinct.size >= 2 -> rankDistinct[0].last().digitToInt()
//                    else -> if(suitDistinct.isEmpty()) {
//                        rankDistinct[0].last().digitToInt()
//                    } else {
//                        suitDistinct[0].last().digitToInt()
//                    }
//                }
//            }
//        }
//        return cardsPosition
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
//        val randomCard = computerMoveLogic()
//        println()
//        cardsOnTableUpdater()
//        println(computerDeck.joinToString (separator = " "))
//        println("Computer plays ${computerDeck[randomCard]}")
//        dealOrNoDeal("Computer", computerDeck, randomCard, this::computerCardsCount, this::computerScoreCount)
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
