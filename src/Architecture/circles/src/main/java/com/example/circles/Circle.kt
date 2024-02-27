package com.example.circles

enum class Relation { INCORRECT, INTERSECT, NO_INTERSECT, TOUCH, INSIDE }

class Circle private constructor(val x: Double, val y: Double, val r: Double) {
    companion object {
        operator fun invoke(x: Double, y: Double, r: Double): Circle? {
            return if (r >= 0) Circle(x, y, r) else null
        }
        operator fun invoke(str: String): Circle? {
            val parameters = getArrayArguments(str)
            if (parameters == null || parameters[2] < 0)
                return null
            return Circle(parameters[0], parameters[1], parameters[2])
        }
        private fun getArrayArguments(str: String): List<Double>? {
            val number = "[-+]?[0-9]+[.]?[0-9]?"
            if (str.contains("^$number $number $number$".toRegex())) {
                return str.split(' ', limit = 3).map { it.toDouble() }
            }
            return null
        }
    }
}

fun Circle.getRelation(other: Circle?): Relation {
    if (other == null)
        return Relation.INCORRECT
    val distance = kotlin.math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y))
    if ((r + other.r) < distance) return Relation.NO_INTERSECT
    if ((r + other.r) == distance) return Relation.TOUCH
    if (distance <= kotlin.math.abs(other.r - r)) return  Relation.INSIDE
    return Relation.INTERSECT
}
