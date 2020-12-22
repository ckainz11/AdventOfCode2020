package day21


import utils.getResourceAsLines
import javax.print.DocFlavor
import kotlin.system.measureTimeMillis

fun main() {
    var res1 = 0
    val time1 = measureTimeMillis {
        res1 = part1()
    }
    println("Solution for part 1: $res1")
    println("In $time1 ms")
    var res2 = ""
    val time2 = measureTimeMillis {
        res2 = part2()
    }
    println("Solution for part 1: $res2")
    println("In $time2 ms")
}

val input = getResourceAsLines("/day21/input.txt")
val allAllergens = mutableMapOf<String, Int>()
val ingredients = mutableMapOf<String, Ingredient>()
val allIngredients = mutableListOf<String>()
val possibleAllergens = mutableMapOf<String, MutableList<String>>()     //ingredient, possible Allergens
fun part1(): Int {


    for (line in input) {
        val r = Regex("\\((.*?)\\)")
        val allergens = r.find(line)!!.value.trim('(').trim(')').split(", ")
            .map { it -> if (it.split(" ").size == 1) it else it.split(" ")[1] }
        val ings = line.split(" (").first().split(" ")
        for (i in ings) {
            val ingredient = ingredients[i]
            if (ingredient == null)
                ingredients[i] = Ingredient(i, allergens.toMutableList())
            else
                ingredient.addAllergens(allergens.toMutableList())
        }
        allIngredients.addAll(ings)
        for (a in allergens) {
            val allergen = allAllergens[a]
            if (allergen == null)
                allAllergens[a] = 1
            else
                allAllergens[a] = allergen + 1
        }
    }

    for (i in ingredients) {
        val ing = i.value
        for (a in allAllergens) {
            val allergenCount = a.value
            if (ing.getAllergenCount(a.key) == allergenCount) {
                val x = possibleAllergens[i.key]
                if (x == null) {
                    possibleAllergens[i.key] = mutableListOf(a.key)
                } else
                    x.add(a.key)
            }

        }
    }
    val noAllergen = mutableSetOf<String>()
    for (i in ingredients) {
        if (!possibleAllergens.containsKey(i.key))
            noAllergen.add(i.key)
    }
    return allIngredients.count { noAllergen.contains(it) }
}

fun part2(): String {
    val map = mutableMapOf<String, String>()
    while (map.size != possibleAllergens.size) {
        for (p in possibleAllergens) {
            if (p.value.size == 1) {
                map[p.value[0]] = p.key
                val x = p.value[0]
                for (p2 in possibleAllergens) {
                    p2.value.remove(x)
                }
            }
        }
    }
    val sorted = map.toSortedMap()
    return sorted.values.joinToString(",")
}


class Ingredient(val name: String, private val allergens: MutableList<String>) {

    fun addAllergens(allergens: List<String>) {
        this.allergens.addAll(allergens)
    }

    fun getAllergenCount(allergen: String): Int {
        return allergens.count { it == allergen }
    }


}