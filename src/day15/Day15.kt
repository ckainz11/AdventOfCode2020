package day15

import utils.getResourceAsString

fun main() {
    println(solve1())
}
val input = getResourceAsString("/day15/testinput.txt").split(",")
fun solve1(): Int {
    val map = mutableMapOf<Int, Pair<Int, Int>>()
    var lastNumber = 0
    for(i in 1..30000000){
        if(i <= input.size){
            val number = input[i-1].toInt()
            map[number] = Pair(i, i)
            lastNumber = number
        }
        else {
            val lastEntry = map[lastNumber]!!
            val number = lastEntry.first - lastEntry.second

            val entry = map[number]
            if(entry == null){
                map[number] = Pair(i,i)
            }
            else {
                val first = entry.first
                map[number] = Pair(i, first)
            }
            lastNumber = number
        }
    }



    return lastNumber

}