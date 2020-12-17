package day16

import utils.getResourceAsLines
import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis { println(solve2())  }
    println("time: $time ms")

}

val input = getResourceAsLines("/day16/input.txt")
fun solve1(): Int {
    val rules = mutableListOf<Rule>()

    var errorRate = 0

    var currentIndex = 0
    for (i in input.indices) {
        currentIndex = i
        val line = input[i].split(":")
        if (line[0] == "")
            break
        val lineRules = line[1].trim().split(" or ")
        val rule1 = lineRules[0].split("-").map { it.toInt() }
        val rule2 = lineRules[1].split("-").map { it.toInt() }
        rules.add(Rule(rule1[0], rule1[1], rule2[0], rule2[1]))

    }
    currentIndex += 5
    while (currentIndex in input.indices) {
        val nums = input[currentIndex].split(",")
        for (n in nums) {
            var count = 1
            val number = n.toInt()
            for (rule in rules) {
                val inFirst = number in rule.min1..rule.max1
                val inSecond = number in rule.min2..rule.max2
                if ((inFirst || inSecond)) {
                    break
                }
                if (count == rules.size)
                    errorRate += number
                count++
            }
        }

        currentIndex++
    }

    return errorRate
}



fun solve2(): Long {
    val rules = mutableListOf<Rule>()
    val validTickets = mutableListOf<List<Int>>()
    val ruleNames = mutableMapOf<Int, String>()
    var currentIndex = 0
    for (i in input.indices) {
        currentIndex = i
        val line = input[i].split(":")
        if (line[0] == "")
            break
        ruleNames[i] = line[0]
        val lineRules = line[1].trim().split(" or ")
        val rule1 = lineRules[0].split("-").map { it.toInt() }
        val rule2 = lineRules[1].split("-").map { it.toInt() }
        rules.add(Rule(rule1[0], rule1[1], rule2[0], rule2[1]))

    }
    currentIndex += 2
    val myTicket = input[currentIndex].split(",").map { it.toInt() }
    currentIndex +=3
    val possibleRules = mutableMapOf<Int, MutableList<Int>>()
    while(currentIndex in input.indices){
        val ticket = input[currentIndex].split(",").map { it.toInt() }
        var notValid = false
        for(field in ticket){
            var count = 0
            for(rule in rules){
                if (field in rule.min1..rule.max1 || field in rule.min2..rule.max2)
                    count++
            }
            if(count <= 1)
                notValid = true
        }
        if(!notValid)
            validTickets.add(ticket)
        currentIndex++
    }
    for(fieldIndex in myTicket.indices){
        for((ruleIndex, rule) in rules.withIndex()){
            var count = 0
            for(ticket in validTickets){
                val fieldVal = ticket[fieldIndex]

                if(fieldVal !in rule.min1..rule.max1 && fieldVal !in rule.min2..rule.max2)
                    break
                else
                    count++
            }
            if(count == validTickets.size){
                var previous = possibleRules[fieldIndex]
                if(previous == null)
                    previous = mutableListOf(ruleIndex)
                else
                    previous.add(ruleIndex)
                possibleRules[fieldIndex] = previous
            }

        }
    }
    val fieldRules = mutableMapOf<Int, Int>()
    while(fieldRules.size != rules.size){
        for(possibleRule in possibleRules){
            if(possibleRule.value.size == 1){
                val rule = possibleRule.value[0]
                fieldRules[possibleRule.key] = rule
                for(entry in possibleRules){
                    entry.value.remove(rule)
                }
            }

        }
    }
    var sum = 1L
    for(entry in fieldRules){
        val ruleName = ruleNames[entry.value]
        if(ruleName!!.startsWith("departure"))
            sum *= myTicket[entry.key].toLong()
    }

    return sum
}

data class Rule(val min1: Int, val max1: Int, val min2: Int, val max2: Int)
data class Ticket(val passedRules: Map<Int, List<Int>>)