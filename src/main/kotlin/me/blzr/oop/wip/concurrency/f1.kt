package me.blzr.oop.wip.concurrency

import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.ReentrantReadWriteLock

//T1          T2          T3    Т4...Т100
//A,X,Y       B,V,W       C      D,F,G,H
//Lock A
//            Lock B
//            Lock A
//--- Тут
//Хочу B      Хочу C      Хочу А (строим граф зависимостей, ищем цикл и разрываем)
//
//Lock B

fun main31() {
    // ConcurrentModificationException
    val list = mutableListOf(1, 2, 3, 4)
    for (i in list) {
        list.add(1)
    }
}

/*data class Table(
    var fork: Boolean = true,
    var spoon: Boolean = true
)
class Philosopher(val name: String, val table: Table) {
    var fork: Boolean = false
    var spoon: Boolean = false
    fun grabFork(): Boolean {
        if (this.fork) {
            return true
        } else {
            println("$name Trying grab fork")
            if (!table.fork) {
                println("$name No fork available")
                return false
            } else {
                println("$name Grabbing fork")
                table.fork = false
                this.fork = true
                return true
            }
        }
    }
    fun grabSpoon(): Boolean {
        if (this.spoon) {
            return true
        } else {
            println("$name Trying grab spoon")
            if (!table.spoon) {
                println("$name No spoon available")
                return false
            } else {
                println("$name Grabbing spoon")
                table.spoon = false
                this.spoon = true
                return true
            }
        }
    }
    fun eat() {
        while (true) {
            if (!fork && !spoon) {
                grabFork()
                Thread.sleep(Random().nextInt(100).toLong())
                grabSpoon()
                Thread.sleep(Random().nextInt(100).toLong())
            }
            if (fork && spoon) {
                println("$name Eating")
                fork = false
                spoon = false
                table.fork = true
                table.spoon = true
                println("$name Freeing")
                Thread.sleep(Random().nextInt(1000).toLong())
            } else {
                println("$name Sleeping")
                Thread.sleep(Random().nextInt(1000).toLong())
            }
        }
    }
}
// Deadlock
fun main() {
    val table = Table()
    val vasya = Philosopher("vasya", table)
    val petya = Philosopher("petya", table)
    val t1 = Thread {
        vasya.eat()
    }
    t1.start()
    val t2 = Thread {
        petya.eat()
    }
    t2.start()
    t1.join()
    t2.join()
}*/

/*data class Room(val id: Int, var temp: Int)
class Sensor(val id: Int, val rooms: List<Room>) {
    fun measure() {
        while (true) {
            for (r in rooms) {
                if (r.temp <= 20) {
                    Thread.sleep(100)
                    println("Sensor $id checking room $r increase temp")
                    r.temp += 1
                } else {
                    println("Sensor $id checking room $r ok")
                    Thread.sleep(100)
                }
            }
            Thread.sleep(200)
        }
    }
}
fun main() {
    val rooms = (0..10).map { Room(it, Random().nextInt(20)) }
    (0..5).map {
        val sensor = Sensor(it, rooms)
        val t = Thread {
            sensor.measure()
        }
        t.start()
        t
    }.forEach {
        it.join()
    }
}*/
/*
// Still deadlock
//
// Вася                         Петя
// Проверяет (ВЛ свободны)      Проверяет (ВЛ свободны)
// Пытается взять вилку
// Берет вилку
//                              Пытается взять вилку
//                              Неуспешно
//                              Пытается взять ложку
//                              Успешно
// Пытается взять ложку
// Неуспешно
// Спим
data class Table(
    var fork: Boolean = true,
    var spoon: Boolean = true
)
class Philosopher(val name: String, val table: Table) {
    var fork: Boolean = false
    var spoon: Boolean = false
    fun grabFork(): Boolean {
        if (this.fork) {
            return true
        } else {
            println("$name Trying grab fork")
            if (!table.fork) {
                println("$name No fork available")
                return false
            } else {
                println("$name Grabbing fork")
                table.fork = false
                this.fork = true
                return true
            }
        }
    }
    fun grabSpoon(): Boolean {
        if (this.spoon) {
            return true
        } else {
            println("$name Trying grab spoon")
            if (!table.spoon) {
                println("$name No spoon available")
                return false
            } else {
                println("$name Grabbing spoon")
                table.spoon = false
                this.spoon = true
                return true
            }
        }
    }
    fun eat() {
        while (true) {
            if (!fork && !spoon) {
                if (table.fork && table.spoon) {
                    Thread.sleep(Random().nextInt(100).toLong())
                    println("$name Both fork and spoon are available")
                    grabFork()
                    Thread.sleep(Random().nextInt(100).toLong())
                    grabSpoon()
                    Thread.sleep(Random().nextInt(100).toLong())
                } else {
                    println("$name Fork or spoon not available")
                    Thread.sleep(Random().nextInt(100).toLong())
                }
            }
            if (fork && spoon) {
                println("$name Eating")
                fork = false
                spoon = false
                table.fork = true
                table.spoon = true
                println("$name Freeing")
                Thread.sleep(Random().nextInt(1000).toLong())
            } else {
                println("$name Sleeping")
                Thread.sleep(Random().nextInt(1000).toLong())
            }
        }
    }
}
// Deadlock
fun main() {
    val table = Table()
    val vasya = Philosopher("vasya", table)
    val petya = Philosopher("petya", table)
    val t1 = Thread {
        vasya.eat()
    }
    t1.start()
    val t2 = Thread {
        petya.eat()
    }
    t2.start()
    t1.join()
    t2.join()
}*/

/*data class Table(
    var fork: Boolean = true,
    var spoon: Boolean = true
)
// Вася                 Петя
// Пробует захватить
// Успешно
//                      Пробует захватить
//                      Неуспешно
//                      Спит
// Обедает
//                      Пробует захватить
//                      Неуспешно
//                      Спит
// Спит
// Пробует захватить
// Успешно
// Обедает
//                      Просыпается
//                      Пробует захватить
//                      Неуспешно
//                      Спит
// Starvation
class Philosopher(val name: String, val table: Table) {
    var fork: Boolean = false
    var spoon: Boolean = false
    var hunger: Long = 1
    fun grabFork(): Boolean {
        if (this.fork) {
            return true
        } else {
            println("$name Trying grab fork")
            if (!table.fork) {
                println("$name No fork available")
                return false
            } else {
                println("$name Grabbing fork")
                table.fork = false
                this.fork = true
                return true
            }
        }
    }
    fun grabSpoon(): Boolean {
        if (this.spoon) {
            return true
        } else {
            println("$name Trying grab spoon")
            if (!table.spoon) {
                println("$name No spoon available")
                return false
            } else {
                println("$name Grabbing spoon")
                table.spoon = false
                this.spoon = true
                return true
            }
        }
    }
    fun eat() {
        while (true) {
            if (!fork && !spoon) {
                println("$name Both fork and spoon are available")
                grabFork()
                Thread.sleep(Random().nextInt(100).toLong())
                grabSpoon()
                Thread.sleep(Random().nextInt(100).toLong())
            }
            if (fork && spoon) {
                hunger -= 1
                println("$name Eating $hunger")
                Thread.sleep(Random().nextInt(1000).toLong())
                fork = false
                spoon = false
                table.fork = true
                table.spoon = true
                println("$name Freeing")
            } else if (fork) {
                hunger += 1
                fork = false
                table.fork = true
                println("$name Freeing fork $hunger")
                Thread.sleep(Random().nextInt(1000).toLong())
            } else if (spoon) {
                hunger += 1
                spoon = false
                table.spoon = true
                println("$name Freeing spoon $hunger")
                Thread.sleep(Random().nextInt(1000).toLong())
            } else {
                hunger += 1
                println("$name Sleeping $hunger")
                Thread.sleep(Random().nextInt(1000).toLong())
            }
        }
    }
}
// Deadlock
fun main() {
    val table = Table()
    val vasya = Philosopher("vasya", table)
    val petya = Philosopher("petya", table)
    val t1 = Thread {
        vasya.eat()
    }
    //t1.priority = Thread.MAX_PRIORITY
    t1.start()
    val t2 = Thread {
        petya.eat()
    }
    //t2.priority = Thread.MAX_PRIORITY
    t2.start()
    t1.join()
    t2.join()
}*/

/*data class Table(
    var fork: Boolean = true,
    var spoon: Boolean = true
)
// Ok
class Philosopher(val name: String, val table: Table) {
    var fork: Boolean = false
    var spoon: Boolean = false
    var hunger: Long = 1
    fun grabFork(): Boolean {
        if (this.fork) {
            return true
        } else {
            println("$name Trying grab fork")
            if (!table.fork) {
                println("$name No fork available")
                return false
            } else {
                println("$name Grabbing fork")
                table.fork = false
                this.fork = true
                return true
            }
        }
    }
    fun grabSpoon(): Boolean {
        if (this.spoon) {
            return true
        } else {
            println("$name Trying grab spoon")
            if (!table.spoon) {
                println("$name No spoon available")
                return false
            } else {
                println("$name Grabbing spoon")
                table.spoon = false
                this.spoon = true
                return true
            }
        }
    }
    fun eat() {
        while (true) {
            if (grabFork()) {
                Thread.sleep(Random().nextInt(100).toLong())
                println("$name grabbed fork $table f: $fork s: $spoon")
                if (grabSpoon()) {
                    Thread.sleep(Random().nextInt(100).toLong())
                    println("$name grabbed spoon $table f: $fork s: $spoon")
                    hunger -= 1
                    println("$name eating $hunger $table f: $fork s: $spoon")
                    fork = false
                    spoon = false
                    table.fork = true
                    table.spoon = true
                    Thread.sleep(Random().nextInt(1000).toLong())
                } else {
                    hunger += 1
                    println("$name spoon not available $hunger $table f: $fork s: $spoon")
                    Thread.sleep(Random().nextInt(100).toLong())
                    fork = false
                    table.fork = true
                }
            } else {
                hunger += 1
                println("$name fork not available $hunger $table f: $fork s: $spoon")
                Thread.sleep(Random().nextInt(100).toLong())
            }
        }
    }
}
// Protocol
fun main() {
    val table = Table()
    val vasya = Philosopher("vasya", table)
    val petya = Philosopher("petya", table)
    val t1 = Thread {
        vasya.eat()
    }
    //t1.priority = Thread.MAX_PRIORITY
    t1.name = "vasya thread"
    t1.start()
    val t2 = Thread {
        petya.eat()
    }
    //t1.priority = Thread.MIN_PRIORITY
    t2.name = "petya thread"
    t2.start()
    t1.join()
    t2.join()
}*/

//data class Room(val id: Int, var temp: Int)

/*data class Room(val id: Int, var temp: Int) {
    @Synchronized
    fun checkTemp() {
        if (temp <= 20) {
            Thread.sleep(100)
            println("Sensor $id checking room $this increase temp")
            temp += 1
        } else {
            println("Sensor $id checking room $this ok")
            Thread.sleep(100)
        }
    }
}
class Sensor(val id: Int, val rooms: List<Room>) {
    fun measure() {
        while (true) {
            for (r in rooms) {
                synchronized(r) {
                    r.checkTemp()
                }
            }
            Thread.sleep(200)
        }
    }
}
fun main() {
    val rooms = (0..10).map { Room(it, Random().nextInt(20)) }
    (0..5).map {
        val sensor = Sensor(it, rooms)
        val t = Thread {
            sensor.measure()
        }
        t.start()
        t
    }.forEach {
        it.join()
    }
}*/

/*data class Table(
    var fork: Boolean = true,
    var spoon: Boolean = true
)
// Ok
data class Philosopher(val name: String, val table: Table) {
    var fork: Boolean = false
    var spoon: Boolean = false
    var hunger: Long = 1
    fun grabFork(): Boolean {
        synchronized(table) {
            if (this.fork) {
                return true
            } else {
                println("$name Trying grab fork")
                if (!table.fork) {
                    println("$name No fork available")
                    return false
                } else {
                    println("$name Grabbing fork")
                    table.fork = false
                    this.fork = true
                    return true
                }
            }
        }
    }
    fun grabSpoon(): Boolean {
        synchronized(table) {
            if (this.spoon) {
                return true
            } else {
                println("$name Trying grab spoon")
                if (!table.spoon) {
                    println("$name No spoon available")
                    return false
                } else {
                    println("$name Grabbing spoon")
                    this.spoon = true
                    Thread.sleep(20)
                    table.spoon = false
                    return true
                }
            }
        }
    }
    fun eat() {
        while (true) {
            synchronized(table) {
                if (grabFork()) {
                    Thread.sleep(Random().nextInt(100).toLong())
                    println("$name grabbed fork $table f: $fork s: $spoon")
                    if (grabSpoon()) {
                        Thread.sleep(Random().nextInt(1000).toLong())
                        println("$name grabbed spoon $table f: $fork s: $spoon")
                        hunger -= 1
                        println("$name eating $hunger $table f: $fork s: $spoon")
                        table.fork = true
                        table.spoon = true
                        Thread.sleep(20)
                        fork = false
                        spoon = false
                        Thread.sleep(Random().nextInt(100).toLong())
                    } else {
                        hunger += 1
                        println("$name spoon not available $hunger $table f: $fork s: $spoon")
                        Thread.sleep(Random().nextInt(100).toLong())
                        table.fork = true
                        Thread.sleep(20)
                        fork = false
                    }
                } else {
                    hunger += 1
                    println("$name fork not available $hunger $table f: $fork s: $spoon")
                    Thread.sleep(Random().nextInt(100).toLong())
                }
            }
        }
    }
    override fun toString(): String {
        return "Philosopher(name='$name', fork=$fork, spoon=$spoon)"
    }
}
// Protocol
fun main() {
    val table = Table()
    val vasya = Philosopher("vasya", table)
    val petya = Philosopher("petya", table)
    val t1 = Thread {
        vasya.eat()
    }
    //t1.priority = Thread.MIN_PRIORITY
    t1.name = "vasya thread"
    t1.start()
    val t2 = Thread {
        petya.eat()
    }
    //t2.priority = Thread.MIN_PRIORITY
    t2.name = "petya thread"
    t2.start()
    val checker = Thread {
        while (true) {
            synchronized(table) {
                val t = table
                val v = vasya
                val p = petya
                val forkCount = (if (t.fork) 1 else 0) + (if (v.fork) 1 else 0) + (if (p.fork) 1 else 0)
                val spoonCount = (if (t.spoon) 1 else 0) + (if (v.spoon) 1 else 0) + (if (p.spoon) 1 else 0)
                if (spoonCount < 1) println("INCONSISTENCY NO SPOON $t, $v, $p")
                if (spoonCount > 1) println("INCONSISTENCY TOO MANY SPOON $t, $v, $p")
                if (forkCount < 1) println("INCONSISTENCY NO FORK $t, $v, $p")
                if (forkCount > 1) println("INCONSISTENCY TOO MANY FORKS $t, $v, $p")
            }
            Thread.sleep(10)
        }
    }
    checker.start()
    t1.join()
    t2.join()
    checker.join()
}*/

data class Table(
    var fork: Boolean = true,
    var spoon: Boolean = true
) {
    val flock = ReentrantLock()
    val slock = ReentrantLock()
    // val rwlock = ReentrantReadWriteLock()
}

// Ok
data class Philosopher(val name: String, val table: Table) {
    var fork: Boolean = false
    var spoon: Boolean = false
    var hunger: Long = 1

    fun grabFork(): Boolean {
        if (this.fork) {
            return true
        } else {
            println("$name Trying grab fork")
            if (!table.fork) {
                println("$name No fork available")
                return false
            } else {
                println("$name Grabbing fork")
                table.fork = false
                this.fork = true
                return true
            }
        }
    }

    fun grabSpoon(): Boolean {
        if (this.spoon) {
            return true
        } else {
            println("$name Trying grab spoon")
            if (!table.spoon) {
                println("$name No spoon available")
                return false
            } else {
                println("$name Grabbing spoon")
                this.spoon = true
                Thread.sleep(20)
                table.spoon = false
                return true
            }
        }
    }

    fun eat() {
        while (true) {
            if (table.flock.tryLock(1, TimeUnit.SECONDS)) {
                Thread.sleep(Random().nextInt(100).toLong())
                if (table.slock.tryLock(1, TimeUnit.SECONDS)) {
                    Thread.sleep(Random().nextInt(1000).toLong())
                    println("$name grabbed spoon $table f: $fork s: $spoon")
                    hunger -= 1
                    println("$name eating $hunger $table f: $fork s: $spoon")
                    table.slock.unlock()
                } else {
                    hunger += 1
                    println("$name spoon not available $hunger $table f: $fork s: $spoon")
                    Thread.sleep(Random().nextInt(100).toLong())
                }
                table.flock.unlock()
            } else {
                hunger += 1
                println("$name fork not available $hunger $table f: $fork s: $spoon")
                Thread.sleep(Random().nextInt(100).toLong())
            }
        }
    }

    override fun toString(): String {
        return "Philosopher(name='$name', fork=$fork, spoon=$spoon)"
    }
}

// Protocol
fun main() {
    val table = Table()
    val vasya = Philosopher("vasya", table)
    val petya = Philosopher("petya", table)

    val t1 = Thread {
        vasya.eat()
    }
    //t1.priority = Thread.MIN_PRIORITY
    t1.name = "vasya thread"
    t1.start()

    val t2 = Thread {
        petya.eat()
    }
    //t2.priority = Thread.MIN_PRIORITY
    t2.name = "petya thread"
    t2.start()

    val checker = Thread {
        while (true) {
            if (table.flock.tryLock(1, TimeUnit.SECONDS)) {
                if(table.slock.tryLock(1, TimeUnit.SECONDS)) {
                    val t = table
                    val v = vasya
                    val p = petya

                    val forkCount = (if (t.fork) 1 else 0) + (if (v.fork) 1 else 0) + (if (p.fork) 1 else 0)
                    val spoonCount = (if (t.spoon) 1 else 0) + (if (v.spoon) 1 else 0) + (if (p.spoon) 1 else 0)

                    if (spoonCount < 1) println("INCONSISTENCY NO SPOON $t, $v, $p")
                    if (spoonCount > 1) println("INCONSISTENCY TOO MANY SPOON $t, $v, $p")
                    if (forkCount < 1) println("INCONSISTENCY NO FORK $t, $v, $p")
                    if (forkCount > 1) println("INCONSISTENCY TOO MANY FORKS $t, $v, $p")

                    table.slock.unlock()
                }
                table.flock.unlock()
            } else {
                println("Checker can't lock")
            }

            Thread.sleep(10)
        }
    }
    checker.start()

    t1.join()
    t2.join()
    checker.join()

    Thread.yield()
}

// BUSY WAITING
