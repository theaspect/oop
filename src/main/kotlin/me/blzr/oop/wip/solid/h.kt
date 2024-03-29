package me.blzr.oop.wip.solid

import java.util.*

/**
 * SOLID principles:
 *
 * Single responsibility
 * Open-closed
 * Liskov's substitution
 * Interface segregation
 * Dependency inversion (класс не создаёт свои зависимости) / Dependency Injection (Зависимости в класс передаются/инжектятся через конструктор или сеттеры)
 */

class GenerateReport {
    fun generate(format: Format, dbnum: Int, report: Report, dateStart: Date, dateEnd: Date) {
        val dbData = when (dbnum) {
            1 -> selectDataFromDb1(dateStart, dateEnd)
            2 -> selectDataFromDb2(dateStart, dateEnd)
            else -> throw IllegalArgumentException("Incorrect db")
        }

        val transformedData =
            when (report) {
                Report.ATTENDANCE -> transformDataAttendance(dbData)
                Report.SCORE -> transformDataScore(dbData)
            }

        when (format) {
            Format.XLS -> generateXlsReport(transformedData, "report${dateStart}.xls")
            Format.HTML -> generateHtmlReport(transformedData, "report${dateStart}.html")
        }
    }

    private fun selectDataFromDb1(dateStart: Date, dateEnd: Date): List<DbRow> {
        // Used to query data
        val connectToDb = connectToDb1()

        return listOf()
    }

    private fun selectDataFromDb2(dateStart: Date, dateEnd: Date): List<DbRow> {
        // Used to query data
        val connectToDb = connectToDb2()

        return listOf()
    }

    private fun connectToDb1(): DBConnection {
        return DBConnection()
    }

    private fun connectToDb2(): DBConnection {
        return DBConnection()
    }

    private fun transformDataAttendance(srcData: List<DbRow>): List<ReportRow> {
        return listOf()
    }

    private fun transformDataScore(srcData: List<DbRow>): List<ReportRow> {
        return listOf()
    }

    private fun generateXlsReport(srcData: List<ReportRow>, filename: String) {

    }

    private fun generateHtmlReport(srcData: List<ReportRow>, filename: String) {

    }
}

enum class Format {
    XLS,
    HTML
}

enum class Report {
    ATTENDANCE, SCORE
}

class DBConnection
class DbRow
class ReportRow

class AttendanceDbRow
class ScoreDbRow

class AttendanceReportRow
class ScoreReportRow


// Concrete class
class Db1AttendanceXlsReportService {
    val dataSelector = Db1AttendanceDataSelector()
    val attendanceTransformer = AttendanceTransformer()
    val reportFormatter = XlsAttendanceReportFormatter()

    fun generate(dateStart: Date, dateEnd: Date, filename: String) {
        val data = dataSelector.selectDataFromDb1(dateStart, dateEnd)
        val report = attendanceTransformer.transformDataAttendance(data)
        reportFormatter.format(report, filename)
    }
}

class Db2ScoreHtmlReportService {
    val dataSelector = Db2ScoreDataSelector()
    val transformer = ScoreTransformer()
    val reportFormatter = HtmlScoreReportFormatter()

    fun generate(dateStart: Date, dateEnd: Date, filename: String) {
        val data = dataSelector.selectDataFromDb2(dateStart, dateEnd)
        val report = transformer.transformDataScore(data)
        reportFormatter.format(report, filename)
    }
}

class Db1AttendanceDataSelector {
    fun selectDataFromDb1(dateStart: Date, dateEnd: Date): List<AttendanceDbRow> {
        // Used to query data
        val connectToDb = connectToDb1()

        return listOf()
    }

    private fun connectToDb1(): DBConnection {
        return DBConnection()
    }
}

class Db2ScoreDataSelector {
    fun selectDataFromDb2(dateStart: Date, dateEnd: Date): List<ScoreDbRow> {
        // Used to query data
        val connectToDb = connectToDb2()

        return listOf()
    }

    private fun connectToDb2(): DBConnection {
        return DBConnection()
    }
}

class AttendanceTransformer {
    fun transformDataAttendance(srcData: List<AttendanceDbRow>): List<AttendanceReportRow> {
        return listOf()
    }
}

class ScoreTransformer {
    fun transformDataScore(srcData: List<ScoreDbRow>): List<ScoreReportRow> {
        return listOf()
    }
}

class XlsAttendanceReportFormatter {
    fun format(srcData: List<AttendanceReportRow>, filename: String) {}
}

class HtmlScoreReportFormatter {
    fun format(srcData: List<ScoreReportRow>, filename: String) {}
}

// Abstract classes

abstract class AbstractDbRow
class AAttendaceDbRow : AbstractDbRow()
class AScoreDbRow : AbstractDbRow()

abstract class AbstractReportRow
class AAttendaceReportRow : AbstractReportRow()
class AScoreReportRow : AbstractReportRow()

abstract class AbstractDataSelector {
    abstract fun selectDataFromDb(dateStart: Date, dateEnd: Date): List<AbstractDbRow>
    fun connectToDb(): DBConnection = DBConnection()
}

class Db1DataSelector : AbstractDataSelector() {
    override fun selectDataFromDb(dateStart: Date, dateEnd: Date): List<AAttendaceDbRow> {
        TODO("Not yet implemented")
    }
}

abstract class AbstractTransformer {
    abstract fun transformData(srcData: List<AbstractDbRow>): List<AbstractReportRow>
}

class AAttendanceTransformer : AbstractTransformer() {
    // override fun transformData(srcData: List<AAttendaceDbRow>): List<AAttendaceReportRow> { // Ошибка более строгий тип для контравариантности

    override fun transformData(srcData: List<AbstractDbRow>): List<AAttendaceReportRow> {
        TODO("Not yet implemented")
    }
}

abstract class AbstractFormatter {
    abstract fun format(srcData: List<AbstractReportRow>, filename: String)
}

class AAttendanceXlsFormatter : AbstractFormatter() {
    override fun format(srcData: List<AbstractReportRow>, filename: String) {
        TODO("Not yet implemented")
    }
}

class AScoreHtmlFormatter : AbstractFormatter() {
    override fun format(srcData: List<AbstractReportRow>, filename: String) {
        TODO("Not yet implemented")
    }

}

class AbstractReportService(
    val dataSelector: AbstractDataSelector,
    val transformer: AbstractTransformer,
    val formatter: AbstractFormatter
) {
    fun generate(dateStart: Date, dateEnd: Date, filename: String) {
        val data = dataSelector.selectDataFromDb(dateStart, dateEnd)
        val report = transformer.transformData(data)
        formatter.format(report, filename)
    }
}

fun main21() {
    val reportService = AbstractReportService(
        dataSelector = Db1DataSelector(),
        transformer = AAttendanceTransformer(),
        formatter = AAttendanceXlsFormatter()
    )
    reportService.generate(Date(), Date(), "Attendance.xls")

    val reportServiceError = AbstractReportService(
        dataSelector = Db1DataSelector(),
        transformer = AAttendanceTransformer(),
        formatter = AScoreHtmlFormatter() //  Компилятор нас не защищает от передачи неправильного типа данных
    )
    reportServiceError.generate(Date(), Date(), "Score.xls")
}

// Зависимость между типами

abstract class GenericDataSelector<DB : AbstractDbRow> {
    abstract fun selectDataFromDb(dateStart: Date, dateEnd: Date): List<DB>
    fun connectToDb(): DBConnection = DBConnection()
}

class GDb1DataSelector : GenericDataSelector<AAttendaceDbRow>() {
    override fun selectDataFromDb(dateStart: Date, dateEnd: Date): List<AAttendaceDbRow> {
        TODO("Not yet implemented")
    }
}

// Дженерики можно применять с интерфейсами
interface GenericTransformer<DB : AbstractDbRow, RES : AbstractReportRow> {
    fun transformData(srcData: List<DB>): List<RES>
}

class GAttendanceTransformer : GenericTransformer<AAttendaceDbRow, AAttendaceReportRow> {
    override fun transformData(srcData: List<AAttendaceDbRow>): List<AAttendaceReportRow> {
        TODO("Not yet implemented")
    }
}

// Дженерики можно применять с обычными классами
open class GenericFormatter<RES : AbstractReportRow> {
    open fun format(srcData: List<RES>, filename: String) {} // Пустая реализация, метод который ничего не делает
}

class GAttendanceXlsFormatter : GenericFormatter<AAttendaceReportRow>() {
    override fun format(srcData: List<AAttendaceReportRow>, filename: String) {
        TODO("Not yet implemented")
    }
}

class GScoreHtmlFormatter : GenericFormatter<AScoreReportRow>() {
    override fun format(srcData: List<AScoreReportRow>, filename: String) {
        TODO("Not yet implemented")
    }
}

class GenericReportService<DB : AbstractDbRow, RES : AbstractReportRow>(
    val dataSelector: GenericDataSelector<DB>,
    val transformer: GenericTransformer<DB, RES>,
    val formatter: GenericFormatter<RES>
) {
    fun generate(dateStart: Date, dateEnd: Date, filename: String) {
        val data = dataSelector.selectDataFromDb(dateStart, dateEnd)
        val report = transformer.transformData(data)
        formatter.format(report, filename)
    }
}

fun main22() {
    val reportService = GenericReportService(
        dataSelector = GDb1DataSelector(),
        transformer = GAttendanceTransformer(),
        formatter = GAttendanceXlsFormatter()
    )
    reportService.generate(Date(), Date(), "Attendance.xls")

//    val reportServiceError = GenericReportService(
//        dataSelector = GDb1DataSelector(),
//        transformer = GAttendanceTransformer(),
//        formatter = GScoreHtmlFormatter() // Компилятор нам не дает подставить класс неправильными типами
//    )
//    reportServiceError.generate(Date(), Date(), "Score.xls")
}

// Артём абрстракт
// Абстракт и Интерфейс

interface MyInterface {
    // Только описание методов без реализации
    // Мы не можем создать экземпляр интерфейса
    //  потому что методы не реализованы
    // Не может быть конструктора
    // Не может быть полей класса
    fun transform(string: String): String
}

abstract class MyAbstract(val echo: String) : MyInterface {
    // Может содержать реализацию методов
    // Мы не можем создать экземпляр абстрактного класса
    //  потому что методы не реализованы
    // У абстрактного класса может быть конструктор

    val xxx = "XXX"

    // Это метод закрыт для изменения (Open-Closed)
    override fun transform(string: String): String {
        return "${prefix()} $echo: $string ${suffix()}"
    }

    // Вот эти два метода доступны для расширения (Open-Closed)
    open fun prefix(): String = "" // Метод который можно не преопределять, у него есть поведение по-умолчанию
    abstract fun suffix(): String // Метод который мы обязаны переопределить

    open fun abs() = "I'm abstract"
}

class Question : MyAbstract("Echo") {
    override fun prefix(): String = "!!!"

    override fun suffix(): String = "???"

    override fun abs(): String = "I'm Question"
}

class Quote : MyAbstract("Echo") {
    override fun prefix(): String = "'''"

    override fun suffix(): String = "\"\"\""
}

//MyInterface
//    MyAbstract
//        Question
//        Quote

fun main() {
    val a: MyInterface = Question()
    val b: MyAbstract = Quote()

    println("1." + a.transform("I'm Question"))
    println("2." + b.transform("I'm Quote"))

    println("3." + b.prefix())
    println("4." + (a as Question).suffix())
    println("5." + (a as MyAbstract).suffix())
    // println("6." + (a as Quote).suffix()) // com.Question cannot be cast to com.Quote

    // Переопределяет I'm question
    val qe1: MyAbstract = Question()
    val qe2: Question = Question()
    val qe3: MyAbstract = qe2

    // Не переопределяет I'm abstract
    val qu1: MyAbstract = Quote()
    val qu2: Quote = Quote()
    val qu3: MyAbstract = qu2

    val x: Any = Quote()
    val y: Quote = a as Quote

    println("7." + qe1.abs())
    println("8." + qe2.abs())
    println("9." + qe3.abs())

    println("10." + qu1.abs())
    println("11." + qu2.abs())
    println("12." + qu3.abs())
}

// Liskov's substitution

// Поведение функции не должно меняться если я передам Quote или Question
fun myFun(a: MyInterface) {}

// a, b, α, β
// Квадрат          a=b α=β
// Прямоугольник        α=β
// Параллелограмм
// Ромб             a=b

// Абстрактная фигура
//      Квадрат
//      Прямоугольник
//      Параллелограмм
//      Ромб


open class Parallelogramm {
    open fun setA(a: Int) {}
    open fun setB(a: Int) {}
    open fun setAlpha(a: Int) {}
    open fun setBeta(a: Int) {}

    open fun getA(): Int = 0
    open fun getB(): Int = 0
    open fun getAlpha(): Int = 0
    open fun getBeta(): Int = 0
}

open class Rectangle : Parallelogramm() {
    override fun setA(a: Int) {}
    override fun setB(a: Int) {}
    override fun setAlpha(a: Int) {}
}

class Square : Rectangle() {
    override fun setA(a: Int) {}
    override fun getA(): Int = 0

    // override fun setB(a: Int){throw Exception()}
}

class Diamond {
    fun setA(a: Int) {}
    fun setAlpha(a: Int) {}
    fun setBeta(a: Int) {}
}

fun inheritance() {
    val s: Square = Square()
    s.setA(1)

    val p: Parallelogramm = s
    p.setB(2)

    s.getA() // ?
}
