package me.blzr.oop.wip.hof

import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.stream.IntStream
import java.util.stream.StreamSupport
import kotlin.collections.ArrayList
import kotlin.streams.toList
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

// High Order Functions HOF / Функции Высшего Порядка ФВП
// any, fold, filter, map, forEach

// any (T -> Boolean) -> Boolean
// forEach (T -> Unit) -> Unit
// filter (T -> Boolean) -> Collection<T>
// map (T -> R) -> Collection<R>
// fold initial<R>, (accumulator<R>, T -> R) -> R

fun main28() {
    // [1,2,3,4,5] -> [2,4,6,8,10] -> ["**","****","******","********","**********"]

    val srcList = listOf(1, 2, 3, 4, 5)
    val dstList1 = ArrayList<Int>(srcList.size)
    for (i in srcList) {
        val mappedElement = i * 2 // Подвижная часть
        dstList1.add(mappedElement)
    }
    println(dstList1)

    val dstList2 = ArrayList<String>(srcList.size)
    for (i in dstList1) {
        val mappedElement = "*".repeat(i) // Подвижная часть
        dstList2.add(mappedElement)
    }
    println(dstList2)
//    println(listOf(1, 2, 3, 4, 5).myMap { (it * 2).toString() })

    // GOF / Gang of Four / Банда Четырех

    // Strategy
    val srcList2 = listOf(1, 2, 3, 4, 5)

    val multiplier = MultiplyByTwo()
    val multiplyStrategy = MyStrategy<Int, Int>(multiplier)
    val dst2 = multiplyStrategy.function(srcList2)

    val repeater = Repeater("_")
    val repeatStrategy = MyStrategy(repeater)
    val dst3 = repeatStrategy.function(dst2)
    println(dst3)

    // Chain
    val multiplyByTwoRepeater = MultiplyByTwoRepeater("!")
    val mB2RStrategy = MyStrategy(multiplyByTwoRepeater)
    println(mB2RStrategy.function(srcList2))

    // Generic chain
    val chainStrategy = MyStrategy(ComposeApplicator(multiplier, repeater))
    println(chainStrategy.function(srcList2))

    // SAM conversion (Single Abstract Method)
}

interface Applicator<T, R> {
    fun apply(input: T): R
}

class MultiplyByTwo : Applicator<Int, Int> {
    override fun apply(input: Int): Int {
        return input * 2
    }
}

class Repeater(val symbol: String) : Applicator<Int, String> {
    override fun apply(input: Int): String {
        return symbol.repeat(input)
    }
}

class MultiplyByTwoRepeater(val symbol: String) : Applicator<Int, String> {
    val mBy2 = MultiplyByTwo()
    val rep = Repeater(symbol)

    override fun apply(input: Int): String {
        val res1 = mBy2.apply(input)
        val res2 = rep.apply(res1)
        return res2
    }
}

class ComposeApplicator<A, B, C>(
    val applicator1: Applicator<A, B>,
    val applicator2: Applicator<B, C>
) :
    Applicator<A, C>  // (A -> B, B -> C) -> (A -> C)
{
    override fun apply(input: A): C {
        return applicator2.apply(applicator1.apply(input))
    }
}

class MyStrategy<T, R>(val myIface: Applicator<T, R>) {
    fun function(input: List<T>): List<R> {
        val dst = ArrayList<R>(input.size)
        for (i in input) {
            val mappedElement = myIface.apply(i) // Подвижная часть
            dst.add(mappedElement)
        }
        return dst
    }
}

// Left vs Right fold
// Эквивалентность итерации и рекурсии
// TCO tail call optimization

//(1) 1
//(2) 1
//(3) (1) + (2) == 1 + 1
//(4) (2) + (3) == (2) + ((1) + (2)) == 1 + (1 + 1)
//(5) (3) + (4) == ((1) + (2)) + ((2) + (3)) == ((1) + (2)) + ((2) + ((1) + (2))) == ((1 + 1) + 1) + (1 + 1)
//(6) (4) + (5) == ((2) + (3)) + ((3) + (4)) == ((2) + ((1) + (2))) + (((1) + (2)) + ((2) + (3))) == ... == ((1 + 1) + 1) + ((1 + 1) + 1) + (1 + 1)

fun fibi(n: Long): Long {
    var a1: Long = 1
    var a2: Long = 1

    for (i in 3..n) {
        val a3 = a1 + a2
        a1 = a2
        a2 = a3
    }
    return a2
}

// Нельзя пометить как tailrec потому что последняя операция это сумма
fun fib(n: Long): Long = when {
    n <= 2 -> 1
    else -> fib(n - 1) + fib(n - 2)
}

data class Tuple(val a1: Long, val a2: Long, val n: Long)

// Итерация справа налево
fun fibr(t: Tuple): Tuple {
    return if (t.n <= 0) t
    else fibr(Tuple(t.a2, t.a1 + t.a2, t.n - 1))
}

// Итерация слева направо
fun fibrl(n: Long, a1: Long = 0, a2: Long = 1): Long {
    return if (n <= 0) a1
    else fibrl(n - 1, a2, a1 + a2)
}

// https://kotlinlang.org/docs/reference/functions.html#tail-recursive-functions
tailrec fun fibrl2(n: Long, a1: Long = 0, a2: Long = 1): Long {
    return if (n <= 0) a1
    else fibrl2(n - 1, a2, a1 + a2)
}

class Fibonacci : Iterator<Long> {
    var a1 = 1L
    var a2 = 1L

    override fun hasNext(): Boolean = true

    override fun next(): Long {
        val cur = a1

        a1 = a2
        a2 += cur

        println("= $a1 $a2")

        return cur
    }
}

class FibIterable : Iterable<Long> {
    override fun iterator(): Iterator<Long> {
        return Fibonacci()
    }

}

fun main() {
//    println(fib(1))
//    println(fib(2))
//    println(fib(3))
//    println(fib(10))
//    // println(fib(50))
//
//    println(fibi(1))
//    println(fibi(2))
//    println(fibi(3))
//    println(fibi(4))
//    println(fibi(5))
//    println(fibi(50))
//
//    println(fibrl(1))
//    println(fibrl(2))
//    println(fibrl(3))
//    println(fibrl(4))
//    println(fibrl(5))
//    println(fibrl(50))

    // println(fibrl(1_000_000)) // StackOverflowError
    println(fibrl2(1_000_000))

    val fibIter = Fibonacci()

    for (i in 1..5) {
        println(fibIter.next())
    }
// Бесконечная генерация значений
//    for (i in fibIter) {
//        println(i)
//    }

    println("Sequence!")
    // Lazy Evaluation / Ленивые вычисления
    // take, filter, map - intermediate, промежуточные функции, не приводят к вычислению последовательности
    println(Fibonacci().asSequence().take(10).filter { it % 2L == 1L }.map { it })
    println("Sequence List!")
    // find - terminal, завершающая функция, приводит к вычислению последовательности
    println(
        Fibonacci().asSequence()
        .take(100)
        .filter {
            println("# $it")
            it % 2L == 1L
        }
        .find {
            println("? $it")
            it > 100
        }
    )

    println("Koltin collections")
    println(
        FibIterable()
        .take(20)
        .filter {
            println("!! $it")
            it % 2L == 1L
        }
        .find {
            println("** $it")
            it > 100
        }
    )

    println("Lazy/Non Lazy")
    println(Fibonacci().asSequence().take(5))
    println(FibIterable().take(5))
}


// HOF
// ФВП

class Receiver {
    fun doSmth(strategy: IStrategy<List<Int>, Int>) =
        strategy.execute(listOf(1, 2, 3, 4, 5))
}

@FunctionalInterface
interface IStrategy<T, R> {
    fun execute(input: T): R
}


class Counter : IStrategy<List<Int>, Int> {
    override fun execute(input: List<Int>): Int =
        input.count()
}

class Adder : IStrategy<List<Int>, Int> {
    override fun execute(input: List<Int>): Int =
        input.sum()
}

class Receiver2 {
    fun doSmth(strategy: (List<Int>) -> Int) =
        strategy(listOf(1, 2, 3, 4, 5))
}

fun main4() {
    println(Receiver().doSmth(Counter()))
    println(Receiver().doSmth(Adder()))

//    println(Receiver2().doSmth({i: List<Int> -> i.sum() }))
//    println(Receiver2().doSmth { i: List<Int> -> i.sum() })
//    println(Receiver2().doSmth { i -> i.sum() })
    println(Receiver2().doSmth { it.count() })
    println(Receiver2().doSmth { it.sum() })
}

fun main5() {
    // map/collect, filter/find, reduce/fold/inject
    // foreach

    // [1,2,3,4,5,6] -> [x]

    // Sum
    // listOf(1, 2, 3, 4, 5).fold(0,  {acc, current -> acc + current })
    val f1 = listOf(1, 2, 3, 4, 5).fold(0) { acc, current ->
        acc + current
    }
    println(f1)

    // count
    val f2 = listOf(1, 2, 3, 4, 5).fold(0) { acc, current ->
        acc + 1
    }
    println(f2)

    // a, b :: collection
    // a -> b
    // size(a) == size(b)
    // map
    // [a, b, c] -> [a1, b1, c1]

    // toStr Collection<Int> -> Collection<String>
    val f3 = listOf(1, 2, 3, 4, 5).map { current -> "!$current!" }
    println(f3)
    // +10 Collection<Int> -> Collection<Int>
    // val f4 = listOf(1, 2, 3, 4, 5).map { current -> current + 10 }
    val f4 = listOf(1, 2, 3, 4, 5).map { it + 10 } // если анонимная функция содержит 1 элемент
    println(f4)

    // a, b :: collection
    // filter
    // a -> b
    // T -> T
    // size(a) <= size(b)
    // [a, b, c] -> [a, c]

    val f5 = listOf(1, 2, 3, 4, 5).filter { it % 2 == 0 }
    println(f5)

    val f6 = listOf(1, 2, 3, 4, 5).reversed().filterIndexed { index, elem -> index % 2 == 0 }
    println(f6)

    // map via fold
    // + 10
    val f7 = listOf(1, 2, 3, 4, 5).fold(emptyList<Int>()) { acc, current ->
        // acc.plus(current + 10)
        // acc + (current + 10) // [] + elem
        acc + listOf(current + 10) // [] + []
    }
    println(f7)

    // filter via fold
    // odd elements
    val f8 = listOf(1, 2, 3, 4, 5).fold(emptyList<Int>()) { acc, current ->
//        return@fold if (current % 2 == 0) {
//            acc + current
//        } else {
//            acc
//        }
        if (current % 2 == 0) acc + current else acc
    }
    println(f8)
}

@ExperimentalTime
fun main6() {
    // stream vs map

    // map > filter > reduce

    println("Begin2")
    println(measureTime {

        //val res = IntStream.range(0, 1_000_000_000).map {
        val res = IntStream.range(0, 10_000_000).map {
            // Thread.sleep(1)
            it * 10
        }.map {
            it + 1
        }
//        }.mapToObj {
//            it.toString()
//        }
            //.limit(10)
            .reduce { acc, i -> acc + i }

        println(res)
    })

    println("Begin1")
    println(measureTime {
        val res = (0..10_000_000).map {
            // Thread.sleep(1)
            it * 10
        }.map {
            it + 1
        }.map {
            // it.toString()
            it
        }.take(10)
//        }.filterIndexed { i, e ->
//            i < 10
//        }
            .reduce { acc, i ->
                acc + i
            }
        println(res)
    })

    println("Begin3")
    println(measureTime {
        val res = IntStream.range(0, 10_000_000).map {
            // Thread.sleep(1)
            it * 10
        }.map {
            it + 1
        }.mapToObj {
            it.toString()
        }
        //.limit(10)
        //.reduce { acc, i -> acc + i }

        println(res)
    })
}

@ExperimentalTime
fun main7() {
    fun mul(i: Int): Int = i * 10
    fun add(i: Int): Int = i + 1

    fun compose(i: Int): Int = add(mul(i))
    println(measureTime {
        val res1 = (0..10_000_000).map(::mul).map(::add).reduce { acc, i -> acc + i }
        println(res1)
    })

    println(measureTime {
        //val res1 = (0..10_000_000).map { it -> compose(it) }.reduce { acc, i -> acc + i }
        val res1 = (0..10_000_000).map(::compose).reduce { acc, i -> acc + i }
        println(res1)
    })
}

//<- foldl == foldleft
//[1, 2, 3, 4]
// tailrec
fun fibRec(n: Long): Long =
    when {
        n <= 1 -> 1
        else -> fibRec(n - 1) + fibRec(n - 2)
    }

//-> foldr == foldright
//[1, 2, 3, 4]
fun fibRecTail(n: Long) = fibRecTail(0, n, 1, 1)
tailrec fun fibRecTail(i: Long, max: Long, iMinus1: Long, cur: Long): Long {
    return when {
        i >= max -> cur
        else -> {
            val next = iMinus1 + cur
            fibRecTail(i + 1, max, cur, next)
        }
    }
}
/*
[0, -, -] -> a
[1, -, -] -> b
[2, a, b] -> c = a + b
[3, b, c] -> d = b + c
*/

class FibGenerator {
    private var cur: Long = 1
    private var prev: Long = 1

    fun hasNext() = true
    fun next(): Long {
        val next = cur + prev
        prev = cur
        cur = next
        return cur
    }
}

class FibIterator : Iterator<Long> {
    private var cur: Long = 1
    private var prev: Long = 1

    override fun hasNext() = true
    override fun next(): Long {
        val next = cur + prev
        prev = cur
        cur = next
        return cur
    }
}

class C : AutoCloseable {
    override fun close() {
        println("closed")
    }
}

class MyWrap {
    fun wrap(lambda: () -> String) {
        println("Begin")
        lambda()
        println("end")
    }
}

class D {
    fun x(): String {
        val use = C().use {
            println("opened")
            //return@use "a"
            return@x "asd"
        }
        return ""
    }

//    fun y(): String {
//        MyWrap().wrap {
//            println("Inside")
//            return "blabla"
//        }
//    }
}

@ExperimentalTime
fun main8() {
    D().x()

    println(measureTime {
        println(fibRec(40))
    })

//    println(fibRecTail(0))
//    println(fibRecTail(1))
//    println(fibRecTail(2))
//    println(fibRecTail(3))
//    println(fibRecTail(4))
//    println(fibRecTail(5))
    println(measureTime {
        println(fibRecTail(100))
    })

    println("Generator")
    println(measureTime {
        val gen = FibGenerator()
        for (i in 0..98) {
            gen.next()
        }
        println(gen.next())
    })

    println("Stream")
    println(measureTime {
        val fibStream = StreamSupport.stream(FibIterator().asSequence().asIterable().spliterator(), false)
        println(fibStream.limit(20).toList())

        val fibStream2 = StreamSupport.stream(FibIterator().asSequence().asIterable().spliterator(), false)
        println(fibStream2.skip(99).limit(1).toList()[0])

//        for (i in FibIterator()) {
//            println(i)
//        }

        println(Random().ints().limit(100).toList())
    })
}

fun main20() {
    val async1: CompletableFuture<Int> = CompletableFuture.supplyAsync {
        println("${Thread.currentThread().id}: Inside")
        Thread.sleep(3000)
        println("${Thread.currentThread().id}: End Sleep")
        10
    }
    val async2: CompletableFuture<Int> = CompletableFuture.supplyAsync {
        println("${Thread.currentThread().id}: Inside")
        Thread.sleep(3000)
        println("${Thread.currentThread().id}: End Sleep")
        20
    }

    async1.thenAccept { result ->
        println("Callback on result: $result ${Thread.currentThread().id}")
    }

    CompletableFuture.allOf(async1, async2).thenAccept {
        println("${Thread.currentThread().id}: Wait for two")
        println("${Thread.currentThread().id}: async1 ${async1.get()}")
        println("${Thread.currentThread().id}: async2 ${async2.get()}")
    }

    for (i in 0..5) {
        if (async1.isDone) {
            println("Result: ${async1.get()}")
        } else {
            println("Not Yet1 in ${Thread.currentThread().id}")
            Thread.sleep(1000)
        }
    }

}
