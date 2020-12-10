package day9

import utils.getResourceAsLines

fun main() {
    println(solve2())
}

val input = getResourceAsLines("/day9/input.txt")

fun solve1(): Int {
    var lower = 0
    var upper = 24


    var currentNum = 25

    while (currentNum < input.size) {
        if (!isValid(lower, upper, currentNum))
            return input[currentNum].toInt()
        lower++
        upper++
        currentNum++
    }
    return 0
}

fun solve2(): Int {
    val firstInvalidSum = solve1()


    var i = 0
    var remainder = firstInvalidSum
    var indices = emptyList<Int>().toMutableList()


    while (i < input.size) {
        var value = input[i].toInt()
        indices.add(i)
        if (remainder >= value) {
            i++
            remainder -= value
            if (remainder == 0)
                return addMaxMin(indices)
        }
        else {
            i = indices[0]
            i++
            indices = emptyList<Int>().toMutableList()
            remainder = firstInvalidSum
        }


    }
    return 0
}

fun addMaxMin(indices: List<Int>): Int {
    var min = input[indices[0]].toInt()
    var max = input[indices[0]].toInt()

    for(i in indices){
        var value = input[i].toInt()
        if(value > max)
            max = value
        else if(value < min)
            min = value


    }
    return min + max
}

fun isValid(lower: Int, upper: Int, current: Int): Boolean {
    val map = mutableMapOf<Int, Int>()
    var lower = lower

    while (lower <= upper) {
        val n = input[lower].toInt()
        val x = map[n]
        if (x != null)
            return true
        map[input[current].toInt() - n] = n
        lower++
    }
    return false
}