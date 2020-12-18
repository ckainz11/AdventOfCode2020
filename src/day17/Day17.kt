package day17

import utils.getResourceAsLines
import kotlin.system.measureTimeMillis

val input = getResourceAsLines("/day17/input.txt")

fun main() {
    var res1 = 0
    val time1 = measureTimeMillis {
        res1 = solve1()
    }
    println("Solution for part 1: $res1")
    println("In $time1 ms")
    var res2 = 0
    val time2 = measureTimeMillis {
        res2 = solve2()
    }
    println("Solution for part 1: $res2")
    println("In $time2 ms")
}


var activePoints = mutableSetOf<Point3D>()

fun solve1(): Int {
    var maxX = input[0].length/ 2 +1
    var maxY = input[0].length/ 2 +1
    var maxZ = 0
    var maxW = 0
    for ((i, line) in input.withIndex()) {
        val y = i -1
        for ((j, point) in line.withIndex()) {
            val x = j -1
            if(point == '#')
                activePoints.add(Point3D(x, y, 0, 0))
        }
    }
    for (i in 0 until 6) {
        maxX++
        maxY++
        maxZ++
        nextGeneration(maxX, maxY, maxZ, maxW)
    }

    return activePoints.size
}
fun solve2(): Int {
    activePoints = mutableSetOf()
    var maxX =  input[0].length/ 2 +1
    var maxY = input[0].length/ 2 +1
    var maxZ = 0
    var maxW = 0
    for ((i, line) in input.withIndex()) {
        val y = i -1
        for ((j, point) in line.withIndex()) {
            val x = j -1
            if(point == '#')
                activePoints.add(Point3D(x, y, 0, 0))
        }
    }
    //printPoints(maxX, maxY, maxZ, maxW)
    repeat (6) {
        maxX++
        maxY++
        maxZ++
        maxW++
        nextGeneration(maxX, maxY, maxZ, maxW)
        //printPoints(maxX, maxY, maxZ, maxW)
    }

    return activePoints.size
}
private fun nextGeneration(maxX: Int, maxY: Int, maxZ: Int, maxW: Int) {
    val future = mutableSetOf<Point3D>()
    for(w in -maxW..maxW){
        for(z in -maxZ..maxZ){
            for(y in -maxY..maxY){
                for(x in -maxX..maxX){
                    var pointState = getState(x,y,z,w)
                    val point = Point3D(x,y,z,w)
                    if(pointState == 1){
                        val activeNeighbours = getNeighbours(point)
                        if(activeNeighbours in 2..3)
                            future.add(point)
                    }
                    else{
                        val activeNeighbours = getNeighbours(point)
                        if(activeNeighbours == 3)
                            future.add(point)
                    }
                }
            }
        }
    }
    activePoints = future
}

private fun getNeighbours(point: Point3D): Int {
    var count = 0
    for(w in -1..1){
        for (z in -1..1) {
            for (y in -1..1) {
                for (x in -1..1) {
                    if(x == 0 && y == 0 && z == 0 && w == 0)
                        continue
                    if(getState(point.x + x, point.y + y, point.z + z, point.w + w) == 1)
                        count++
                }
            }
        }
    }
    return count
}

private fun getState(x: Int, y: Int, z: Int, w: Int): Int {
    for (point in activePoints) {
        if (point.x == x && point.y == y && point.z == z && point.w == w)
            return 1
    }
    return 0
}

private fun printPoints(maxX: Int, maxY: Int, maxZ: Int, maxW: Int) {
    for(w in -maxW..maxW){
        println("dimension $w")
        for(i in -maxZ..maxZ){
            println("slice $i")
            for(j in -maxY..maxY){
                for(k in -maxX..maxX){
                    print(if(getState(k,j,i,w) == 1) '#' else '.' )
                }
                println()
            }
        }
    }

}



data class Point3D(val x: Int, val y: Int, val z: Int, val w:Int) {

}