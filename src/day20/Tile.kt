package day20

class Tile(var grid: List<String>, val index: Int) {

    companion object {
        fun fromImage(image: Array<Tile>, sideLength: Int, tileLength: Int): Tile {
            val tilegrid = mutableListOf<String>()

            for(i in 0 until sideLength * (tileLength - 2)){
                var line = ""
                for(j in 0 until sideLength){
                    val index = (i / (tileLength - 2)) * 3 + j
                    var t = image[index]
                    t = Tile(t.trim(), t.index)
                    line += t.grid[i % (tileLength-2)]
                }
                tilegrid.add(line)

            }
            return Tile(tilegrid, 0)
        }
    }

    private val maxCol = (grid.maxBy { it.length })!!.length
    private fun rotate(): Tile {
        var rotated = mutableListOf<String>()
        for (j in 0 until maxCol) {
            rotated.add(getColumn(j).reversed())
        }
        return Tile(rotated, index)
    }
    private fun rotateTimes(i: Int): Tile{
        var rotated = Tile(grid, index)
        for(i in 1..i){
            rotated = rotated.rotate()
        }
        return rotated
    }
    fun flipY(): Tile {
        val tile = mutableListOf<String>()
        for (line in grid.reversed()) {
            tile.add(line)
        }
        return Tile(tile, index)
    }

    fun getAll(): List<Tile> {
        val possibilities = mutableListOf<Tile>()
        for (i in 0..3) {
            val tile = rotateTimes(i)
            possibilities.add(tile)
            possibilities.add(tile.flipY())
        }
        return possibilities
    }

    private fun getColumn(index: Int): String {
        var col = ""
        if (index >= maxCol)
            error("col out of range")
        else {
            for (line in grid) {
                col += line[index]
            }
        }
        return col
    }

    fun isValid(other: Tile, side: Side): Boolean {
        val s = getSide(side)
        return when (side) {
            Side.BOTTOM -> other.getSide(Side.TOP) == s
            Side.LEFT -> other.getSide(Side.RIGHT) == s
            Side.RIGHT -> other.getSide(Side.LEFT) == s
            Side.TOP -> other.getSide(Side.BOTTOM) == s
        }
    }

    private fun getSide(side: Side): String {
        return when (side) {
            Side.BOTTOM -> grid[grid.size - 1]
            Side.LEFT -> getColumn(0)
            Side.RIGHT -> getColumn(maxCol - 1)
            Side.TOP -> grid[0]
        }
    }
    fun trim(): List<String> {
        val trimmed = mutableListOf<String>()
        for(i in 1 until grid.size-1){
            trimmed.add(grid[i].substring(1 until grid[i].length - 1))
        }
        return trimmed

    }
    fun mask(mask: List<Point>): Boolean{
        var found = false
        val maxWidth = mask.maxBy { it.y }!!.y
        val maxHeight = mask.maxBy { it.x }!!.x
        val newTile = Array<String>(grid.size){grid[it]}
        (0..(grid.size - maxHeight)).forEach { x ->
            (0..(grid.size - maxWidth)).forEach { y ->
                val lookingAt = Point(x, y)
                val actualSpots = mask.map { Point(it.x + lookingAt.x, it.y + lookingAt.y) }
                if (actualSpots.all { grid[it.x][it.y] == '#' }) {
                    found = true
                    for(spot in actualSpots){
                        val s = StringBuilder(newTile[spot.x])
                        s.set(spot.y, '0')
                        newTile[spot.x] = s.toString()
                    }
                }
            }
        }
        if(found)
            grid = newTile.asList()
        return found
    }
}

enum class Side {
    LEFT, RIGHT, TOP, BOTTOM
}