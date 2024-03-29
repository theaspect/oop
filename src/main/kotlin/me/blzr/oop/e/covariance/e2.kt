package me.blzr.oop.e.covariance

fun main() {
    val a: List<Food> = listOf<Grass>(Grass())
    val b: List<Grass> = listOf()

    // Type mismatch: inferred type is Food but Grass was expected
    // Type mismatch: inferred type is MutableList<Grass> but MutableList<Food> was expected
    // val c: MutableList<Food> = mutableListOf<Grass>(Grass())
    // c.add(Food())

    val g = ArrayList<Grass>()
    g.add(Grass())
    val f: List<Food> = g

    (f as ArrayList<Food>).add(Food()) // Java type erasure

    // class Food cannot be cast to Grass
    for (i: Grass in g) {
        println(i)
    }

    // Предупреждает о потенциальной проблеме
    val x: ArrayList<Food> = g as ArrayList<Food>
}
