package day23

class CupsLinked(input: String, numOfCups: Int = input.length) {


    fun doMove() {


    }

    fun getLabelsMultiplied(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private class Cup(val label: Int) {
        lateinit var next: Cup
        fun nextAsList(n: Int): List<Cup> {
            val list = mutableListOf<Cup>()
            var next = this
            for(i in 1..n){
                next = next.next
                list.add(next)
            }
            return list.toList()
        }

    }
}
