<<<<<<< Updated upstream
////class Deck {
//
////    private val cardsOnTable = mutableListOf<String>("8♣", "10♣", "J♣", "7♠")
////    private val computerDeck = mutableListOf<String>("2♣", "9♥", "5♠", "3♦")
////    private val cardsOnTable = mutableListOf<String>("4♦", "5♦", "10♠", "K♣")
////    private val computerDeck = mutableListOf<String>("Q♠", "8♠", "Q♦", "2♦", "10♦", "8♣")
////    val cardPosition = computerDeck.indexOf(tempLid)
////    fun suitCheck() {
////        val tempList = computerDeck.toMutableList()
////        tempList.sortBy { it.last() }
////        for(dupe in 0 until tempList.size -1) {
////            if(tempList[dupe].last() == tempList[dupe + 1].last()) {
////                cardsPosition = computerDeck.indexOf(tempList[dupe])
////                return
////            }
////        }
////    }
//
//
////    fun computerMoveLogic() {
////        var cardsPosition = 0
////        val suitCheck = ArrayList<String>()
////        val rankCheck = ArrayList<String>()
////        fun suitCheck() {
////            val tempList = computerDeck.toMutableList()
////            tempList.sortBy { it.last() }
////            for(dupe in 0 until tempList.size -1) {
////                if(tempList[dupe].last() == tempList[dupe + 1].last()) {
////                    cardsPosition = computerDeck.indexOf(tempList[dupe])
////                    return
////                }
////            }
////        }
////
////        fun rankCheck() {
////            val tempList = computerDeck.toMutableList()
////            tempList.sortBy { it.first() }
////            for(dupe in 0 until tempList.size -1) {
////                if(tempList[dupe].first() == tempList[dupe + 1].first()) {
////                    cardsPosition = computerDeck.indexOf(tempList[dupe])
////                    return
////                }
////            }
////        }
////
////        fun randomCard() {
////            cardsPosition = computerDeck.indexOf(computerDeck.random())
////        }
////
////        if (computerDeck.size == 1) {
////            cardsPosition = 0
////        }
////
////        for(j in computerDeck) {
////            if(j.last() == cardsOnTable.last().last())
////                suitCheck.add(j.last().toString() + computerDeck.indexOf(j))
////        }
////
////        for(j in computerDeck) {
////            if(j.first() == cardsOnTable.last().first())
////                rankCheck.add(j.last().toString() + computerDeck.indexOf(j))
////        }
////
////        val suitDistinct = suitCheck.distinct()
////        val rankDistinct = rankCheck.distinct()
////
////        when {
////            suitDistinct.size + rankDistinct.size == 1 -> {
////                cardsPosition = if(suitDistinct.size == 1) {
////                    suitDistinct[0].last().digitToInt()
////                } else {
////                    rankDistinct[0].last().digitToInt()
////                }
////            }
////            cardsOnTable.size == 0 -> {
////                suitCheck()
////                rankCheck()
////                randomCard()
////            }
////            suitDistinct.size + rankDistinct.size == 0 -> {
////                suitCheck()
////                rankCheck()
////                randomCard()
////            }
////            suitDistinct.size + rankDistinct.size >= 2 -> {
////                cardsPosition = if(suitDistinct.size == 2) {
////                    suitDistinct[0].last().digitToInt()
////                } else {
////                    rankCheck[0].last().digitToInt()
////                }
////                cardsPosition = when {
////                    suitDistinct.size >= 2 -> suitDistinct[0].last().digitToInt()
////                    rankDistinct.size >= 2 -> rankDistinct[0].last().digitToInt()
////                    else -> if(suitDistinct.isEmpty()) {
////                        rankDistinct[0].last().digitToInt()
////                    } else {
////                        suitDistinct[0].last().digitToInt()
////                    }
////                }
////            }
////        }
////    }
////}
//fun main() {
//    val cardsOnTable = ArrayList<String>()
//    val computerDeck = mutableListOf<String>("A♣", "3♦","8♦", "Q♠", "Q♥")
//    var cardsPosition = 0
//    fun suitCheck(): Boolean{
//        val tempList = computerDeck.toMutableList()
//        tempList.sortBy { it.last() }
//        for(dupe in 0 until tempList.size -1) {
//            if(tempList[dupe].last() == tempList[dupe + 1].last()) {
//                cardsPosition = computerDeck.indexOf(tempList[dupe])
//                return true
//            }
=======
class Deck {

//    private val cardsOnTable = mutableListOf<String>("8♣", "10♣", "J♣", "7♠")
//    private val computerDeck = mutableListOf<String>("2♣", "9♥", "5♠", "3♦")
    private val cardsOnTable = mutableListOf<String>()
    private val computerDeck = mutableListOf<String>("2♦", "A♠", "Q♠")



    fun computerMoveLogic() {
        var cardsPosition = 0
        val suitCheck = ArrayList<String>()
        val rankCheck = ArrayList<String>()
//        fun suitCheck(): Int {
//            val cardPosition = 0
//            computerDeck.sortBy { it.last() }
//            for(dupe in 0 until computerDeck.size -1) {
//                if(computerDeck[dupe].last() == computerDeck[dupe + 1].last()) {
//                    cardsPosition = dupe
//                }
//            }
//            return cardsPosition
//        }

        fun rankCheck() {
            computerDeck.sortBy { it.first() }
            for(dupe in 0 until computerDeck.size -1) {
                if(computerDeck[dupe].first() == computerDeck[dupe + 1].first()) {
                    cardsPosition = dupe
                    return
                }
            }
        }

        fun randomCard() {
            computerDeck.indexOf(computerDeck.random())
        }

//        if (computerDeck.size == 1) {
//            cardsPosition = 0
//            return 0
//        }

        for (i in cardsOnTable) {
            for(j in computerDeck) {
                if(j.last() == i.last())
                    suitCheck.add(j.last().toString() + computerDeck.indexOf(j))
            }
        }

        for (i in cardsOnTable) {
            for(j in computerDeck) {
                if(j.first() == i.first())
                    rankCheck.add(j.first().toString() + computerDeck.indexOf(j))
            }
        }

        val suitDistinct = suitCheck.distinct()
        val rankDistinct = rankCheck.distinct()

        when {
//            suitDistinct.size + rankDistinct.size == 1 -> {
//                cardsPosition = if(suitDistinct.size == 1) {
//                    suitDistinct[0].last().digitToInt()
//                } else {
//                    rankCheck[0].last().digitToInt()
//                }
//            }
            cardsOnTable.size == 0 -> {
//                cardsPosition
                computerDeck.sortBy { it.last() }
                for(dupe in 0 until computerDeck.size -1) {
                    if(computerDeck[dupe].last() == computerDeck[dupe + 1].last()) {
                        println(computerDeck[dupe])
                    }
                }
//                rankCheck()
//                randomCard()
            }
            suitDistinct.size + rankDistinct.size == 0 -> {
//                suitCheck()
                rankCheck()
                randomCard()
            }
            suitDistinct.size + rankDistinct.size >= 2 -> {
//                suitCheck()
                rankCheck()
                randomCard()
            }
            suitDistinct.size + rankDistinct.size == 1 -> {
                cardsPosition = if(suitDistinct.size == 1) {
                    suitDistinct[0].last().digitToInt()
                } else {
                    rankCheck[0].last().digitToInt()
                }
            }
        }
//        return cardsPosition
    }
}
fun main() {
        val deck = Deck()
        deck.computerMoveLogic()
//        println(deck.computerMoveLogic())

}
//    val deck = Deck()
//    val suitCheck = ArrayList<String>()
//    val rankCheck = ArrayList<String>()
//
//    for (i in deck.cardsOnTable) {
//        for (j in deck.computerDeck) {
//            if (j.first() == i.first())
//                rankCheck.add(j.first().toString() + deck.computerDeck.indexOf(j))
>>>>>>> Stashed changes
//        }
//        return false
//    }
//
//    fun rankCheck(): Boolean {
//        val tempList = computerDeck.toMutableList()
//        tempList.sortBy { it.first() }
//        for(dupe in 0 until tempList.size -1) {
//            if(tempList[dupe].first() == tempList[dupe + 1].first()) {
//                cardsPosition = computerDeck.indexOf(tempList[dupe])
//                return true
//            }
//        }
//        return false
//    }
//
//    fun randomCard() {
//        cardsPosition = computerDeck.indexOf(computerDeck.random())
//    }
//
//    when {
//        cardsOnTable.size == 0 -> {
//            while (true) {
//                when {
//                    suitCheck() -> return
//                    rankCheck() -> return
//                    else -> {
//                        cardsPosition = 0
//                        break
//                    }
//                }
//            }
//        }
//    }
//    println(cardsPosition)
//}
//
//
//
//
////}
////    val deck = Deck()
////    val suitCheck = ArrayList<String>()
////    val rankCheck = ArrayList<String>()
////
////    for (i in deck.cardsOnTable) {
////        for (j in deck.computerDeck) {
////            if (j.first() == i.first())
////                rankCheck.add(j.first().toString() + deck.computerDeck.indexOf(j))
////        }
////    }
////    for (i in deck.cardsOnTable) {
////        for (j in deck.computerDeck) {
////            if (j.last() == i.last())
////                suitCheck.add(j.last().toString() + deck.computerDeck.indexOf(j))
////        }
////    }
//
////    for (pairs in 0 until deck.computerDeck.size - 1) {
////        if(sortedRank[pairs].first() == sortedRank[pairs + 1].first()) {
////            println(sortedRank[pairs])
////        }
////    }
//
////    println(deck.computerDeck.sorted().last())
//
////    val computerDeck = deck.computerDeck
////
////    val tempList = ArrayList<String>()
////
////    for (check in 0 until computerDeck.size - 1) {
////        if(computerDeck[check] == computerDeck)
////    }
////
////    for(check in computerDeck) {
////        if (computerDeck[check] == )
////    }
//
////    for (x in 0 until computerDeck.size) {
////        if(computerDeck[x].first() in computerDeck[x]) {
////            println(computerDeck[x])
////        }
////
////    }
//
//
////    for(x in computerDeck) {
////       print("${x.last()} ${x.indexOf(x)} ")
////    }
////
////    computerDeck.sortBy { it.last() }
////
////    println(computerDeck)
////
////    for(x in 0 until computerDeck.size - 1) {
////        if(computerDeck[x].last() == computerDeck[x + 1].last()) {
////            println(computerDeck[x])
////        }
////    }
//
//
////    tempList.sort
////
////    println(tempList)
//
////}