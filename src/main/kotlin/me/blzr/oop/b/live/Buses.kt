package me.blzr.oop.b.live

/**
 * Карта 10 x 10
 * Маршрут 10 горизонтально 0..9
 * Маршрут 20 вертикально 0..9
 */

val mapX = 10
val mapY = 10
val buses = 3

fun step(routes: Array<Int>, coordsX: Array<Int>, speedX: Array<Int>, coordsY: Array<Int>, speedY: Array<Int>) {
    for (i in 0..<buses) {
        when (routes[i]) {
            10 -> route10(i, coordsX, speedX)
            20 -> route20(i, coordsY, speedY)
            30 -> TODO()
        }
    }
}

fun route10(i: Int, coordsX: Array<Int>, speedX: Array<Int>) {
    coordsX[i] += speedX[i]

    if (coordsX[i] <= 0) {
        coordsX[i] = -coordsX[i]
        speedX[i] = -speedX[i]
    } else if (coordsX[i] >= mapX) {
        coordsX[i] = (mapX - 1) - (coordsX[i] - mapX)
        speedX[i] = -speedX[i]
    }
}

fun route20(i: Int, coordsY: Array<Int>, speedY: Array<Int>) {
    coordsY[i] += speedY[i]

    if (coordsY[i] <= 0) {
        coordsY[i] = -coordsY[i]
        speedY[i] = -speedY[i]
    } else if (coordsY[i] >= mapY) {
        coordsY[i] = (mapY - 1) - (coordsY[i] - mapY)
        speedY[i] = -speedY[i]
    }
}

fun route30() {
    TODO()
}

fun draw(routes: Array<Int>, coordsX: Array<Int>, speedX: Array<Int>, coordsY: Array<Int>, speedY: Array<Int>) {
    val map = Array(mapY) { Array(mapX) { '.' } }

    for (i in 0..<buses) {
        val r = routes[i]
        val x = coordsX[i]
        val y = coordsY[i]
        val sx = speedX[i]
        val sy = speedY[i]

        when {
            r == 10 && sx > 0 -> map[y][x] = 'a'
            r == 10 && sx < 0 -> map[y][x] = 'A'
            r == 20 && sy > 0 -> map[y][x] = 'b'
            r == 20 && sy < 0 -> map[y][x] = 'B'
            r == 30 -> TODO()
        }
    }

    println()
    for (row in map) {
        for (col in row) {
            print(col)
        }
        println()
    }
}

fun main() {
    val routes = arrayOf(10, 10, 20)
    val coordsX = arrayOf(0, 9, 0)
    val coordsY = arrayOf(0, 0, 0)
    val speedX = arrayOf(2, -2, 0)
    val speedY = arrayOf(0, 0, 3)

    println("Press any key")
    while (true) {
        readln()
        step(routes, coordsX, speedX, coordsY, speedY)
        draw(routes, coordsX, speedX, coordsY, speedY)
    }
}
