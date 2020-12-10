package day10

import utils.getResourceAsInts
import utils.getResourceAsLines
import kotlin.math.exp
import kotlin.math.max

fun main() {


    println(solve2())

}

val input = getResourceAsInts("/day10/input.txt").sorted().toMutableList()


fun solve1(): Int {

    var current = 0

    var numOf1s = 0
    var numOf3s = 0

    for (adapter in input) {


        if (adapter - current == 1)
            numOf1s++
        else if (adapter - current == 3)
            numOf3s++

        current = adapter


    }

    return numOf1s * (numOf3s + 1)
}


fun solve2(): Long {
    input.add(0, 0)
    val history = mutableMapOf<Int, Long>()
    fun explore(currentIndex: Int): Long {
        var sum = 0L
        if (!history.containsKey(currentIndex))
        {
            for (next in getNextPossibleAdapters(currentIndex)) {
                sum += explore(next)
            }
            history.put(currentIndex, max(1, sum))
        }
        else {
            sum += history[currentIndex]!!
        }
        return max(1, sum)
    }
    return explore(0)
}



//fun explore(currentIndex: Int) {
//    if (input[currentIndex] == goal) {
//        allSolutions++
//        return
//    }
//    for (next in getNextPossibleAdapters(currentIndex)) {
//        explore(next)
//    }
//
//}


fun getNextPossibleAdapters(currentIndex: Int): List<Int> {

    val next = emptyList<Int>().toMutableList()
    for (i in 1..3) {
        try {
            if (input[currentIndex + i] - input[currentIndex] in 1..3)
                next.add(currentIndex + i)
        } catch (e: IndexOutOfBoundsException) {
        }


    }
    return next
}

