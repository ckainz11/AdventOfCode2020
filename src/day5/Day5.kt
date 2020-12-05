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
fun getSeatID(line: String): Int{
    val rowID = line.substring(0..6)
    val columnID = line.substring(7..9)
    return getRow(rowID) * 8 + getColumn(columnID)
}

fun getRow(rowID: String): Int{

    var lower = 0
    var upper = 127

    for(c in rowID){
        val range = (upper - lower + 1) / 2
        if(c == 'B'){
            val newLower = range + lower
            lower = newLower
        }
        else {
            val newUpper = upper - range
            upper = newUpper
        }
    }
    return if (rowID.last() == 'F') lower else upper
}
fun getColumn(columnID: String): Int{

    var lower = 0
    var upper = 7

    for(c in columnID){
        val range = (upper - lower + 1) / 2
        if(c == 'R'){
            val newLower = range + lower
            lower = newLower
        }
        else {
            val newUpper = upper - range
            upper = newUpper
        }
    }
    return if (columnID.last() == 'L') lower else upper
}