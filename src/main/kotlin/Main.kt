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
    val computerDeck = ArrayList<String>()
    var playerScoreCount = 0
    var playerCardsCount = 0
    var computerScoreCount = 0
    var computerCardsCount = 0
    var startDecision = ""
    var playerLastWon = ""

    fun shuffle() = mainDeck.shuffle()

    fun deal(): String = mainDeck[0]

    fun removeCard(): String = mainDeck.removeAt(0)

    fun cardsOnTableUpdater() {
        return when (cardsOnTable.size) {
            0 -> println("No cards on the table")
            else -> println("${cardsOnTable.size} cards on the table, and the top card is ${cardsOnTable.last()}")
        }
    }

    fun startDecision(): Boolean {
        when (startDecision) {
            "yes" -> {
                if (playerMove()) {
                    return true
                }
                computerMove()
            }
            "no" -> {
                computerMove()
                if (playerMove()) {
                    return true
                }
            }
        }
        return false
    }

    fun beforeStringCheck(list: ArrayList<String>): Int {
        var y = 0
        for (x in list) {
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

    fun emptyDeckDeal(deck: ArrayList<String>) {
        if (deck.size < 1) {
            repeat(6) {
                deck.add(deal())
                removeCard()
            }
        }
    }

    fun computerMoveLogic(): Int {
        var cardsPosition = 0
        val suitCheck = ArrayList<Int>()
        val rankCheck = ArrayList<Int>()
        fun winningCardCheck() {
            if (cardsOnTable.size > 0) {
                for(j in computerDeck) {
                    if(j.last() == cardsOnTable.last().last())
                        suitCheck.add(computerDeck.indexOf(j))
                }
                for(j in computerDeck) {
                    if(j.first() == cardsOnTable.last().first())
                        rankCheck.add(computerDeck.indexOf(j))
                }
            }
        }
        fun suitCheck(): Boolean {
            val tempList = computerDeck.toMutableList()
            tempList.sortBy { it.last() }
            for(dupe in 0 until tempList.size -1) {
                if(tempList[dupe].last() == tempList[dupe + 1].last()) {
                    cardsPosition = computerDeck.indexOf(tempList[dupe])
                    return true
                }
            }
            return false
        }

        fun rankCheck(): Boolean {
            val tempList = computerDeck.toMutableList()
            tempList.sortBy { it.first() }
            for(dupe in 0 until tempList.size -1) {
                if(tempList[dupe].first() == tempList[dupe + 1].first()) {
                    cardsPosition = computerDeck.indexOf(tempList[dupe])
                    return true
                }
            }
            return false
        }

        fun randomCard(){
            cardsPosition = computerDeck.indexOf(computerDeck.random())
        }

        if (computerDeck.size == 1) {
            return 0
        }

        winningCardCheck()
        val suitDistinct = suitCheck.distinct()
        val rankDistinct = rankCheck.distinct()
        when {
            suitDistinct.size + rankDistinct.size == 1 -> {
                cardsPosition = if(suitDistinct.size == 1) {
                    suitDistinct[0]
                } else {
                    rankDistinct[0]
                }
            }
            cardsOnTable.size == 0 -> {
                while (true) {
                    when {
                        suitCheck() -> break
                        rankCheck() -> break
                        else -> {
                            randomCard()
                            break
                        }
                    }
                }
            }
            suitDistinct.size + rankDistinct.size == 0 -> {
                while (true) {
                    when {
                        suitCheck() -> break
                        rankCheck() -> break
                        else -> {
                            randomCard()
                            break
                        }
                    }
                }
            }

            suitDistinct.size + rankDistinct.size >= 2 -> {
                cardsPosition = if(suitDistinct.size >= 2) {
                    suitDistinct[0]
                } else {
                    rankDistinct[0]
                }
                cardsPosition = when {
                    suitDistinct.size >= 2 -> suitDistinct[0]
                    rankDistinct.size >= 2 -> rankDistinct[0]
                    else -> if(suitDistinct.isEmpty()) {
                        rankDistinct[0]
                    } else {
                        suitDistinct[0]
                    }
                }
            }
        }
        return cardsPosition
    }
    fun playerMove(): Boolean {
        while (true) {
            emptyDeckDeal(playerDeck)
            println()
            cardsOnTableUpdater()
            println("Cards in hand: ")
            playerDeck.forEachIndexed {  index, card ->
                print("${index + 1})$card ")
            }
            println()
            println("Choose a card to play (1-${playerDeck.size}):")
            val playerInput = readln()
            if (playerInput == "exit") {
                return true
            }
            try {
                val nextMove = playerInput.toInt() - 1
                if (nextMove !in 0 until playerDeck.size) {
                    continue
                }
                dealOrNoDeal("Player", playerDeck, nextMove, this::playerCardsCount, this::playerScoreCount,"Player")
                return false
            } catch (_: NumberFormatException) {
            }
        }
    }

    fun computerMove() {
        emptyDeckDeal(computerDeck)
        println()
        cardsOnTableUpdater()
        val randomCard = computerMoveLogic()
        println(computerDeck.joinToString (separator = " "))
        println("Computer plays ${computerDeck[randomCard]}")
        dealOrNoDeal("Computer", computerDeck, randomCard, this::computerCardsCount, this::computerScoreCount,"Computer")
    }

    private fun dealOrNoDeal(
        type: String,
        cards: ArrayList<String>,
        index: Int,
        totalCards: KMutableProperty0<Int>,
        totalScore: KMutableProperty0<Int>,
        whoWonLast: String
    ) {
        val currentCard = cards[index]
        if (cardsOnTable.size > 0) {
            if (currentCard.first() == cardsOnTable.last().first() || currentCard.last() == cardsOnTable.last().last()) {
                addAndRemove(cards, currentCard, index)
                totalCards.set(totalCards.get() + cardsOnTable.size)
                totalScore.set(totalScore.get() + beforeStringCheck(cardsOnTable))
                cardsOnTable.clear()
                println("$type wins cards")
                scoreBoard()
                playerLastWon = whoWonLast
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
            deck.startDecision = playerChoice
            deck.shuffle()
            repeat(4) {
                deck.cardsOnTable.add(deck.deal())
                deck.removeCard()
            }
            println("Initial cards on the table: ${deck.cardsOnTable.joinToString(separator = " ")}")
            break
        }
    }

    while (true) {
        deck.startDecision
        if (deck.startDecision()) {
            println("Game Over")
            break
        }

        if (deck.mainDeck.size == 0 && deck.playerDeck.size == 0 && deck.computerDeck.size == 0) {
            when(deck.playerLastWon) {
//                "nobody" -> {
//                    if(deck.startDecision == "yes") {
//                        deck.playerDeck += deck.cardsOnTable
//                    } else deck.computerDeck += deck.cardsOnTable
//                }
                "Player" -> deck.playerDeck += deck.cardsOnTable
                "Computer" -> deck.computerDeck += deck.cardsOnTable
            }

            deck.playerScoreCount += deck.beforeStringCheck(deck.playerDeck)
            deck.playerCardsCount += deck.playerDeck.size
            deck.computerScoreCount += deck.beforeStringCheck(deck.computerDeck)
            deck.computerCardsCount += deck.computerDeck.size

            when {
                deck.playerCardsCount > deck.computerCardsCount ->  {
                    deck.playerScoreCount += 3
                }
                deck.computerCardsCount > deck.playerCardsCount -> {
                    deck.computerScoreCount += 3
                }
                else ->
                    if (deck.startDecision == "yes") {
                        deck.playerScoreCount += 3
                    } else {
                        deck.computerScoreCount += 3
                    }
            }
            println()
            deck.cardsOnTableUpdater()
            deck.scoreBoard()
            println("Game Over")
            break
        }
    }
}