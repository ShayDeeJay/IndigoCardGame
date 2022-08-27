class Deck {
    companion object {
        private val mainDeckOfCards = listOf(
            "A♠", "2♠", "3♠", "4♠", "5♠", "6♠", "7♠", "8♠", "9♠", "10♠", "J♠", "Q♠", "K♠",
            "A♥", "2♥", "3♥", "4♥", "5♥", "6♥", "7♥", "8♥", "9♥", "10♥", "J♥", "Q♥", "K♥",
            "A♦", "2♦", "3♦", "4♦", "5♦", "6♦", "7♦", "8♦", "9♦", "10♦", "J♦", "Q♦", "K♦",
            "A♣", "2♣", "3♣", "4♣", "5♣", "6♣", "7♣", "8♣", "9♣", "10♣", "J♣", "Q♣", "K♣"
        )
    }
    /*Companion object holds the origin un-shuffled deck that can be re-used.*/
    val mainDeck = ArrayList(mainDeckOfCards)
    /*Main deck is a copy of the original deck that can be altered.*/
    val cardsOnTable = mutableListOf<String>()
    /*Cards on table holds the current cards in play on the table.*/
    val playerDeck = mutableListOf<String>()
    /*Player deck holds all the cards in players hand.*/
//    fun reset() {
//        this.mainDeck.clear()
//        this.mainDeck.addAll(mainDeckOfCards)
//    }
    /*Reset resets the main deck back to its original un-shuffled state.*/
    fun shuffle() = this.mainDeck.shuffle()
    /*Shuffles deck.*/
    fun deal(): String = this.mainDeck[0]
    /*Deals the top cards on the deck.*/
    fun removeCard(): String = this.mainDeck.removeAt(0)
    /*Removes card at from top (usually from player deck)*/
    fun cardsOnTableUpdater(): String {
        return "${cardsOnTable.size} cards on the table, and the top card is ${cardsOnTable.last()}"
    }
    /*Keeps track of size of cards on table and top card on table, made in to a function so that it can keep the count
    * updated in the main function. */
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
    /*
    Player move in steps
    * 1. Deal 6 cards if the player has no cards, used to deal initial cards as well as refresh when player runs out.
    * 2. Prints out card on table updater.
    * 3. Prints out cards in deck in order with the index +1 for selection.
    */
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
    /*Player move 2 in steps
    * 1. Input the selection from choices above
    * 2. If input is "exit" break from loop using true condition which is checked in main loop to break.
    * 3. Input converted to Int -1 for index correction, checks if the player selection is in range of available selection, if not loops until
    * correct slection is made.
    * 4. Removes the selected card from hand and adds to top of the cards on table
    * 5. Cjecls */

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
    /*Instead of giving the computer an actual hand, just plays and removes the top card from deck.*/
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
    /*Loop step by step
    * 1. Read if player goes first or not (not means computers turn first)
    * 2. Shuffle deck
    * 3. Deal top 4 for cards to table (added to list)
    * 4. Print cards on table.
    * */
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
    /*Main game loop - step by step
    * Yes -> player move function first, then player move 2, if player move 2 is true (player types exit) loop is broken and game ends without
    * finishing.
    * No -> Same as above however computer function is first then player functions.
    * Game ends when no cards are left, in current case, it would be all 52 cards.*/
}