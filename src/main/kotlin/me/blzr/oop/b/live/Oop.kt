/**
 * Карта 10 x 10
 * Маршрут 10 горизонтально 0..9
 * Маршрут 20 вертикально 0..9
 */

val MAP_X = 10
val MAP_Y = 10

abstract class Bus(
    var x: Int,
    var y: Int,
    protected var sx: Int,
    protected var sy: Int,
) {
    abstract fun step()
    abstract fun getChar(): Char
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

    override fun getChar(): Char = if (sy > 0) 'b' else 'B'
}

class Bus30(x: Int, y: Int, sx: Int, sy: Int) : Bus(x, y, sx, sy) {
    override fun step() {
        TODO("Not yet implemented")
    }

    override fun getChar(): Char {
        TODO("Not yet implemented")
    }

}

class Map(vararg val buses: Bus) {
    fun step() {
        for (bus in buses) {
            bus.step()
        }
    }

    fun draw() {
        val map = Array(MAP_Y) { Array(MAP_X) { '.' } }

        for (bus in buses) {
            map[bus.y][bus.x] = bus.getChar()
        }

        println()
        for (row in map) {
            for (col in row) {
                print(col)
            }
            println()
        }
    }
}

fun main() {
    val map = Map(
        Bus10(0, 0, 2, 0),
        Bus10(9, 0, -2, 0),
        Bus20(0, 0, 0, 3),
        Bus30(0, 0, 0, 0),
    )

    println("Press any key")
    while (true) {
        readln()
        map.step()
        map.draw()
    }
}
