package me.blzr.oop.c.inheritance

// Принципы SOLID
// S – single responsibility принцип единственной ответственности
//
// O – open/closed класс открыт для расширения, закрыт для изменения (с точки зрения контрактов)
//
// L – Liscov's substitution principle поведение программы не должно меняться,
//     если мы вместо родительского класса подставим потомка
//
// I – interface segregation мы не должны требовать от программы реализации избыточных методов
//
// D - Dependency inversion – сервисы не должны инстанциировать другие сервисы, а только получать через конструкторы

// Diamond inheritance
//                  дл. стороны  углы
// Четырехугольник  -            -
// Квадрат          a=b=c=d      α=β=ɣ=δ=90
// Прямоугольник    a=c b=d      α=β=ɣ=δ=90
// Параллелограмм   a=c b=d      α=ɣ β=δ
// Ромб             a=b=c=d      α=ɣ β=δ
//
// A совместим с B
//
// A\B кв пр пг ро
// кв   +  +  +  +
// пр   -  +  +  -
// пг   -  -  +  -
// ро   -  -  +  +

abstract class Quadrilateral(
    val a: Float,
    val b: Float,
    val c: Float,
    val d: Float,

    val alpha: Float,
    val beta: Float,
    val gamma: Float,
    val delta: Float,
) {
    override fun toString(): String =
        "Quadrilateral(a=$a, b=$b, c=$c, d=$d, " +
                "alpha=$alpha, beta=$beta, gamma=$gamma, delta=$delta)"
}

/**
 * Все стороны равны
 * Все углы 90 градусов
 */
open class Square(len: Float) : Quadrilateral(
    a = len, b = len, c = len, d = len,
    alpha = 90f, beta = 90f, gamma = 90f, delta = 90f,
)

/**
 * Все стороны равны
 * Сумма соседних углов 180
 */
open class Rhombus(len: Float, angle: Float) : Quadrilateral(
    a = len, b = len, c = len, d = len,
    alpha = angle, beta = 180f - angle, gamma = angle, delta = 180 - angle,
)

/**
 * Стороны попарно равны
 * Все углы 90 градусов
 */
open class Rectangle(lenA: Float, lenB: Float) : Quadrilateral(
    a = lenA, b = lenB, c = lenA, d = lenB,
    alpha = 90f, beta = 90f, gamma = 90f, delta = 90f,
)

/**
 * Стороны попарно равны
 * Сумма соседних углов 180 градусов
 */
open class Parallelogram(lenA: Float, lenB: Float, angle: Float) : Quadrilateral(
    a = lenA, b = lenB, c = lenA, d = lenB,
    alpha = angle, beta = 180f - angle, gamma = angle, delta = 180 - angle,
)

fun main() {
    val s = Square(len = 10f)
    val rh = Rhombus(len = 10f, angle = 20f)
    val rec = Rectangle(lenA = 10f, lenB = 20f)
    val p = Parallelogram(lenA = 10f, lenB = 20f, angle = 30f)
}
