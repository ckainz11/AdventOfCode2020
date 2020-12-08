package day8

import utils.getResourceAsLines

val input = getResourceAsLines("/day8/input.txt")

fun main(){
    println(solve2())
}

fun solve1(): Int {
    var acc = 0
    val executedLines = emptySet<Int>().toMutableSet()

    var toExecute = 0

    while (!executedLines.contains(toExecute)){
        val lineSplitted = input[toExecute].split(" ")
        val operation = lineSplitted[0]
        val value = lineSplitted[1].toInt()
        executedLines.add(toExecute)
        when(operation){
            "acc" -> {acc += value; toExecute++}
            "jmp" -> toExecute += value
            "nop" -> toExecute++
        }
    }
    return acc
}

fun solve2(): Int {

    for(i in 0..input.size){
        val testInput = input.toMutableList()

        val lineSplitted = testInput[i].split(" ")
        val operation = lineSplitted[0]
        val value = lineSplitted[1].toInt()

        if(operation == "jmp")
            testInput[i] = "nop " + value
        else if(operation == "nop")
            testInput[i] = "jmp " + value

        if(!loops(testInput))
            return execute(testInput)

    }
    return 0
}
fun loops(program: List<String>): Boolean {
    val executedLines = emptySet<Int>().toMutableSet()
    var toExecute = 0

    while (toExecute < program.size){
        if(executedLines.contains(toExecute))
            return true
        val lineSplitted = program[toExecute].split(" ")
        val operation = lineSplitted[0]
        val value = lineSplitted[1].toInt()
        executedLines.add(toExecute)
        when(operation){
            "acc" -> toExecute++
            "jmp" -> toExecute += value
            "nop" -> toExecute++
        }
    }
    return false
}
fun execute(program: List<String>): Int {

    var toExecute = 0
    var acc = 0

    while (toExecute < program.size){
        val lineSplitted = program[toExecute].split(" ")
        val operation = lineSplitted[0]
        val value = lineSplitted[1].toInt()
        when(operation){
            "acc" -> {acc += value; toExecute++}
            "jmp" -> toExecute += value
            "nop" -> toExecute++
        }

    }
    return acc
}