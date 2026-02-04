package com.betteryou.feature.register.presentation.register

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val password2: String = "",
    val isPassword1Visible:Boolean=false,
    val isPassword2Visible:Boolean=false,
    val isLoading:Boolean=false
)
