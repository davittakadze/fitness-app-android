package com.example.betteryou.util

import java.util.Calendar

fun getStartOfDayMillis(): Long {
    val now = Calendar.getInstance()
    now.set(Calendar.HOUR_OF_DAY, 0)
    now.set(Calendar.MINUTE, 0)
    now.set(Calendar.SECOND, 0)
    now.set(Calendar.MILLISECOND, 0)
    return now.timeInMillis
}