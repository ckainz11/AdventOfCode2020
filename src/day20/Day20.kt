package day20

import utils.getResourceAsLines
import java.lang.IndexOutOfBoundsException
import javax.print.DocFlavor
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

fun main() {
    var res1 = 0L
    val time1 = measureTimeMillis {
        res1 = part1()
    }
    println("Solution for part 1: $res1")
    println("In $time1 ms")
    println(part2())

}

var firstTile = mutableListOf<String>()
val input = getResourceAsLines("/day20/input.txt")
var sideLength = 0
var possibilites = mutableMapOf<Int, List<Tile>>()
var image = emptyArray<Tile>()
var defaultTiles = mutableMapOf<Int, Tile>()
fun part1(): Long {
    val allTiles = mutableSetOf<Tile>()
    var index = 0
    var grid = mutableListOf<String>()
    for (line in input) {
        if (line == "") {
            firstTile = grid
            val tile = Tile(grid, index)
            allTiles.add(tile)
            possibilites[index] = tile.getAll()
            defaultTiles[index] = tile
            index = 0
            grid = mutableListOf()
            continue
        }
        val splitted = line.split(" ")
        if (splitted.size == 1) {
            grid.add(line)
        } else {
            index = splitted[1].trim(':').toInt()
        }
    }
    sideLength = sqrt(allTiles.size.toDouble()).toInt()
    val camera = Array(sideLength.toDouble().pow(2).toInt()) { Tile(firstTile, -1) }
    image = solve(allTiles, camera, 0)
    return getCornersMultiplied(image)
}

fun part2(): Int {
    val seaMonsterOffsets = listOf(
        Point(0, 18), Point(1, 0), Point(1, 5), Point(1, 6), Point(1, 11), Point(1, 12),
        Point(1, 17), Point(1, 18), Point(1, 19), Point(2, 1), Point(2, 4), Point(2, 7),
        Point(2, 10), Point(2, 13), Point(2, 16)
    )

    return Tile.fromImage(image, sideLength, image[0].grid.size)
        .getAll()
        .first { it.mask(seaMonsterOffsets) }
        .grid
        .sumBy { row ->
            row.count { char -> char == '#' }
        }
}


private fun isDone(camera: Array<Tile>): Boolean {
    var count = 0
    for (entry in camera) {
        if (entry.index != -1)
            count++
    }
    return count == camera.size
}

private fun getCornersMultiplied(camera: Array<Tile>): Long {
    return camera[0].index.toLong() *
            camera[0 + sideLength - 1].index.toLong() *
            camera[camera.size - sideLength].index.toLong() *
            camera[camera.size - 1].index.toLong()
}

fun solve(tiles: Set<Tile>, camera: Array<Tile>, index: Int): Array<Tile> {
    for (t in tiles) {
        for (p in possibilites[t.index]!!) {
            val valid = when {
                index == 0 -> true
                index < sideLength -> (p.isValid(camera[index - 1], Side.LEFT))
                index % sideLength == 0 -> (p.isValid(camera[index - sideLength], Side.TOP))
                else -> p.isValid(camera[index - sideLength], Side.TOP) && p.isValid(camera[index - 1], Side.LEFT)
            }
            if (valid) {
                val copy = camera.copyOf()
                copy[index] = p
                val newTiles = tiles.toMutableSet()
                newTiles.remove(t)
                val newCamera = solve(newTiles, copy, index + 1)
                if (isDone(newCamera))
                    return newCamera
            }
        }
    }
    return camera
}
data class Point(val x: Int, val y: Int)