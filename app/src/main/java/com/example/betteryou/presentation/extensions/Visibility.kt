package com.example.betteryou.presentation.extensions

import android.view.View

fun View.setVisible(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}