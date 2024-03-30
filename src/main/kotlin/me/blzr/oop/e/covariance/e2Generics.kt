package me.blzr.oop.e.covariance

// kotlin in/opt generics, Java PECS = producer extends, consumer super
// consumers
open class Animal1<T : Food> {
    open fun consume(a: T) {
        println("Animal1 $a")
    }

    open fun getT(a: T): T {
        return a
    }
}

open class Herbivore1<T : Grass> : Animal1<T>() {
    override fun consume(a: T) {
        println("Herbivore $a")
    }

    override fun getT(a: T): T {
        return a
    }
}

open class Panda1<T : Bamboo> : Herbivore1<T>() {
    override fun consume(a: T) {
        println("Panda $a")
    }

    override fun getT(a: T): T {
        return a
    }
}

@Suppress("RemoveExplicitTypeArguments")
fun main() {
    val a1: Animal1<Food> = Animal1<Food>()
    val a2: Animal1<Grass> = Herbivore1<Grass>()
    // val a3: Animal1<Food> = Herbivore1<Grass>()

    a1.consume(Food())
    a1.consume(Grass())
    // Type mismatch: inferred type is Food but Grass was expected
    // a2.consume(Food())
    a2.consume(Grass())

    println(a1.getT(Food()))
    println(a1.getT(Grass()))
    // Type mismatch: inferred type is Food but Grass was expected
    // a2.consume(Food())
    println(a2.getT(Grass()))
}
