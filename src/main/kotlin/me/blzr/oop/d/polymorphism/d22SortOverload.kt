package me.blzr.oop.d.polymorphism

fun sort2(v: IntListSortable): List<Any?> = v.sortNumerical()
fun sort2(v: StringListSortable): List<Any?> = v.sortAlphabetical()

fun main() {
    val a = IntListSortable(3)
    a.set(0, 2)
    a.set(1, 1)
    a.set(2, 0)

    val b = StringListSortable(3)
    b.set(0, "bb")
    b.set(1, "ccc")
    b.set(2, "a")

    val c = AnyList(3)
    c.set(0, 1)
    c.set(1, "ccc")
    c.set(2, a)

    println(sort2(a))
    println(sort2(b))
    // None of the following functions can be called with the arguments supplied
    // println(sort2(c))
}
