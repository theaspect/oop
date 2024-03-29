package me.blzr.oop.d.polymorphism

// Полиморфизм перегрузки 1
class A {
    fun say(what: String): String = what
    fun say(what: String, whom: String): String = "$what $whom"
}

fun main() {
    val a = A()

    println(a.say("Hello")) // Hello
    println(a.say("Hello", "World")) // World
}
