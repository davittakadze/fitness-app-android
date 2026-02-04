package com.betteryou.feature.register.data.repository

import com.betteryou.feature.register.domain.repository.RegisterRepository
import com.example.betteryou.data.common.HandleFirebase
import com.example.betteryou.domain.common.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val handleFirebase: HandleFirebase,
    private val firebaseAuth: FirebaseAuth,
) : RegisterRepository {
    override suspend fun registerUser(
        email: String,
        password: String,
    ): Flow<Resource<Unit>> {
        return handleFirebase.safeCall {
            firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()
            firebaseAuth.currentUser
                ?.sendEmailVerification()
                ?.await()
            firebaseAuth.signOut()
        }
    }
}