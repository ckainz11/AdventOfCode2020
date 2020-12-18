package day18


import utils.getResourceAsLines
import java.math.BigInteger
import kotlin.system.measureTimeMillis

fun main() {
    var res1 = 0.toBigInteger()
    val time1 = measureTimeMillis {
        res1 = solve(1)
    }
    println("Solution for part 1: $res1")
    println("In $time1 ms")
    var res2 = 0.toBigInteger()
    val time2 = measureTimeMillis {
        res2 = solve(2)
    }
    println("Solution for part 2: $res2")
    println("In $time2 ms")
}

val input = getResourceAsLines("/day18/input.txt")

fun solve(part: Int): BigInteger {
    var sum = 0.toBigInteger()
    for ((i, line) in input.withIndex()) {
        if(part == 1)
            sum += evaluate(line)
        else
            sum += evaluate2(line)
    }

    return sum
}




fun evaluate(line: String): BigInteger {
    var sum = 0.toBigInteger()
    var operation = '+'
    var brackets = false
    var subForm = StringBuilder()
    var openBracketsCount = 0
    var closedBracketsCount = 0
    for (x in line) {
        var num = x.toString().toBigIntegerOrNull()
        if (x == '(' || brackets) {
            brackets = true
            if (x == '(') {
                subForm.append(x)
                openBracketsCount++
            } else if (x == ')') {
                subForm.append(x)
                closedBracketsCount++
                if(openBracketsCount == closedBracketsCount)
                    brackets = false
            } else
                subForm.append(x)
            if (!brackets) {
                num = evaluate(subForm.removePrefix("(").removeSuffix(")").toString().trim())
                subForm = StringBuilder()
                openBracketsCount = 0
                closedBracketsCount = 0
            } else
                continue
        }
        if(x == ' ')
            continue
        if (num == null) {
            operation = x
        } else {
            when (operation) {
                '+' -> sum += num
                '*' -> sum *= num
            }
        }
    }
    return sum
}
fun evaluate2(line: String): BigInteger {
    var result = 1.toBigInteger()
    var operation = '+'
    var lineSums = mutableListOf<BigInteger>()
    var sum = 0.toBigInteger()
    var brackets = false
    var subForm = StringBuilder()
    var openBracketsCount = 0
    var closedBracketsCount = 0
    for (x in line) {
        var num = x.toString().toBigIntegerOrNull()
        if (x == '(' || brackets) {
            brackets = true
            if (x == '(') {
                subForm.append(x)
                openBracketsCount++
            } else if (x == ')') {
                subForm.append(x)
                closedBracketsCount++
                if (openBracketsCount == closedBracketsCount)
                    brackets = false
            } else
                subForm.append(x)
            if (!brackets) {
                num = evaluate2(subForm.removePrefix("(").removeSuffix(")").toString().trim())
                subForm = StringBuilder()
                openBracketsCount = 0
                closedBracketsCount = 0
            } else
                continue
        }
        if (x == ' ')
            continue
        if (num == null) {
            operation = x
        } else {
            if (operation == '+') {
                sum += num
            } else {
                lineSums.add(sum)
                sum = num
            }
        }
    }
    lineSums.add(sum)
    for (lineSum in lineSums) {
        result *= lineSum
    }
    return result
}

