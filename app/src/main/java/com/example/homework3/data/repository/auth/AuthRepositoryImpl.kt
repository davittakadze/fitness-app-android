package com.example.homework3.data.repository.auth

import com.example.homework3.domain.repository.token.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

}
