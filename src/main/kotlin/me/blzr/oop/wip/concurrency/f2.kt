package me.blzr.oop.wip.concurrency

import java.util.concurrent.*
import kotlin.random.Random

// Синхронность, параллельности от Асинхронности
// Аналогия клиенты в кофейне

class MyRunnable(val inp: String) : Runnable {
    var output: String? = null
    override fun run() {
        Thread.sleep(5000)
        println("${Thread.currentThread().id} Execution in thread")
        output = "Result: $inp"
    }
}

fun main11() {
// fun main() {
    val myRunnable = MyRunnable("Hello World")
    val thread = Thread(myRunnable)
    thread.start()
    println("${Thread.currentThread().id} Execution in main")

    // thread.join() // Ожидание завершение треда

    println("${Thread.currentThread().id} Result: " + myRunnable.output)
}

class MyInterruptible1 : Runnable {
    override fun run() {
        try {
            println("${Thread.currentThread().id} Sleeping in thread")
            Thread.sleep(10000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
            Thread.currentThread().interrupt()
        }
    }
}

class MyInterruptible2 : Runnable {
    override fun run() {
        while (true) {
            if (Thread.interrupted()) {
                println("${Thread.currentThread().id} Interrupting thread")
                Thread.currentThread().interrupt()
                throw InterruptedException("Ok stopping")
            }
            println("${Thread.currentThread().id} Sleeping in thread")
            Thread.sleep(100)
        }
    }
}

// fun main() {
fun main13() {
    //val thread = Thread(MyInterruptible1())
    val thread = Thread(MyInterruptible2())
    thread.setUncaughtExceptionHandler { t, e -> println("In thread ${t?.id} caught exception $e") }
    thread.start()
    println("${Thread.currentThread().id} Sleeping in main")
    Thread.sleep(2000)
    thread.interrupt()
    thread.join()
    println("${Thread.currentThread().id} Interrupted thread")
}

fun main14() {
// fun main() {
    val pool = Executors.newFixedThreadPool(10)

    for (i: Int in 1..1_000_000) {
        pool.submit {
            try {
                Thread.sleep(100)
            } catch (e: InterruptedException) {
                println("Thread ${Thread.currentThread().id} : $i : Interrupted")
                Thread.currentThread().interrupt()
                return@submit
            }
            println("Thread ${Thread.currentThread().id} : $i")
        }
    }
    println("All tasks submitted")
    Thread.sleep(2000)
    println("Thread ${Thread.currentThread().id} shutdown now")
    pool.shutdownNow() // Очищает очередь и прерывает текущие задачи
    // pool.shutdown() // Запрещает добавлять новые в очередь, но текущие задачи выполняет полностью
    println("Thread ${Thread.currentThread().id} await termination")
    pool.awaitTermination(10, TimeUnit.SECONDS)
    println("Thread ${Thread.currentThread().id} Done")
}

//fun main() {
fun main15() {
    val t = Thread {
        try {
            while (true) {
                Thread.sleep(100)
                println("${Thread.currentThread().id} executing")
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
    t.isDaemon = true
    t.start()
    // Нет синхронного ожидания завершения задачи
    Thread.sleep(1000)
    println("Done")

}

//fun main() {
fun main16() {
    val pool = Executors.newFixedThreadPool(5)

    val futureResultsList = (1..20).map { i: Int ->
        pool.submit(Callable {
            if (i % 5 == 0) {
                throw RuntimeException("ERROR!")
            }
            Thread.sleep(200)
            println("Thread ${Thread.currentThread().id} : $i")
            "FutureResult $i"
        })
    }
    println("All tasks submitted")

    // Ожидание результата с таймаутом
    futureResultsList.forEachIndexed { id, future ->
        try {
            val result = future.get(50, TimeUnit.MILLISECONDS)
            println("Result $id: $result")
        } catch (e: TimeoutException) {
            println("Result $id timeout")
        } catch (e: Exception) {
            println("ResException $id: $e")
        }
    }

    // Синхронное ожидание результата
    futureResultsList.forEachIndexed { id, future ->
        try {
            val result = future.get()
            println("Result $id: $result")
        } catch (e: TimeoutException) {
            println("Result $id timeout")
        } catch (e: Exception) {
            println("ResException $id: $e")
        }
    }
}

//fun main() {
fun main17() {
    val pool = Executors.newScheduledThreadPool(2)
    pool.scheduleAtFixedRate({
        println("${System.currentTimeMillis()} ${Thread.currentThread().id}")
        Thread.sleep(2000)
    }, 0, 1, TimeUnit.SECONDS)

    pool.scheduleWithFixedDelay({
        println("${System.currentTimeMillis()} ${Thread.currentThread().id}")
        Thread.sleep(Random(1).nextLong(2000))
    }, 0, 1, TimeUnit.SECONDS)
}
/*
    1) накинуть тредов
    2) неограниченная очередь
    3) ограниченная очередь
        3а) отказываться от новой задачи
        3б) evict oldest - удалить самую старую
        3в) evict youngest – удалить самую новую
        ===
        3г) отказываться от наименее приоритетной - не встречал
        3д) отказаться от текущей – не встречал
        3е) отказаться от всех – не всречал
    [5, 6, 7, 8] FIFO
    T1 3
    T2 4
    [1, 2]
    mvn dependency:get -DremoteRepositories=http://repo1.maven.org/maven2/ -DgroupId=org.jetbrains.kotlinx -DartifactId=kotlinx-coroutines-core -Dversion=1.0.0 -Dtransitive=false
 */

fun main23() {
// fun main() {
    val queue = LinkedBlockingQueue<Runnable>(10)
    val pool = ThreadPoolExecutor(
        2, 2,
        0L, TimeUnit.MILLISECONDS,
        queue
    )

    (1..20).map { i ->
        try {
            pool.submit(Callable {
                println("Thread ${Thread.currentThread().id} executing $i")
                Thread.sleep(1000)
                return@Callable i
            })
        } catch (e: Exception) {
            println("Task $i rejected")
            throw e
        }
    }.mapIndexed { i, future ->
        try {
            println("Future $i: result ${future.get()}")
        } catch (e: Exception) {
            println("Future $i: $e")
        }
    }
    pool.shutdown()
}
