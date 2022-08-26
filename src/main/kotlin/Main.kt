//class Deck {
//    companion object{
//        private val mainDeckOfCards = listOf(
//            "A♠","2♠", "3♠", "4♠", "5♠" ,"6♠", "7♠", "8♠", "9♠", "10♠", "J♠", "Q♠", "K♠",
//            "A♥", "2♥", "3♥", "4♥", "5♥", "6♥", "7♥", "8♥", "9♥", "10♥", "J♥", "Q♥", "K♥",
//            "A♦", "2♦", "3♦", "4♦", "5♦", "6♦", "7♦", "8♦", "9♦", "10♦", "J♦", "Q♦", "K♦",
//            "A♣", "2♣", "3♣", "4♣", "5♣", "6♣", "7♣", "8♣", "9♣", "10♣", "J♣", "Q♣", "K♣"
//        )
//    }
//    val mainDeck = ArrayList(mainDeckOfCards)
//
//    fun reset() {
//        this.mainDeck.clear()
//        this.mainDeck.addAll(mainDeckOfCards)
//    }
//
//    fun shuffle() = this.mainDeck.shuffle()
//
//    fun deal(i: Int) = this.mainDeck[i]
//}
//
//fun main() {
//    val deck = Deck()
//
//    while (true) {
//
//        println("Choose an action (reset, shuffle, get, exit):")
//
//        when(readln()) {
//
//            "reset" -> {
//                deck.reset()
//                println("Card deck is reset.")
//                continue
//            }
//
//            "shuffle" -> {
//                deck.shuffle()
//                println("Card deck is shuffled.")
//                continue
//            }
//
//            "get" -> {
//                println("Number of cards:")
//                try{
//                    val noOfCards = readln().toInt()
//                    when {
//
//                        deck.mainDeck.size < noOfCards -> {
//                            println("The remaining cards are insufficient to meet the request.")
//                            continue
//                        }
//
//                        else -> {
//                            var counter = 0
//                            while (counter < noOfCards) {
//                                print("${deck.deal(0)} ")
//                                deck.mainDeck.removeAt(0)
//                                counter++
//                            }
//                            println()
//                        }
//                    }
//                } catch (e: NumberFormatException) {
//                    println("Invalid number of cards.")
//                    continue
//                }
//            }
//
//            "exit" -> {
//                println("Bye")
//                break
//            }
//
//            else -> {
//                println("Wrong Action.")
//                continue
//            }
//        }
//    }
//}