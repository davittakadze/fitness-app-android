package com.example.betteryou.domain.repository.token

interface AuthRepository {
    fun isLoggedIn(): Boolean
}

