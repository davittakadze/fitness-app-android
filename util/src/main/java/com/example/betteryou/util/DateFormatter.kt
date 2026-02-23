package com.example.betteryou.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.formatToString(): String {
    val day = this.dayOfMonth.toString().padStart(2, '0')
    val month = this.month.ordinal + 1
    val monthStr = month.toString().padStart(2, '0')
    val year = this.year.toString()
    return "$day/$monthStr/$year"
}

fun String?.toLocalDate(): LocalDate? {
    if (this.isNullOrBlank()) return null
    return try {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        LocalDate.parse(this, formatter)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}