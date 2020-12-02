package day1

import utils.getResourceAsInts
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

val input = getResourceAsInts("/day1/input.txt")

fun main(){
    val ms = measureTimeMillis {
        solve1()
        solve2()
    }
    println(ms)
}

fun solve1(): Int {
    val map = mutableMapOf<Int, Int>()
    for(n in input){
        val x = map[n]
        if(x != null)
            return x * n
        map[2020 - n] = n
    }
    return 0
}

fun solve2(): Int {
    val map = mutableMapOf<Int, Pair<Int, Int>>()
    for(n in input){
        val x = map[n]
        if(x != null) {
           return x.first * x.second * n
        }
        for(k in input){
            if(k == n)
                continue
            map[2020-n-k] = Pair(n, k)
        }
    }
    return 0
}

