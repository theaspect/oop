package me.blzr.oop.d.polymorphism

class AnyList(val size: Int) {
    private val data: Array<Any?> = Array(size) { null }

    fun get(i: Int): Any? = data[i]

    fun set(where: Int, i: Any?) {
        data[where] = i
    }

    fun copy(): AnyList {
        val new = AnyList(size)
        for (i: Int in data.indices) {
            new.set(i, data[i])
        }
        return new
    }

    override fun toString(): String = "AnyList(data=${data.contentToString()})"
}

open class IntList(val size: Int) {
    protected val data: Array<Int?> = Array(size) { null }

    fun get(i: Int): Int? = data[i]

    fun set(where: Int, i: Int?) {
        data[where] = i
    }

    fun copy(): IntList {
        val new = IntList(size)
        for (i: Int in data.indices) {
            new.set(i, data[i])
        }
        return new
    }

    override fun toString(): String = "IntList(data=${data.contentToString()})"
}

open class StringList(val size: Int) {
    protected val data: Array<String?> = Array(size) { null }

    operator fun get(i: Int): String? {
        return data[i]
    }

    fun set(where: Int, i: String?) {
        data[where] = i
    }

    fun copy(): StringList {
        val new = StringList(size)
        for (i in data.indices) {
            new.set(i, data[i])
        }
        return new
    }

    override fun toString(): String = "StringList(data=${data.contentToString()})"
}

fun main() {
    val intList = IntList(4)
    intList.set(0, 0)
    intList.set(1, 1)
    intList.set(2, 2)
    println(intList.toString())

    val intListCopy = intList.copy()
    println(intListCopy.toString())

    val stringList = StringList(4)
    stringList.set(0, "a")
    stringList.set(1, "b")
    stringList.set(2, "c")
    println(stringList.toString())

    val stringListCopy = stringList.copy()
    println(stringListCopy.toString())

    val anyList = AnyList(3)
    anyList.set(0, 0)
    anyList.set(1, 1)
    anyList.set(2, "a")
    println(anyList.toString())

    println(anyList.get(0) as Int)
    println(anyList.get(2) as Int) // class java.lang.String cannot be cast to class java.lang.Integer
}
