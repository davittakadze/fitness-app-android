package com.example.betteryou.data.exception

class EmailNotVerifiedException : Exception(AuthErrorCodes.VERIFY_EMAIL_FIRST)