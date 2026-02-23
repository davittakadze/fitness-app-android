package com.example.betteryou.util

fun Long.formatToDateString(): String {
    val instant = java.time.Instant.ofEpochMilli(this)
    val dateTime = java.time.LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())

    val formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    return dateTime.format(formatter)
}