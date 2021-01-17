package day22

import utils.getResourceAsString
import java.util.*
import kotlin.system.measureTimeMillis


val deck1: Queue<Int> = ArrayDeque<Int>()
val deck2: Queue<Int> = ArrayDeque<Int>()
val input = getResourceAsString("/day22/input.txt")
fun main() {
    var res1 = 0L
    val time1 = measureTimeMillis {
        res1 = part1()
    }
    println("Solution for part 1: $res1")
    println("In $time1 ms")
    var res2 = 0L
    val time2 = measureTimeMillis {
        res2 = part2()
    }
    println("Solution for part 1: $res2")
    println("In $time2 ms")
}
val lsp = System.getProperty("line.seperator")
fun part1(): Long {
    val splitted = input.split("\n\n")
    deck1.addAll(splitted[0].lines().drop(1).map { it.toInt() })
    deck2.addAll(splitted[1].lines().drop(1).map { it.toInt() })
    return playGame(ArrayDeque(deck1.toList()), ArrayDeque(deck2.toList()))

}
fun playGame(deck1: Queue<Int>, deck2: Queue<Int>): Long {
    while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
        val card1 = deck1.remove()
        val card2 = deck2.remove()
        if (card1 > card2) {
            deck1.addAll(listOf(card1, card2))
        } else {
            deck2.addAll(listOf(card2, card1))
        }
    }
    val winner = if (deck1.isEmpty()) deck2 else deck1
    return getScore(winner)
}

fun part2(): Long {
    val winner = playRecursiveGame(deck1, deck2)
    val winnerDeck = if (winner) deck1 else deck2
    return getScore(winnerDeck)
}


fun playRecursiveGame(deck1: Queue<Int>, deck2: Queue<Int>): Boolean {
    val history = mutableSetOf<Pair<Queue<Int>, Queue<Int>>>()
    //rintln("-----Game Start-----\n")
    while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
//        println("-----Round Start-----")
//        println("Player 1's deck: ${deck1.joinToString(", ")}")
//        println("Player 2's deck: ${deck2.joinToString(", ")}")

        if (isRepeatedRound(history, deck1, deck2)) {
            //println("Game ends due to infinity rule")
            return true
        }
        history.add(Pair(ArrayDeque(deck1), ArrayDeque(deck2)))
        val card1 = deck1.remove()
        val card2 = deck2.remove()
//        println("Player 1 plays: $card1")
//        println("Player 1 plays: $card2")

        var winner: Boolean
        winner = if (card1 <= deck1.size && card2 <= deck2.size) {
            val deck1Copy = ArrayDeque<Int>(deck1.toList().take(card1))
            val deck2Copy = ArrayDeque<Int>(deck2.toList().take(card2))
//            println("Playing subgame to determine winner")
            playRecursiveGame(deck1Copy, deck2Copy) //player1 = true, player2 = false
        } else card1 > card2
        if (winner)
            deck1.addAll(listOf(card1, card2))
        else
            deck2.addAll(listOf(card2, card1))
//        println("-----Round End-----\n")

    }
//    println("Game ends because of empty deck! Winner: ${deck1.isNotEmpty()}")
//    println("-----Game End-----\n")
    return deck1.isNotEmpty()
}



fun getScore(winnerDeck: Queue<Int>): Long {
    var score = 0L
    for ((i, card) in winnerDeck.reversed().withIndex()) {
        score += card * (i + 1)
    }
    return score
}
fun isRepeatedRound(history: Set<Pair<Queue<Int>, Queue<Int>>>, deck1: Queue<Int>, deck2: Queue<Int>): Boolean{
    for(round in history){
        val pastDeck1 = round.first
        val pastDeck2 = round.second
        if (pastDeck1.size == deck1.size && pastDeck2.size == deck2.size){
            val pair1 = pastDeck1.zip(deck1)
            val pair2 = pastDeck2.zip(deck2)
            val b = pair1.all { (el, el2) -> el == el2 } && pair2.all { (el, el2) -> el == el2 }
            if(b)
                return b
        }
    }
    return false
}