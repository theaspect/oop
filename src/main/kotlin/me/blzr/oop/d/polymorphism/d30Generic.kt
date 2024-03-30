package me.blzr.oop.d.polymorphism

class GenericList<T>(private val size: Int) {
    // Type erasure
    private val data = Array<Any?>(size) { null }

    operator fun get(i: Int): T {
        @Suppress("UNCHECKED_CAST")
        return data[i] as T
    }

    operator fun set(where: Int, i: T) {
        data[where] = i
    }

    fun copy(): GenericList<T> {
        val new = GenericList<T>(size)
        for (i: Int in data.indices) {
            @Suppress("UNCHECKED_CAST")
            new[i] = data[i] as T
        }
        return new
    }

    override fun toString(): String = "GenericList(data=${data.contentToString()})"
}

data class MyClass(val a: Int, val b: String)

fun main() {
    val intList = GenericList<Int>(4)
    intList[0] = 0
    intList[1] = 1
    intList[2] = 2
    // intList[3] = "3"
    println(intList.toString())

    val intListCopy: GenericList<Int> = intList.copy()
    println(intListCopy.toString())

    val stringList = GenericList<String>(4)
    stringList[0] = "a"
    stringList[1] = "b"
    stringList[2] = "c"
    println(stringList.toString())

    val stringListCopy: GenericList<String> = stringList.copy()
    println(stringListCopy.toString())

    val myList = GenericList<MyClass>(2)
    myList[0] = MyClass(1, "a")
    myList[1] = MyClass(2, "b")
    println(myList.toString())

    // !!!
    // val anyList: GenericList<Any> = intList as GenericList<Any>
    // anyList[1] = 1
    // anyList[1] = "a"
    // println(anyList)

    // class java.lang.String cannot be cast to class java.lang.Number
    // val error: Int = intList[1]
}
