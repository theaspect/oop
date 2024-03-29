package me.blzr.oop.d.polymorphism

fun main() {
    // Even more generic
    val list = listOf(2, 1, 0).sortedWith(object : Comparator<Int> {
        override fun compare(x: Int, y: Int): Int {
            return x.compareTo(y)
        }
    })
    println(list)

    // listOf(2, 1, 0).sortedWith(Comparator<Int> { x: Int, y: Int -> x.compareTo(y) })
    // listOf(2, 1, 0).sortedWith({ x: Int, y: Int -> x.compareTo(y) })
    // listOf(2, 1, 0).sortedWith { x: Int, y: Int -> x.compareTo(y) }
    // listOf(2, 1, 0).sortedWith { x, y -> x.compareTo(y) }
}
