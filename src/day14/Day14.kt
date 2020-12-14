package day14

import utils.getResourceAsLines
import java.lang.Exception
import kotlin.math.pow
import kotlin.text.StringBuilder

fun main() {


    println(solve2())
}

val input = getResourceAsLines("/day14/input.txt")

fun solve1(): Long {
    val memory = mutableMapOf<Int, Long>()
    var mask = input[0]
    for (line in input) {
        if (line.startsWith("mask")) {
            mask = line.split(" = ")[1]
        } else {
            var splitted = line.split(" = ")
            var index = "(?<=\\[).+?(?=\\])".toRegex().find(splitted[0])?.value?.toInt()!!
            var value = valueMasked(splitted[1].toInt(), mask)
            memory[index] = value
        }


    }
    return memory.map { it.value }.sum()
}

fun solve2(): Long {
    var lineCount = 0
    val memory = mutableMapOf<Long, Long>()
    var mask = input[0]
    for (line in input) {
        println(lineCount)
        if (line.startsWith("mask")) {
            mask = line.split(" = ")[1]
        } else {
            var splitted = line.split(" = ")
            var index = "(?<=\\[).+?(?=\\])".toRegex().find(splitted[0])?.value?.toInt()!!
            var maskedIndices = indexMasked(index, mask)


            var value = splitted[1].toLong()
            for(maskedIndex in maskedIndices)
                memory[maskedIndex] = value
        }
        lineCount++

    }
    return memory.map { it.value }.sum()

}

fun indexMasked(index: Int, mask: String): List<Long> {

    var builder = StringBuilder()
    var binary = index.toString(2).reversed()
    var reversedMask = mask.reversed()
    for ((i, char) in binary.withIndex()) {
        val mask = reversedMask[i]
        if (mask == 'X')
            builder.append(mask)
        else if (mask == '0')
            builder.append(char)
        else
            builder.append('1')
    }
    builder.append(mask.substring(0 until mask.length - binary.length).reversed())

    var floatingCount = 0
    val indicesOfXs = mutableListOf<Int>()
    builder.forEachIndexed { index, c ->
        run {
            if (c == 'X') {
                floatingCount++
                indicesOfXs.add(index)
            }
        }
    }
    val possibleCombinations = (2.0.pow(floatingCount)).toInt()
    val addresses = mutableListOf<Long>()
    for (i in 0 until possibleCombinations) {
        val binaryVal = i.toString(2).reversed()
        var builderCopy = StringBuilder(builder.toString())

        for (j in 0 until floatingCount) {
            try {
                builderCopy.set(indicesOfXs[j], binaryVal[j])
            } catch (e: Exception) {
                builderCopy.set(indicesOfXs[j], '0')
            }
        }
        addresses.add(builderCopy.reversed().toString().toLong(2))
    }


    return addresses
}

fun valueMasked(value: Int, mask: String): Long {
    var builder = StringBuilder()
    var binary = value.toString(2).reversed()
    var reversedMask = mask.reversed()
    for ((i, char) in binary.withIndex()) {
        val mask = reversedMask[i]
        if (mask == 'X')
            builder.append(char)
        else
            builder.append(mask)
    }
    builder.append(mask.substring(0 until mask.length - binary.length).replace('X', '0').reversed())


    return builder.reversed().toString().toLong(2)
}
