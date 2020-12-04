package day4

import utils.getResourceAsLines

fun main() {
    println(solve())
}

val reqFields = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
fun solve(): Int {
    val input = getResourceAsLines("/day4/input.txt")
    var count = 0

    var missingFields = reqFields.toMutableSet()
    for (line in input) {
        if (line == "") {
            if (missingFields.isEmpty())
                count++
            missingFields = reqFields.toMutableSet()
        } else {
            val fields = line.split(" ")
            for (f in fields) {
                if (isValid(f))
                    missingFields.remove(f.split(":")[0])
            }
        }
    }
    return count
}

private val validColors = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

private fun isValid(field: String): Boolean {
    val split = field.split(":")
    val key = split[0]
    val value = split[1]

    return when (key) {
        "byr" -> (value.toInt() in 1920..2002)
        "iyr" -> (value.toInt() in 2010..2020)
        "eyr" -> (value.toInt() in 2020..2030)
        "hgt" -> (checkHeight(value))
        "ecl" -> validColors.contains(value)
        "hcl" -> checkHairColor(value)
        "pid" -> value.length == 9
        else -> false
    }
}

private fun checkHeight(value: String): Boolean {
    val unit = value.substring(value.length - 2, value.length)
    val height = value.substring(0, value.length - 2)
    return when (unit) {
        "cm" -> (height.toInt() in 150..193)
        "in" -> (height.toInt() in 59..76)
        else -> false
    }
}

private fun checkHairColor(value: String): Boolean {
    val firstChar = value[0]
    val color = value.substring(1 until value.length)
    val validChars = arrayOf('a', 'b', 'c', 'd', 'e', 'f', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0')

    if (firstChar == '#') {
        for (c in color) {
            if (!validChars.contains(c))
                return false
        }
    } else return false
    return true
}

