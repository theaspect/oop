package me.blzr.oop.b.encapsulation

/**
 * Карта 10 x 10
 * Маршрут 10 горизонтально 0..9
 * Маршрут 20 вертикально 0..9
 */

const val MAP_X = 10
const val MAP_Y = 10

abstract class Bus(
    var x: Int,
    var y: Int,
    var sx: Int,
    var sy: Int,
) {
    abstract fun step()
    abstract fun getChar(): Char
}

class Map(vararg val buses: Bus) {
    fun draw() {
        val map = Array(MAP_X) { Array(MAP_Y) { ' ' } }
        for (b in buses) {
            map[b.y][b.x] = b.getChar()
        }

        println()
        for (row in map) {
            for (col in row) {
                print(col)
            }
            println("\u001B[10A \u001B[10D")
        }
    }

    fun step() {
        for (b in buses) {
            b.step()
        }
    }
}

class Bus10(x: Int, y: Int, sx: Int, sy: Int) : Bus(x, y, sx, sy) {
    override fun step() {
        x += sx

        if (x <= 0) {
            x = -x
            sx = -sx
        } else if (x >= MAP_X) {
            x = (MAP_X - 1) - (x - MAP_X)
            sx = -sx
        }
    }

    override fun getChar(): Char = if (sx > 0) 'a' else 'A'
}

class Bus20(x: Int, y: Int, sx: Int, sy: Int) : Bus(x, y, sx, sy) {
    override fun step() {
        y += sy

        if (y <= 0) {
            y = -y
            sy = -sy
        } else if (y >= MAP_Y) {
            y = (MAP_Y - 1) - (y - MAP_Y)
            sy = -sy
        }
    }

    override fun getChar(): Char = if (sx > 0) 'b' else 'B'
}

fun main() {
    val map = Map(
        Bus10(0, 0, 2, 0),
        Bus10(9, 0, -2, 0),
        Bus20(0, 0, 0, 3)
    )

    println("Press enter to step")
    while (true) {
        readln()
        map.step()
        map.draw()
    }
}
