package me.blzr.oop.d.polymorphism

// Полиморфизм подтипов
open class X {
    override fun toString(): String = "I'm X"
}

class Y(val str: String) : X() {
    fun myToString(): String = "TEST"
    override fun toString(): String = "I'm Y: $str"
}

class Z : X()

@Suppress("RedundantExplicitType")
fun main() {
    var x: X = X()
    val y: Y = Y("123")

    println("${x::class.simpleName}: $x") // X: I'm X
    println("${y::class.simpleName}: $y") // Y: I'm Y

    x = y
    println("${x::class.simpleName}: $x") // ???

    val z: X = Y("321")
    println("${z::class.simpleName}: $z") // ???

    // z.myToString()
    (z as Y).myToString()

    x = Z()
    // (x as Y).myToString() // Error
}
