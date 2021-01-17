package day23

fun main() {
    println(solve1())
    //println(solve2())
}

val input = "389125467"
val inputAsIntList = MutableList(input.length) { Character.getNumericValue(input[it]) }


fun solve1(): String {
    val cups = Cups(inputAsIntList)
    repeat(100) {
        cups.doMove(false)
    }
    return cups.getCupsAsString()
}

fun solve2(): Long {
    val max = inputAsIntList.max()!!
    val input2 = mutableListOf<Int>()
    for(c in input){
        input2.add(Character.getNumericValue(c))
    }
    for (i in max + 1..1_000_000) {
        input2.add(i)
    }
    //val cups = CupsLinked(input2)
    //repeat(10_000_000) { cups.doMove() }
    return 0L
}