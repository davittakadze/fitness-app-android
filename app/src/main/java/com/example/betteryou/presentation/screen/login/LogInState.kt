package com.example.betteryou.presentation.screen.login

data class LogInState(
    val email: String="",
    val password: String="",
    val isLoading:Boolean=false,
    val isPasswordVisible:Boolean=false
)

