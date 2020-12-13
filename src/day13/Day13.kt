package day13

import utils.getResourceAsLines
import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis { println(solve2()) }
    println(time)

}

val input = getResourceAsLines("/day13/input.txt")
val time = input[0].toInt()

fun solve1(): Int {
    var closest = 0
    var departure = 0
    var closestId = 0

    for (bus in input[1].split(",")) {
        bus.trim()
        if (bus != "x") {
            val id = bus.toInt()
            var a = time % id

            if(a > closest){
                closest = a
                departure = ((time / id) + 1) * id
                closestId = id
            }
        }
    }
    return (departure - time) * closestId
}

fun solve2(): Long {

    val relativeDepartures = mutableMapOf<Long, Long>() //time difference to t, id

    var offset = 0L
    for(bus in input[1].split(",")){
        if(bus != "x")
            relativeDepartures[offset] = bus.trim().toLong()
        offset++
    }
    var t = 0L
    while(true){
        var timeToSkip = 1L
        var tFound = true
        for(entry in relativeDepartures){
            if((t + entry.key) % entry.value == 0L){
                timeToSkip *= entry.value
            }
            else
                tFound = false
        }
        if(tFound){
            return t
        }
        println("$t $timeToSkip")
        t += timeToSkip
    }
}