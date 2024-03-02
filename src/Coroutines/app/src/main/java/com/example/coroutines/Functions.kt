package com.example.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive
import java.math.BigDecimal as Decimal
import kotlin.math.abs
import kotlin.math.pow

const val DEFAULT_LOGARITHM_BASE = 2.7182818284
const val DEFAULT_DEGREE_BASE = 2
const val CALCULATION_ACCURACY = 1e-6

fun CoroutineScope.calculateFactorial(number: Int): String {
    if (number <= 0) return "n/a"
    var result = Decimal(1)
    for (i in 2..number) {
        if (!isActive) break
        result *= Decimal(i)
    }
    return if (isActive) result.toString() else "cancel"
}

fun CoroutineScope.calculateLogarithm(number: Int,
                                      base: Double = DEFAULT_LOGARITHM_BASE): String {
    if (number <= 0) return "n/a"
    var end = 0.0
    while (base.pow(end) <= number) { end += 1.0 }
    var start = end - 1.0
    val middle = fun() = (start + end) * 0.5
    while (isActive && abs(base.pow(middle()) - number) > CALCULATION_ACCURACY) {
        if (base.pow(middle()) > number)
            end = middle()
        else
            start = middle()
    }
    return if (isActive) middle().toString() else "cancel"
}

fun calculatePower(number: Int, base: Int = DEFAULT_DEGREE_BASE): String {
    return (1..base).toList().fold(Decimal(1)) { accumulator: Decimal, _: Int ->
        Decimal(number).multiply(accumulator)
    }.toString()
}

fun CoroutineScope.calculateRoot(number: Double, base: Int = DEFAULT_DEGREE_BASE): String {
    if (number <= 0) return "n/a"
    var result = number
    val tmp = fun() = power(result, base)
    var i = 0
    while (isActive && abs(tmp() - number) > CALCULATION_ACCURACY) {
        result = 1.0 / base * ((base - 1) * result + number / power(result, base - 1))
        i++
    }
    return if (isActive) result.toString() else "cancel"
}

fun power(number: Double, base: Int): Double {
    require(base >= 0) { "n must be positive" }
    return (1 .. base).toList().fold(1.0) { acc: Double, _: Int ->
        val result = acc * number
        result
    }
}
