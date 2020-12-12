package day12
import utils.getResourceAsLines

fun main() {
    println(solve2())
}
val input = getResourceAsLines("/day12/input.txt")
fun solve1(): Int {
    val ship = Ship()
    for (line in input){
        ship.takeAction(line)
    }
    return ship.getManhattanDistance()
}
fun solve2(): Int {
    val ship = Ship()
    for (line in input){
        ship.takeActionPar2(line)
    }
    return ship.getManhattanDistance()
}