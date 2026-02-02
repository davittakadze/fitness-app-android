package com.example.homework3.data.repository.login

import com.example.homework3.data.common.HandleFirebase
import com.example.homework3.domain.common.Resource
import com.example.homework3.domain.repository.login.LogInRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
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
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = firebaseAuth.currentUser
            user?.reload()?.await()
            if (user?.isEmailVerified != true) {
                firebaseAuth.signOut()
                throw FirebaseAuthException(
                    "EMAIL_NOT_VERIFIED",
                    "you have to verify the email!"
                ) as Throwable
            }
        }
    }
}