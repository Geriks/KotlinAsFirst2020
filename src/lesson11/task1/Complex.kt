@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import kotlin.math.pow

val rx = Regex("""(-?\d+(\.\d+)?)([+-]\d+(\.\d+)?)i""")

/**
 * Класс "комплексное число".
 *
 * Общая сложность задания -- лёгкая, общая ценность в баллах -- 8.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Конструктор из строки вида x+yi
     */
    companion object {
        fun correctString(s: String): Pair<Double, Double> {
            if (s == "") return 0.0 to 0.0
            val ans = rx.matchEntire(s) ?: throw IllegalArgumentException()
            return ans.groupValues[1].toDouble() to ans.groupValues[3].toDouble()
        }
    }

    constructor(s: String) : this(
        correctString(s).first, correctString(s).second
    )

    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex = Complex(this.re + other.re, this.im + other.im)

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex = Complex(-this.re, -this.im)

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = Complex(this.re - other.re, this.im - other.im)

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex = Complex(
        this.re * other.re - this.im * other.im,
        this.im * other.re + this.re * other.im
    )


    /**
     * Деление
     */
    operator fun div(other: Complex): Complex = Complex(
        (this.re * other.re + this.im * other.im) / (other.re.pow(2) + other.im.pow(2)),
        (this.im * other.re - this.re * other.im) / (other.re.pow(2) + other.im.pow(2))
    )

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Complex) return false
        return re == other.re && im == other.im
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String = when {
        this.im < 0 -> "$re${im}i"
        this.im > 0 -> "$re+${im}i"
        else -> "$re"
    }

    override fun hashCode(): Int {
        var result = re.hashCode()
        result = 31 * result + im.hashCode()
        return result
    }
}
