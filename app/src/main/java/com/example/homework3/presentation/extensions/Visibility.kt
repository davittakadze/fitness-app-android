package com.example.homework3.presentation.extensions

import android.view.View

fun View.setVisible(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}