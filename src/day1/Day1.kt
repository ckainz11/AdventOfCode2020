package day1

import utils.getResourceAsInts


fun main(){
    println(solve2())
}

fun solve1(): Int {
    val input = getResourceAsInts("/day1/input.txt")
    val map = mutableMapOf<Int, Int>()
    for(n in input){
        val x = map[n];
        if(x != null)
            return x * n
        map[2020 - n] = n
    }
    return 0
}

fun solve2(): Int {
    val input = getResourceAsInts("/day1/input.txt")
    val map = mutableMapOf<Int, Pair<Int, Int>>()

    for(n in input){
        val x = map[n]
        if(x != null) {
           return x.first * x.second * n
        }
        for(k in input){
            if(k == n)
                continue
            if(2020 - n - k > 0)
                map[2020-n-k] = Pair(n, k)
        }
    }
    return 0
}

