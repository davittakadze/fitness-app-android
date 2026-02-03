package com.example.domain.repository.auth

interface AuthRepository {
    fun isLoggedIn(): Boolean
}