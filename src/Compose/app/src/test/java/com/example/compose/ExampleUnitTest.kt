package com.example.compose

import com.example.compose.modules.Data
import com.example.compose.modules.Password
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun shouldBeTrueCheck() {
        val set = mapOf(
            "http://mail.ru" to "123",
            "mail.ru" to "a",
            "mail" to "a b 2",
            "сайт.рф" to "qwerty.123",
            "ru.google.com" to "пароль",
            "google." to "пароль",
            "http://xn--c6h.com/" to "пароль",
            "https://nic.谷歌/" to "пароль"
        )
        set.forEach {
            assertTrue(Password.doCheckDomainName(it.key))
            assertTrue(Password.doCheckKeyWord(it.value))
        }
    }
    @Test
    fun shouldBeFalseCheck() {
        val set = mapOf(
            "http://" to "",
            ".ru" to " ",
            "mail:" to "",
            "" to "",
            " " to " "
        )
        set.forEach {
            assertFalse(Password.doCheckDomainName(it.key))
            assertFalse(Password.doCheckKeyWord(it.value))
        }
    }

    @Test
    fun shouldBeGenerated() {
        val password1 = Password.get("http://mail.ru", "123")
        val password2 = Password.get("mail.ru", "123")
        assertEquals(10, password1.length)
        assertTrue(password1 == password2)
        println(password1)
        println(password1)
    }

    // tests for Data()
}