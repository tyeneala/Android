package com.example.primenumbers

import org.junit.Assert
import org.junit.Test

class PrimeNumbersTest {
    @Test
    fun addition_isCorrect() {
        val list = PrimeNumbers.calculate(123, Mode.LOWER)
        Assert.assertEquals(3, list.size)
        Assert.assertEquals("3", list[0].first)
        Assert.assertEquals(" - prime", list[0].second)
        Assert.assertEquals("32", list[1].first)
        Assert.assertEquals("", list[1].second)
        Assert.assertEquals("321", list[2].first)
        Assert.assertEquals("", list[2].second)
    }
}