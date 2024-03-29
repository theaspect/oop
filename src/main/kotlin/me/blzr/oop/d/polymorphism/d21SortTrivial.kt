package me.blzr.oop.d.polymorphism

open class IntListSortable(size: Int) : IntList(size) {
    fun sortNumerical(): List<Int?> {
        val copy = data.copyOf()
        copy.sort()
        return copy.toList()
    }
}

open class StringListSortable(size: Int) : StringList(size) {
    fun sortAlphabetical(): List<String?> {
        val copy = data.copyOf()
        copy.sort()
        return copy.toList()
    }
}

fun sort1(v: Any): List<Any?> =
    when (v) {
        is IntListSortable -> v.sortNumerical()
        is StringListSortable -> v.sortAlphabetical()
        else -> throw NotImplementedError()
    }

fun main() {
    val a = IntListSortable(3)
    a.set(0, 2)
    a.set(1, 1)
    a.set(2, 0)

    val b = StringListSortable(3)
    b.set(0, "bb")
    b.set(1, "ccc")
    b.set(2, "a")

    println(sort1(a))
    println(sort1(b))
}
