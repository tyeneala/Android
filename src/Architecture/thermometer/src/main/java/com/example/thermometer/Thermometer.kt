package com.example.thermometer

enum class Season {
    WINTER { override val range = 20f .. 22f},
    SUMMER { override val range = 22f .. 25f};
    abstract val range: ClosedFloatingPointRange<Float>
}

enum class MeasureUnit {
    CELSIUS {
        override fun modified(celsius: Float) = celsius
        override val ratio = 1f
    },
    KELVIN {
        override fun modified(celsius: Float) = celsius + 273.15f
        override val ratio = 1f
    },
    FAHRENHEIT {
        override fun modified(celsius: Float) = 32 + celsius * 1.8f
        override val ratio = 1.8f
    };
    abstract fun modified(celsius: Float): Float
    abstract val ratio: Float
    override fun toString() = name.lowercase().replaceFirstChar { it.uppercase() }
}

object Thermometer {
    fun calculate(value: Float, season: Season, unit: MeasureUnit): Float {
        val r = season.range
        return if (value in r) 0f
            else if (value < r.start) unit.ratio * (r.start - value)
                else unit.ratio * (r.endInclusive - value)
    }
}