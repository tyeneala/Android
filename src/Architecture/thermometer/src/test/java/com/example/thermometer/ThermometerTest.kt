package com.example.thermometer

import org.junit.Assert
import org.junit.Test

class ThermometerTest {
    @Test
    fun calculateCelsiusLower() {
        val thermometer = Thermometer.calculate(15f, Season.WINTER, MeasureUnit.CELSIUS)
        Assert.assertEquals(5f, thermometer)
    }

    @Test
    fun calculateCelsiusUpper() {
        val thermometer = Thermometer.calculate(25f, Season.WINTER, MeasureUnit.CELSIUS)
        Assert.assertEquals(-3f, thermometer)
    }

    @Test
    fun calculateCelsiusNormal() {
        val thermometer = Thermometer.calculate(22f, Season.WINTER, MeasureUnit.CELSIUS)
        Assert.assertEquals(0f, thermometer)
    }

    @Test
    fun calculateFahrenheitLower() {
        val thermometer = Thermometer.calculate(10f, Season.WINTER, MeasureUnit.FAHRENHEIT)
        Assert.assertEquals(18f, thermometer)
    }

    @Test
    fun calculateFahrenheitUpper() {
        val thermometer = Thermometer.calculate(32f, Season.WINTER, MeasureUnit.FAHRENHEIT)
        Assert.assertEquals(-18f, thermometer)
    }
}