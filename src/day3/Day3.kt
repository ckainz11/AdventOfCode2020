package day3

import utils.getResourceAsLines
import java.awt.font.NumericShaper

fun main() {
    println(multiply(solve2(1, 1), solve2(3 ,1), solve2(5, 1), solve2(7, 1), solve2(1,  2)))
}

fun solve1(): Int {
    val inputRaw = getResourceAsLines("/day3/input.txt")
    val input =  List(inputRaw.size){inputRaw[it]}

    var treeCount = 0
    var x = 0

    for(s in input){
        if(s[x] == '#')
            treeCount++
        x += 3
        x %= input[0].length
    }
    return treeCount
}
fun solve2(xIncrement: Int, yIncrement: Int): Int {
    val inputRaw = getResourceAsLines("/day3/input.txt")
    val input =  List(inputRaw.size){inputRaw[it]}

    var treeCount = 0
    var x = 0
    var y = 0
    while(y < input.size){
        if(input[y][x] == '#')
            treeCount++
        y += yIncrement
        x += xIncrement
        x %= input[0].length
    }
    return treeCount
}

private fun multiply(vararg nums: Int): Int{
    var sum = 1
    for (num in nums)
        sum *= num
    return sum
}

