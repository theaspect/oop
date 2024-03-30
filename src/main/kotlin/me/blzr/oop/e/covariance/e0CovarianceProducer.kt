package me.blzr.oop.e.covariance

// Covariance
// results
open class Entertainment
open class Music : Entertainment()
open class GuitarSolo : Music()
// producers

open class Entertainer {
    open fun produce(): Entertainment = Entertainment()
}

open class Musician : Entertainer() {
    override fun produce(): Music = Music()
}

open class Rockstar : Musician() {
    override fun produce(): GuitarSolo = GuitarSolo()
}

@Suppress("RedundantExplicitType", "UNUSED_VARIABLE", "USELESS_IS_CHECK")
fun main() {
    // Верхний уровень
    val pe1: Entertainer = Entertainer()
    val re1: Entertainment = pe1.produce()
    // Type mismatch: inferred type is Entertainment but Music was expected
    // val re2: Music = pe1.produce()

    val pe2: Entertainer = Musician()
    val rm1: Entertainment = pe2.produce()
    // Type mismatch: inferred type is Entertainment but Music was expected
    // val rm2: Music = pe2.produce() // !
    val rm2: Music = (pe2 as Musician).produce() //  Still works
    if (pe2 is Musician) {
        val rm3: Music = pe2.produce()
    }
    val pe3: Entertainer = Rockstar()

    // Средний уровень
    // Type mismatch: inferred type is Entertainer but Musician was expected
    // val pm1: Musician = Entertainer()
    val pm2: Musician = Musician()
    val pm3: Musician = Rockstar()

    // Type mismatch: inferred type is Entertainer but Rockstar was expected
    // val pr1: Rockstar = Entertainer()
    // Type mismatch: inferred type is Musician but Rockstar was expected
    // val pr2: Rockstar = Musician()
    val pr3: Musician = Rockstar()
}
