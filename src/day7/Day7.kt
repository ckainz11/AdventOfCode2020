package day7

import utils.getResourceAsLines
import java.lang.Exception
import java.lang.NullPointerException
import kotlin.system.measureTimeMillis

val map = mutableMapOf<String, List<Pair<Int, String>>>()
var count = 0
var count2 = 0
val input = getResourceAsLines("/day7/input.txt")


fun main() {
    for (line in input) {
        val splitted = line.split("contain")
        val keySplitted = splitted[0].split(" ")
        val key = keySplitted[0] + " " + keySplitted[1]
        val bags = splitted[1].split(",")


        var bagList = emptyList<Pair<Int, String>>().toMutableList()
        for (b in bags) {
            val bagSplitted = b.trim().split(" ")
            val colour = bagSplitted[1] + " " + bagSplitted[2]
            val amount = bagSplitted[0]
            if (amount == "no")
                bagList = emptyList<Pair<Int, String>>().toMutableList()
            else
                bagList.add(Pair(amount.toInt(), colour))


        }
        map.put(key, bagList)
    }
    val time = measureTimeMillis { solve2() }

    println("Part 2 completed in $time ms, result: $count2")
}


fun solve1() {
    for ((k, v) in map) {
        val found = explore(k)
        if (found)
            count++
    }
}

fun solve2() {
    explore2("shiny gold")
}

fun explore(key: String): Boolean {
    var found = false
    try {
        for (colour in map[key]!!) {
            if (colour.second == "shiny gold") {
                return true
            } else {
                found = explore(colour.second)
                if (found) {
                    break
                }
            }

        }
    } catch (e: NullPointerException) {
        return found
    }
    return found
}

fun explore2(key: String) {
    for (colour in map[key]!!) {
        count2 += colour.first
        for (n in 1..colour.first)
            explore2(colour.second)
    }
}