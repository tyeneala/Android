package com.example.coroutines

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.log2
import kotlin.math.pow
import kotlin.math.sqrt

class FunctionsTest {
    @Test
    fun factorialTests() = runBlocking{
        assertEquals("n/a", calculateFactorial(-1))
        assertEquals("n/a", calculateFactorial(0))
        assertEquals("1", calculateFactorial(1))
        assertEquals("2", calculateFactorial(2))
        assertEquals("6", calculateFactorial(3))
        assertEquals("24", calculateFactorial(4))
        assertEquals("120", calculateFactorial(5))
        assertEquals("479001600", calculateFactorial(12))
    }

    @Test
    fun LogarithmTests() = runBlocking{
        assertEquals("n/a", calculateLogarithm(-1))
        assertEquals("n/a", calculateLogarithm(0))
        for (i in 1..1000) {
            assertTrue(abs(calculateLogarithm(i).toDouble() - ln(i.toDouble())) < 1e-6)
            assertTrue(abs(calculateLogarithm(i, 2.0).toDouble() - log2(i.toDouble())) < 1e-6)
            assertTrue(abs(calculateLogarithm(i, 10.0).toDouble() - log10(i.toDouble())) < 1e-6)
        }
    }

    @Test
    fun rootTests() = runBlocking{
        assertEquals("n/a", calculateRoot(-1.0))
        assertEquals("n/a", calculateRoot(0.0))
        for (i in 1..1000) {
            assertTrue(abs(calculateRoot(i.toDouble()).toDouble() - sqrt(i.toDouble())) < 1e-6)
            assertTrue(abs(calculateRoot(i.toDouble(), 3).toDouble() - i.toDouble().pow(1.0/3)) < 1e-6)
        }
    }

    @Test
    fun powerTests() = run {
        for (i in 1..50) {
            for (j in 1..50) {
                assertTrue(abs(calculatePower(i, j).toDouble() - i.toDouble().pow(j)) < 1e-6)
            }
        }
    }

}