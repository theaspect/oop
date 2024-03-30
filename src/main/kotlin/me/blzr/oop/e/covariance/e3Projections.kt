package me.blzr.oop.e.covariance

@Suppress("RemoveExplicitTypeArguments", "UNUSED_VARIABLE")
fun main() {
    val a: List<Food> = listOf<Grass>(Grass())
    val b: List<Grass> = listOf()

    // Type mismatch: inferred type is Food but Grass was expected
    // Type mismatch: inferred type is MutableList<Grass> but MutableList<Food> was expected
    // val c: MutableList<Food> = mutableListOf<Grass>(Grass())
    // c.add(Food())

    val g: MutableList<Grass> = mutableListOf<Grass>()
    g.add(Grass())
    val f: List<Food> = g

    // Type mismatch: inferred type is MutableList<Grass> but MutableList<Food> was expected
    // val h: MutableList<Food> = g

    // (f as MutableList<Food>).add(Food()) // Java type erasure

    // class Food cannot be cast to Grass
    for (i: Grass in g) {
        println(i)
    }

    // Предупреждает о потенциальной проблеме
    // Unchecked cast: MutableList<Grass> to MutableList<Food>
    val x: MutableList<Food> = g as MutableList<Food>
}
