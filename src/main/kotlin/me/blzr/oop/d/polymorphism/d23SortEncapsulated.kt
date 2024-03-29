package me.blzr.oop.d.polymorphism

interface Sortable {
    fun sort(): List<Any?>
}

class IntListSortableGeneric(size: Int) : IntListSortable(size), Sortable {
    override fun sort() = sortNumerical()
}

class StringListSortableGeneric(size: Int) : StringListSortable(size), Sortable {
    override fun sort() = sortAlphabetical()
}

fun sort3(s: Sortable) = s.sort()

fun main() {
    val a = IntListSortableGeneric(3)
    a.set(0, 2)
    a.set(1, 1)
    a.set(2, 0)

    val b = StringListSortableGeneric(3)
    b.set(0, "bb")
    b.set(1, "ccc")
    b.set(2, "a")

    println(sort3(a)) // [0, 1, 2]
    println(sort3(b)) // [a, bb, ccc]
}
