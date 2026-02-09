package com.example.betteryou.util

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun calculateAge(birthDate: java.time.LocalDate): Int {
    val today = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

    var age = today.year - birthDate.year

    if (
        today.month < birthDate.month ||
        (today.month == birthDate.month &&
                today.dayOfMonth < birthDate.dayOfMonth)
    ) {
        age--
    }

    return age
}