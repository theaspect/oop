package me.blzr.oop.e.covariance

// Contravariance
// products
open class Food

open class Grass : Food()
open class Bamboo : Grass()

// consumers
open class Animal {
    open fun consume(a: Food) {
        println("Food Animal")
    }
}

open class Herbivore : Animal() {
    // override fun consume(a: Grass){} // ! error

    // LSP violation
    override fun consume(a: Food) {
        //throw Exception()
        println("Food Herbivore")
    }

    fun consume(a: Grass) {
        println("Grass")
    }
}

open class Panda : Herbivore() {
    // override fun consume(a: Bamboo){} // ! error
}

/**
 * FH, FH
 * G, G
 *
 */
@Suppress("RedundantExplicitType")
fun main() {
    val f1: Food = Food()
    val f2: Food = Grass()
    val f3: Grass = Grass()

    val a1: Animal = Herbivore()
    a1.consume(f1)
    a1.consume(f2)
    a1.consume(f3)

    println("===")
    val a2: Herbivore = Herbivore()
    a2.consume(f1)
    a2.consume(f2)
    a2.consume(f3)
}
