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

fun main() {
    val pe1: Entertainer = Entertainer()
    val re1: Entertainment = pe1.produce()
    // val re2: Music = pe1.produce() // !

    val pe2: Entertainer = Musician()
    val rm1: Entertainment = pe2.produce()
    // val rm2: Music = pe2.produce() // !
    // val rm2: Music = (pe2 as Musician).produce() //  Still works
    if (pe2 is Musician) {
        val rm2: Music = pe2.produce()
    }
    val pe3: Entertainer = Rockstar()

    // val pm1: Musician = Entertainer() // !
    val pm2: Musician = Musician()
    val pm3: Musician = Rockstar()

    // val pr1: Rockstar = Entertainer() // !
    // val pr2: Rockstar = Musician()    // !
    val pr3: Musician = Rockstar()

}
