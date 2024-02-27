package com.example.primenumbers

enum class Mode { LOWER, UPPER }

object PrimeNumbers {

    fun calculate(number: Int, mode: Mode): List<Pair<String, String>> {
        var str = kotlin.math.abs(number).toString()
        if (mode == Mode.LOWER)
            str = str.reversed()
        return (str.indices).map {
            str.subSequence(0, it + 1).toString() to
                    if (isPrime(str.subSequence(0, it + 1).toString().toInt())) " - prime" else ""
        }
    }

    private fun isPrime(n: Int): Boolean {
        if (n < 2 || (n % 2 == 0 && n != 2))
            return false
        var result = true
        val limit = kotlin.math.ceil(kotlin.math.sqrt(n.toDouble())).toInt()
        for (i in 2L..limit) {
            if (n % i == 0L) {
                result = false
                break
            }
        }
        return result
    }
}