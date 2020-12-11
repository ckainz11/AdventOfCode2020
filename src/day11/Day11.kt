package day11

import utils.getResourceAsLines
import java.lang.IndexOutOfBoundsException

fun main() {
    println(solve2())
}

var inputRaw = getResourceAsLines("/day11/input.txt")
var input = inputRaw.toMutableList()
val maxLength = input[0].length
fun solve1(): Int {
    var occupiedSeatsCount = input.sumBy { it.count { x -> x == '#' } }

    var rule1 = true

    while (true) {
        if (rule1)
            applyRule1()
        else
            applyRule2()
        rule1 = !rule1
        val newOccupiedSeatsCount = input.sumBy { it.count { x -> x == '#' } }
        if (occupiedSeatsCount == newOccupiedSeatsCount)
            return occupiedSeatsCount
        println(occupiedSeatsCount)
        occupiedSeatsCount = newOccupiedSeatsCount
    }
}

fun solve2(): Int {
    var occupiedSeatsCount = input.sumBy { it.count { x -> x == '#' } }

    var rule1 = true

    while (true) {
        if (rule1)
            applyRule1Part2()
        else
            applyRule2Part2()
        rule1 = !rule1
        val newOccupiedSeatsCount = input.sumBy { it.count { x -> x == '#' } }
        if (occupiedSeatsCount == newOccupiedSeatsCount)
            return occupiedSeatsCount
        println(occupiedSeatsCount)
        occupiedSeatsCount = newOccupiedSeatsCount
    }
}

fun applyRule1() {
    val newOccupiedSeats = mutableListOf<Pair<Int, Int>>()

    for ((i, n) in input.withIndex()) {
        for ((j, c) in n.withIndex()) {
            if (c == 'L' && canBeOccupied(i, j)) {
                newOccupiedSeats.add(Pair(i, j))
            }
        }
    }
    for (rowCol in newOccupiedSeats) {
        var chars = input[rowCol.first].toCharArray()
        chars[rowCol.second] = '#'
        input[rowCol.first] = String(chars)
    }


}
fun applyRule2() {
    val newEmptySeats = mutableListOf<Pair<Int, Int>>()
    for ((i, n) in input.withIndex()) {
        for ((j, c) in n.withIndex()) {
            if (c == '#' && canBeEmpty(i, j)) {
                newEmptySeats.add(Pair(i, j))
            }
        }
    }
    for (rowCol in newEmptySeats) {
        var chars = input[rowCol.first].toCharArray()
        chars[rowCol.second] = 'L'
        input[rowCol.first] = String(chars)
    }
}
fun applyRule1Part2() {
    val newOccupiedSeats = mutableListOf<Pair<Int, Int>>()

    for ((i, n) in input.withIndex()) {
        for ((j, c) in n.withIndex()) {
            if (c == 'L' && getNeighbours(i, j, '#') == 0) {
                newOccupiedSeats.add(Pair(i, j))
            }
        }
    }
    for (rowCol in newOccupiedSeats) {
        var chars = input[rowCol.first].toCharArray()
        chars[rowCol.second] = '#'
        input[rowCol.first] = String(chars)
    }


}
fun applyRule2Part2() {
    val newEmptySeats = mutableListOf<Pair<Int, Int>>()
    for ((i, n) in input.withIndex()) {
        for ((j, c) in n.withIndex()) {
            if (c == '#' && getNeighbours(i, j, '#') >= 5) {
                newEmptySeats.add(Pair(i, j))
            }
        }
    }
    for (rowCol in newEmptySeats) {
        var chars = input[rowCol.first].toCharArray()
        chars[rowCol.second] = 'L'
        input[rowCol.first] = String(chars)
    }
}
fun getNeighbours(row: Int, col: Int, type: Char): Int {

    var count = 0
    for(xDir in -1..1){
        for(yDir in -1..1){
            if(yDir == 0 && xDir == 0) continue
            var y = row + yDir
            var x = col + xDir

            while(x in 0 until maxLength && y in inputRaw.indices){
                val neighbour = input[y][x]
                if(neighbour != '.'){
                    if(neighbour == type)
                        count++
                    break
                }
                y += yDir
                x += xDir
            }
        }
    }
    return count
}





fun canBeEmpty(row: Int, column: Int): Boolean {
    var adjacentOccupiedSeats = 0
    for (i in -1..1) {
        for (j in -1..1) {
            try {
                if (input[row + i][column + j] == '#') {
                    if(!(j == 0 && i == 0))
                        adjacentOccupiedSeats++
                }
            } catch (e: IndexOutOfBoundsException) {

            }

        }
    }
    return adjacentOccupiedSeats >= 4
}

fun canBeOccupied(row: Int, column: Int): Boolean {
    for (i in -1..1) {
        for (j in -1..1) {
            try {
                if (input[row + i][column + j] == '#')
                    return false
            } catch (e: IndexOutOfBoundsException) {

            }

        }
    }
    return true
}

