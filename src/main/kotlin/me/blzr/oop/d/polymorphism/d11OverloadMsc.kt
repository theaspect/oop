package me.blzr.oop.d.polymorphism

// Полиморфизм перегрузки 2
// Any > Number > Int
class B {
    fun say(what: Any): Unit = println("Any: $what")
    fun say(what: Number): Unit = println("Number: $what")
    fun say(what: Int): Unit = println("Int: $what")

    // fun say(what: Int): String = println("String: $what")
}

fun main() {
    val b = B()

    b.say("Hello") // Any: Hello
    b.say(1.0) // Number: 1.0
    b.say(1) // Int: 1
    println()

    // Most specific candidate
    // https://kotlinlang.org/spec/overload-resolution.html#algorithm-of-msc-selection
    var int: Int = 1
    val num: Number = int
    val any: Any = num

     b.say(int) // ?
     b.say(num) // ?
     b.say(any) // ?

    println()
    b.say(any as Int) // ?

    println()
    int *= 2
    // b.say(any) // ?
}
