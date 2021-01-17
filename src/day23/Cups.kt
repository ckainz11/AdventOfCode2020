package day23

import java.lang.StringBuilder
import java.util.*
import kotlin.system.measureTimeMillis

class Cups(val cups: MutableList<Int>) {

    private val circle = LinkedList<Int>()


    private var current = -1
    private val min = cups.minBy { it }!!
    private val max = cups.maxBy { it }!!
    private var moveCount = 1
    fun doMove(print: Boolean) {
        current = getCurrent()



        if (print) {
            println("-- move $moveCount --")
            println("cups: " + cups.joinToString(" ") + ", current: $current")
        }
        val pickedUp = pickUp()
        val destination = getDestination()
        if (print) {
            println("pick up: ${pickedUp.joinToString(" ")}")
            println("destination: $destination")
        }
        cups.addAll(cups.indexOf(destination) + 1, pickedUp)
        moveCount++
    }

    fun getCupsAsString(): String {
        var s = ""
        var start = cups.indexOf(1)
        for (i in start + 1 until cups.size) {
            s += cups[i]
        }
        for (j in 0 until start)
            s += cups[j]
        return s
    }

    private fun pickUp(): MutableList<Int> {
        var currentIndex = cups.indexOf(current)
        var pickedUp = mutableListOf<Int>()
        for (i in 1..3) {
            var next = (currentIndex + i) % cups.size
            pickedUp.add(cups[next])

        }
        cups.removeAll(pickedUp)



        return pickedUp
    }

    private fun getDestination(): Int {
        var destination = current - 1
        var n = cups.find { it == destination }
        while (n == null) {
            destination -= 1
            n = cups.find { it == destination }
            if (destination < min)
                destination = max + 1
        }
        return n
    }

    private fun getCurrent(): Int {
        if (current == -1)   //initial value
            return cups[0]
        var currentIndex = cups.indexOf(current)
        var nextCurrentIndex = ((currentIndex + 1) % cups.size)
        return cups[nextCurrentIndex]
    }

    fun getLabelsMultiplied(): Long {
        val i1 = cups[(cups.indexOf(1) + 1) % cups.size]
        val i2 = cups[(cups.indexOf(1) + 2) % cups.size]
        return i1.toLong() * i2.toLong()
    }


}