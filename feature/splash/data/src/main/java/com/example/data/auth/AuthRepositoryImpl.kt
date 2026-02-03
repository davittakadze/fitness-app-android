package com.example.data.auth

import com.example.domain.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

}
