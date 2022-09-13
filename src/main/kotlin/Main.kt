import kotlin.reflect.KMutableProperty0
class Deck {
    companion object {
        private val mainDeckOfCards = listOf(
            "A♠", "2♠", "3♠", "4♠", "5♠", "6♠", "7♠", "8♠", "9♠", "10♠", "J♠", "Q♠", "K♠",
            "A♥", "2♥", "3♥", "4♥", "5♥", "6♥", "7♥", "8♥", "9♥", "10♥", "J♥", "Q♥", "K♥",
            "A♦", "2♦", "3♦", "4♦", "5♦", "6♦", "7♦", "8♦", "9♦", "10♦", "J♦", "Q♦", "K♦",
            "A♣", "2♣", "3♣", "4♣", "5♣", "6♣", "7♣", "8♣", "9♣", "10♣", "J♣", "Q♣", "K♣"
        )
        //Initial Immutable deck just to keep original content intact.
    }

    private val mainDeck = ArrayList(mainDeckOfCards)
    // Mutable main deck that can be altered based on the game.
    private val cardsOnTable = ArrayList<String>()
    // All the current cards that are in play on the table.
    private val playerDeck = ArrayList<String>()
    private val computerDeck = ArrayList<String>()
    var playerScoreCount = 0
    var playerCardsCount = 0
    var computerScoreCount = 0
    var computerCardsCount = 0
    var startDecision = ""
    //Start decision is used later to determine who began the game for gameplay order.

    var playerLastWon = ""
    // Used to determine who won last incase scores are even at the end, last person to win would win extra point.
    private fun deal(): String = mainDeck[0]
    private fun removeCard(): String = mainDeck.removeAt(0)
    private fun cardsOnTableUpdater() {
        return when (cardsOnTable.size) {
            0 -> println("No cards on the table")
            else -> println("${cardsOnTable.size} cards on the table, and the top card is ${cardsOnTable.last()}")
        }
    }
    //Decides which message to display depending if cards on table, and how many as well as the relevant win card.

    fun whoPlaysFirst(): Boolean {
        while (true) {
            println("Play first?")
            val playerChoice = readln()
            if (playerChoice == "yes" || playerChoice == "no") {
                startDecision = playerChoice
                mainDeck.shuffle()
                repeat(4) {
                    cardsOnTable.add(deal())
                    removeCard()
                }
                println("Initial cards on the table: ${cardsOnTable.joinToString(separator = " ")}")
                return true
            }
        }
    }
    //Player decides who gets to play first, question is on loop in case of incorrect input. Also displays the initial cards dealt on the table.

    private fun startDecision(): Boolean {
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
    //Decides if a computer goes first or player. If statement is for is player chooses to "exit" and returns true to end the game.
    private fun pointWorthyCards(list: ArrayList<String>): Int {
        var y = 0
        for (x in list) {
            when (x.first()) {
                '1', 'A', 'J', 'Q', 'K' -> y++
            }
        }
        return y
    }
    //Checks if the list (computers deck or players deck) contains winnable cards which are worth 1 point each.
    private fun scoreBoard() {
        println(
            """
            Score: Player $playerScoreCount - Computer $computerScoreCount
            Cards: Player $playerCardsCount - Computer $computerCardsCount
            """.trimIndent()
        )
    }
    private fun addAndRemove(a: ArrayList<String>, b: String, c: Int) {
        cardsOnTable.add(b)
        a.removeAt(c)
    }
    //Removes a selected card from player/computers deck and adds it to the table.
    private fun emptyDeckDeal(deck: ArrayList<String>) {
        if (deck.size < 1) {
            repeat(6) {
                deck.add(deal())
                removeCard()
            }
        }
    }
    //Checks if a player/computers deck is empty, if so, replenishes 6 cards.
    private fun computerMoveLogic(): Int {
        var cardsPosition = 0
        //Index of the card in computers hand.
        val suitCheck = ArrayList<Int>()
        //Hold multiple suits from deck, used later for move logic.
        val rankCheck = ArrayList<Int>()
        //Hold multiple ranks from deck, used later for move logic.

        fun suitCheckMultiple(): Boolean {
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
        //Computers deck is sorted by "suit" to see if there are multiples, used in later logic to throw if there are no winnable cards.

        fun rankCheckMultiple(): Boolean {
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
        //Computers deck is sorted by "rank" to see if there are multiples, used in later logic to throw if there are no winnable cards.

        fun multiUseCardCheck() {
            while (true) {
                when {
                    suitCheckMultiple() -> break
                    rankCheckMultiple() -> break
                    else -> {
                        computerDeck.indexOf(computerDeck.random())
                        break
                    }
                }
            }
        }
        //If no winnable cards, check to see if there are multiple suits, if not, then check multiple ranks, if neither apply, plays a card at random.

        if (computerDeck.size == 1) {
            return 0
        }
        //if there is only one card in computers deck, plays it.

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
        //Checks if any cards in hand match the top card on the table to win. first loops checks the "suit", and second loop checks the "rank".

        val suitDistinct = suitCheck.distinct()
        val rankDistinct = rankCheck.distinct()
        //Sorts out any all the distinct cards from multiple check so see if there are more than one multiple in hand.

        when {
            suitDistinct.size + rankDistinct.size == 1 -> {
                cardsPosition = if(suitDistinct.size == 1) {
                    suitDistinct[0]
                } else {
                    rankDistinct[0]
                }
            }
            //Checks to see if there is only one type multiple(one "suit" multiple or "rank").

            cardsOnTable.size == 0 -> {
                multiUseCardCheck()
            }
            //Check function.

            suitDistinct.size + rankDistinct.size == 0 -> {
                multiUseCardCheck()
            }
            //Check function.

            suitDistinct.size + rankDistinct.size >= 2 -> {
                cardsPosition = when {
                    suitDistinct.size >= 2 -> suitDistinct[0]
                    rankDistinct.size >= 2 -> rankDistinct[0]
                    else -> suitDistinct[0]
                }
                //1. If there are or 2 or more in suit, play one at random (in this case we just play 0).
                //2. If there are or 2 or more in rank, play one at random (in this case we just play 0).
                //3. If there is only 2 or cards in total, only one in each (suit + rank) just play suit card.
            }
        }
        return cardsPosition
        //Adds card to variable, used later for index location in computers deck to make choice.
    }
    private fun playerMove(): Boolean {
        emptyDeckDeal(playerDeck)
        //Check function.

        println()
        cardsOnTableUpdater()
        //Check function.

        println("Cards in hand: ")
        playerDeck.forEachIndexed {  index, card ->
            print("${index + 1})$card ")
        }
        //Prints out cards in players hand to with the index of those cards to for player to make choice (cards index starts from 1).

        println()
        while (true) {
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
                //Check function.
                return false
            } catch (_: NumberFormatException) {
            }
        }
        //Loop is to ensure player makes applicable choice for available cards, as well as valid input.
    }
    private fun computerMove() {
        emptyDeckDeal(computerDeck)
        //Check function.

        println()
        cardsOnTableUpdater()
        //Check function.

        val logicalCard = computerMoveLogic()
        //Assigns computers choice to variable.

        println(computerDeck.joinToString (separator = " "))
        //Computers deck is printed here to ensure computer has made correct logical choice.
        
        println("Computer plays ${computerDeck[logicalCard]}")
        dealOrNoDeal("Computer", computerDeck, logicalCard, this::computerCardsCount, this::computerScoreCount,"Computer")
        //Check function.
    }
    private fun dealOrNoDeal(
        type: String, //Assigned to player or computer.
        cards: ArrayList<String>, //Assigned deck.
        index: Int, //Index of selected card.
        totalCards: KMutableProperty0<Int>, //KmutableProperty is used here to reassign external Mutable "VAR" variable to this function.
        totalScore: KMutableProperty0<Int>,
        whoWonLast: String //See variable at top.
    ) {
        val currentCard = cards[index] // This is the chosen list, and chosen index position.
        if (cardsOnTable.size > 0) {
            // First check to see if there are cards on the table, if not jump to else, where logical card is chosen.
            if (currentCard.first() == cardsOnTable.last().first() || currentCard.last() == cardsOnTable.last().last()) {
                addAndRemove(cards, currentCard, index)
                totalCards.set(totalCards.get() + cardsOnTable.size)
                totalScore.set(totalScore.get() + pointWorthyCards(cardsOnTable))
                cardsOnTable.clear()
                println("$type wins cards")
                scoreBoard()
                playerLastWon = whoWonLast
                //This checks if the card selection made is a winning card, if so, the card is removed from deck and added to the cards on table.
                //Then the cards from table are removed, and added to winners winning pile, if there are point worthy cards, those are added to tally.
            } else {
                addAndRemove(cards, currentCard, index) //Check function.
            }
        } else {
            addAndRemove(cards, currentCard, index) //Check function.
        }
    }
    fun isGameOver(): Boolean {
        while (true) {
            if (startDecision()) {
                //Checks to see if player has typed "exit" to end the game, otherwise will continue to play.
                println("Game Over")
                return true
            }
            if (mainDeck.size == 0 && playerDeck.size == 0 && computerDeck.size == 0) {
                //If no playable cards are available at all the game ends.
                when(playerLastWon) {
                    "Player" -> playerDeck += cardsOnTable
                    "Computer" -> computerDeck += cardsOnTable
                    //whoever won last, gets all the remaining cards on the table (if applicable), this will count towards the score.
                }

                playerScoreCount += pointWorthyCards(playerDeck)
                playerCardsCount += playerDeck.size
                computerScoreCount += pointWorthyCards(computerDeck)
                computerCardsCount += computerDeck.size
                //This will count up all the cards and points that both player and computer have.

                when {
                    playerCardsCount > computerCardsCount ->  {
                        playerScoreCount += 3
                    }
                    computerCardsCount > playerCardsCount -> {
                        computerScoreCount += 3
                    }

                    //Whoever has the most cards at the end, will gain an additional 3 points.
                    else ->
                        if (startDecision == "yes") {
                            playerScoreCount += 3
                        } else {
                            computerScoreCount += 3
                        }
                    //If card counts are tied, whoever played first wins the additional points.
                }
                println()
                cardsOnTableUpdater()
                scoreBoard()
                println("Game Over")
                return true
            }
        }
    }
}
fun main() {
    val deck = Deck()
    println("Indigo Card Game")

    deck.whoPlaysFirst()

    deck.isGameOver()
}