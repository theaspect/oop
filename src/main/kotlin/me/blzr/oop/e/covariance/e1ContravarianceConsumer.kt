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
    // 'consume' overrides nothing
    // override fun consume(a: Grass){}

    // LSP violation
    override fun consume(a: Food) {
        //throw Exception()
        println("Food Herbivore")
    }

    // fun consume(a: Grass) {
    //    println("Grass Herbivore")
    // }
}

open class Panda : Herbivore() {
    // 'consume' overrides nothing
    // override fun consume(a: Bamboo){}
}

@Suppress("RedundantExplicitType")
fun main() {
    val ff: Food = Food()
    val fg: Food = Grass()
    val gg: Grass = Grass()

    val a1: Animal = Herbivore()
    a1.consume(ff) // Bad
    a1.consume(fg) // Bad
    a1.consume(gg) // Bad

    println()
    val a2: Herbivore = Herbivore()
    a2.consume(ff) // Bad
    a2.consume(fg) // Bad
    a2.consume(gg)
}
