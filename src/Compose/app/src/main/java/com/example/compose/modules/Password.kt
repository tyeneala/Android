package com.example.compose.modules

data class Data(
    val lowerCase: String = "abcdefghijklmnopqrstuvwxyz",
    val upperCase: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
    val number: String = "0123456789",
    val symbol: String = "!@$%^&*()_-;+",
    var setTypes: String = "LLUUNNSSLU"
) {
    fun getItem(ch: Char, value: Int) = when(ch) {
        'L' -> lowerCase[value % lowerCase.length]
        'U' -> upperCase[value % upperCase.length]
        'N' -> number[value % number.length]
        else -> symbol[value % symbol.length]
    }
}

object Password {
    private val data = Data()
    private val lengthPassword: Int = data.setTypes.length
    private var key = 0
    private lateinit var normalDomainName: MutableList<Char>
    private lateinit var normalKeyword: MutableList<Char>

    fun doCheckDomainName(value: String) = extractDomain(value).length > 1

    fun doCheckKeyWord(value: String) = value.trim().isNotEmpty()

    fun get(domainName: String, keyword: String): String {
        if (!doCheckDomainName(domainName))
            return "Invalid data in domain name"
        if (!doCheckKeyWord(keyword))
            return "Invalid a keyword"
        key = keyword.length
        normalDomainName = normalizationDomainName(domainName)
        normalKeyword = normalizationKeyword(keyword)
        return generate()
    }

    private fun normalizationDomainName(domainName: String): MutableList<Char> {
        var result = domainName.split("/")
            .first { it.isNotEmpty() && it.last() != ':' }
            .substringBeforeLast('.')
        if (result.length < lengthPassword) {
            repeat(lengthPassword - result.length) {
                result += 'O'
            }
        }
        val distance = key % result.length
        result += result.substring(0, distance)
        return result.reversed().toMutableList()
    }

    private fun normalizationKeyword(keyword: String): MutableList<Char> {
        val item = fun(value: Int) = if (value % 2 == 0) {
            data.getItem('L', value + key)
        } else {
            data.getItem('L', value + key / 2)
        }
        var result = keyword
        var index = 0
        while (result.length < lengthPassword) {
            result += item(result[index++].code)
        }
        return result.toMutableList()
    }

    private fun generate(): String {
        val buffer: MutableList<Char> = data.setTypes.toMutableList()
        val type = fun(value: Int): Char {
            val index = if (value % 2 == 0) 0 else buffer.lastIndex
            return buffer[index].also { buffer.removeAt(index) }
        }

        var result = ""
        repeat(buffer.size) {
            result += data.getItem(type(normalKeyword[it].code),
                normalKeyword[it].code + normalDomainName[it].code)
        }
        return result
    }

    private fun extractDomain(value: String): String {
        val result = value.split("/").filter { it.isNotEmpty() && it.last() != ':' }
        return if (result.isNotEmpty()) result[0].substringBeforeLast('.').trim() else ""
    }
}