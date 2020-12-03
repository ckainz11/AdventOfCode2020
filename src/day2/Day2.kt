package day2

import utils.getResourceAsLines
import kotlin.system.measureTimeMillis


val input = getResourceAsLines("/day2/input.txt")

fun main() {

    println(solve2())

}


fun solve1(): Int {
    var validPasswords = 0

    for (line in input) {
        val splitted = line.split(" ")
        val requirements = splitted[0].split("-")
        val min = requirements[0].toInt()
        val max = requirements[1].toInt()
        val c = splitted[1][0]
        val password = splitted[2]

        val count = countOccurrences(password, c)
        if (count in min..max)
            validPasswords++
    }

    return validPasswords
}

fun solve2(): Int {
    var validPasswords = 0

    for (line in input) {
        val splitted = line.split(" ")
        val requirements = splitted[0].split("-")
        val index1 = requirements[0].toInt()
        val index2 = requirements[1].toInt()
        val c = splitted[1][0]
        val password = splitted[2]
        if (isValid(password, c, index1, index2))
            validPasswords++
    }
    return validPasswords
}

private fun countOccurrences(line: String, c: Char): Int {
    var count = 0
    for (n in line) {
        if (n == c)
            count++
    }
    return count
}

private fun isValid(line: String, c: Char, index1: Int, index2: Int): Boolean = (line[index1 - 1] == c).xor(line[index2 - 1] == c)
