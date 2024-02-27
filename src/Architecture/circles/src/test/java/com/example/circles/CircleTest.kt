package com.example.circles

import org.junit.Assert
import org.junit.Test

class CircleTest {
    fun Circle.print() = {
        println("x = $x, y = $y, r = $r")
    }

    @Test
    fun createCircleCase1() {
        val circle = Circle.invoke(1.0, 2.0, 3.0)
        Assert.assertTrue(circle != null)
    }

    @Test
    fun createCircleCase2() {
        val circle = Circle.invoke("1.0 2.0 3.0")
        Assert.assertTrue(circle != null)
    }

    @Test
    fun createCircleCase3() {
        val circle = Circle.invoke("1 2 3")
        Assert.assertTrue(circle != null)
    }

    @Test
    fun noCreateCircleCase1() {
        val circle = Circle.invoke("1 2 -3")
        Assert.assertTrue(circle == null)
    }

    @Test
    fun noCreateCircleCase2() {
        val circle = Circle.invoke("1 2")
        Assert.assertTrue(circle == null)
    }

    @Test
    fun noCreateCircleCase3() {
        val circle = Circle.invoke("q w z")
        Assert.assertTrue(circle == null)
    }

    @Test
    fun noCreateCircleCase4() {
        val circle = Circle.invoke(1.0, 2.0, -3.0)
        Assert.assertTrue(circle == null)
    }

    @Test
    fun twoCirclesIsIntersect() {
        val circle1 = Circle.invoke("0 0 3")
        val circle2 = Circle.invoke(2.0, 2.0, 3.0)
        Assert.assertTrue(circle1?.getRelation(circle2) == Relation.INTERSECT)
    }

    @Test
    fun twoCirclesIsNoIntersect() {
        val circle1 = Circle.invoke("0 0 3")
        val circle2 = Circle.invoke(7.0, 0.0, 3.0)
        Assert.assertTrue(circle1?.getRelation(circle2) == Relation.NO_INTERSECT)
    }

    @Test
    fun twoCirclesIsTouch() {
        val circle1 = Circle.invoke("0 0 3")
        val circle2 = Circle.invoke(6.0, 0.0, 3.0)
        Assert.assertTrue(circle1?.getRelation(circle2) == Relation.TOUCH)
    }

    @Test
    fun oneCircleInsideOtherCircle() {
        val circle1 = Circle.invoke("0 0 3")
        val circle2 = Circle.invoke(0.0, 0.0, 2.0)
        Assert.assertTrue(circle1?.getRelation(circle2) == Relation.INSIDE)
    }

    @Test
    fun incorrectCircle() {
        val circle1 = Circle.invoke("0 0 3")
        val circle2 = Circle.invoke(2.0, 2.0, -3.0)
        Assert.assertTrue(circle1?.getRelation(circle2) == Relation.INCORRECT)
        Assert.assertFalse(circle2?.getRelation(circle1) == Relation.INCORRECT ?: false)
    }
}