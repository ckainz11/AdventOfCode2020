package day12

import kotlin.math.abs

class Ship {
    private var facing = 1
    private val waypoint = Waypoint()

    private var x = 0
    private var y = 0

    fun takeAction(line: String) {
        val action = line[0]
        val value = line.substring(1).toInt()
        if (action == 'L' || action == 'R')
            turn(action, value)
        else
            move(action, value)
    }

    fun takeActionPar2(line: String){
        val action = line[0]
        val value = line.substring(1).toInt()
        if(action == 'F')
            moveToWaypoint(value)
        else if(action == 'R' || action == 'L')
            waypoint.turn(action, value)
        else
            waypoint.move(action, value)
    }

    private fun moveToWaypoint(value: Int) {

        for(i in 1..value){
            x += waypoint.x
            y += waypoint.y
        }
    }

    private fun move(action: Char, value: Int) {
        when (action) {
            'E' -> x += value
            'S' -> y -= value
            'W' -> x -= value
            'N' -> y += value
            else -> move(getFacingChar(), value)
        }


    }

    private fun getFacingChar(): Char {
        return when (facing) {
            1 -> 'E'
            2 -> 'S'
            3 -> 'W'
            else -> 'N'
        }
    }

    private fun turn(direction: Char, degrees: Int) {
        var amount = degrees / 90
        if (direction == 'R') {
            facing += amount
            if(facing > 4 )
                facing %= 4
        }
        else{
            facing -= amount
            if(facing <= 0)
                facing += 4
        }
    }

    fun getManhattanDistance(): Int {
        return abs(x) + abs(y)
    }



}
class Waypoint {
    var x = 10
    var y = 1

    fun move(action: Char, value: Int) {
        when (action) {
            'E' -> x += value
            'S' -> y -= value
            'W' -> x -= value
            'N' -> y += value
        }

    }
    fun turn(direction: Char, degrees: Int){
        val amount = degrees / 90

        for(i in 1..amount){
            if(direction == 'R'){
                val previousX = x
                x = y
                y = previousX * -1
            }
            else{
                val previousY = y
                y = x
                x = previousY * -1
            }
        }
    }



}
