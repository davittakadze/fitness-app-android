package com.example.data.repository

import com.example.betteryou.data.common.HandleFirebase
import com.example.betteryou.domain.common.Resource
import com.example.domain.repository.LogInRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private val handleFirebase: HandleFirebase,
    private val firebaseAuth: FirebaseAuth,
) : LogInRepository {
    override suspend fun logInUser(
        email: String,
        password: String,
    ): Flow<Resource<Unit>> {
        return handleFirebase.safeCall {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user

            user?.reload()?.await()

            Unit
        }
    }
}