package day6

import utils.getResourceAsLines



fun main() {
    println(solve2())
}

val input = getResourceAsLines("/day6/input.txt")

fun solve1(): Int {
    var sum = 0
    var usedChars = mutableSetOf<Char>()
    for(s in input){
        if(s == "") {
            usedChars = mutableSetOf()
            continue
        }

        for(c in s){
            if(!usedChars.contains(c)){
                sum++
                usedChars.add(c)
            }
        }
    }
    return sum
}
fun solve2(): Int {
    var sum = 0
    var peopleCount = 0
    var usedChars = mutableMapOf<Char, Int>()
    for(s in input){
        if(s == "") {
            usedChars.forEach{ (_, v) -> sum += v / peopleCount}
            usedChars = mutableMapOf()
            peopleCount = 0
            continue
        }
        peopleCount++

        for(c in s){
            if(usedChars.containsKey(c))
                usedChars.set(c, usedChars.getValue(c) + 1)
            else
                usedChars.put(c, 1)
        }
    }
    return sum
}