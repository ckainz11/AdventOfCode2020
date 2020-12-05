package day5

import utils.getResourceAsLines

fun main(){
    println(solve1())
    println(solve2())
}

fun solve1(): Int{

    val input = getResourceAsLines("/day5/input.txt")

    var highestSeatID = 0

    for(line in input) {

        val seatID = getSeatID(line)

        if(seatID > highestSeatID)
            highestSeatID = seatID

    }
    return highestSeatID

}
fun solve2(): Int {
    val input = getResourceAsLines("/day5/input.txt")
    var allSeats = List(input.size){ getSeatID(input[it])}
    allSeats = allSeats.sorted()



    var previous = 0

    for(seat in allSeats) {

        if(seat - previous == 2)
            return seat - 1
        previous = seat
    }
    return 0

}
fun getSeatID(line: String): Int = line.replace('F', '0').replace('B', '1').replace('L', '0').replace('R', '1').toInt(2)


