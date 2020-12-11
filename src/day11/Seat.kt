package day11

class Seat(val row: Int, val col: Int, var occupied: Boolean) {
    val adjacentSeats = mutableListOf<Seat>()


    fun update(part1: Boolean) {
        if (part1) {
            if (!occupied && adjacentSeats.count { seat -> seat.occupied } == 0)
                occupied = true
        } else {
            if (occupied && adjacentSeats.count { seat -> seat.occupied } >= 5)
                occupied = false
        }

    }





}