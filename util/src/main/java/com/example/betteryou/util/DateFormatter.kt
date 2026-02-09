package com.example.betteryou.util

import java.time.LocalDate

fun LocalDate.formatToString(): String {
    val day = this.dayOfMonth.toString().padStart(2, '0')
    val month = this.month.ordinal + 1
    val monthStr = month.toString().padStart(2, '0')
    val year = this.year.toString()

    return "$day/$monthStr/$year"
}