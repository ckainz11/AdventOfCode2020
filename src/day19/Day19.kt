package Day19


import day11.maxLength
import utils.getResourceAsLines
import kotlin.system.measureTimeMillis

fun main() {
    var res1 = 0
    val time1 = measureTimeMillis {
        res1 = solve(106, 65, false)
    }
    println("Solution for part 1: $res1")
    println("In $time1 ms")
    var res2 = 0
    val time2 = measureTimeMillis {
        res2 = solve(106, 65, true)
    }
    println("Solution for part 1: $res2")
    println("In $time2 ms")
}

val input = getResourceAsLines("/day19/input.txt")
val input2 = getResourceAsLines("/day19/input.txt")
val maxlength = input2.indexOf(input2.maxBy { it.length })
var rules2 = mutableMapOf<Int, String>()


fun solve(aIndex: Int, bIndex: Int, part2: Boolean): Int {
    rules2 = mutableMapOf<Int, String>()
    var startIndex = 0
    for ((i, line) in input2.withIndex()) {
        if (line == "") {
            startIndex = i + 1
            break
        }
        val index = line.split(":")[0].toInt()
        val rule = line.split(":")[1]
        val ruleRegex = createRegex(index, rule, aIndex, bIndex, part2)
        rules2[index] = ruleRegex
    }
    var regexS = createUltimateRegex(0)
    var sum = 0
    while (startIndex in input2.indices) {
        val line = input2[startIndex]
        var regex = Regex("")
        if (part2) {
            for (i in 1..maxlength - 1) {
                val s = regexS
                regex = Regex(s.replace("x", "$i"))
                if (line.matches(regex)) {
                    sum++
                    break
                }
            }
        } else {
            regex = Regex(regexS)
            if (line.matches(regex))
                sum++
        }
        startIndex++
    }


    return sum
}

fun createUltimateRegex(index: Int): String {
    var base = rules2[index]!!
    var done = false
    while (!done) {
        var newbase = ""
        var letterCount = 0
        val split = base.split(" ")
        for (x in split) {
            val rule = x.toIntOrNull()
            if (rule == null) {
                newbase += x
                letterCount++
            } else {
                newbase += rules2[rule]
            }
        }
        if (letterCount == split.size)
            done = true
        base = newbase
    }
    return base
}

fun createRegex(ruleIndex: Int, rule: String, aIndex: Int, bIndex: Int, part2: Boolean): String {
    var regexString = "("
    if (part2 && ruleIndex == 11) {
        return "( 42 {x} 31 {x})"
    }

    var split = rule.trim().split("|")
    for ((index, options) in split.withIndex()) {
        for (r in options.trim().split(" ")) {
            var next = r.toIntOrNull()
            if (next == null) {
                regexString += r.trim('"')
            } else if (next == aIndex)
                regexString += "a"
            else if (next == bIndex)
                regexString += "b"
            else
                regexString += " $next "
        }
        if (index >= 0 && index < split.size - 1)
            regexString += "|"
    }
    regexString += ")"
    if (part2 && ruleIndex == 8)
        regexString += "*"
    return regexString
}


