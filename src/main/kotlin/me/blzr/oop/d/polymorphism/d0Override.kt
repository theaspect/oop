package me.blzr.oop.d.polymorphism

// Полиморфизм подтипов
open class X {
    override fun toString(): String = "I'm X"
}

class Y : X() {
    override fun toString(): String = "I'm Y"
}

fun main() {
    val x = X()
    val y = Y()

    println("${x::class.simpleName}: $x") // X: I'm X
    println("${y::class.simpleName}: $y") // Y: I'm Y

    // x = y
    // println("${x::class.simpleName}: $x") // ???
}
