package com.seemsnerdy.test

import java.io.File












fun main(args: Array<String>) {
    val file = File("../input")
    val result = file.useLines { lines ->
        lines.map(::mapInputToPassword)
            .filter { (rule, s) -> rule.isValid(s) }
            .count()
    }
    println("Result: $result")
}


















fun mapInputToPassword(s: String): Pair<PasswordRule, String> = s
    .split(':')
    .let { PasswordRule(it[0]) to it[1].trim() }
















class PasswordRule(s: String) {
    private val from: Int
    private val to: Int
    private val letter: Char

    init {
        val a = s.split('-', ' ')
        from = a[0].toInt()
        to = a[1].toInt()
        letter = a[2].toCharArray()[0]
    }

    fun isValid(password: String): Boolean =
        password.toCharArray()
            .filter { c -> c == letter }
            .size
            .let { s -> s in from..to }
}



