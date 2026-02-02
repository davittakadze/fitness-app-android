package com.example.homework3.data.repository.register

import com.example.homework3.data.common.HandleFirebase
import com.example.homework3.domain.common.Resource
import com.example.homework3.domain.repository.register.RegisterRepository
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