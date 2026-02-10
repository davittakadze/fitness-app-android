package com.betteryou.feature.register.data.repository

import com.betteryou.feature.register.domain.model.RegisterUserInfo
import com.betteryou.feature.register.domain.repository.RegisterRepository
import com.example.betteryou.data.common.HandleFirebase
import com.example.betteryou.domain.common.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class RegisterRepositoryImpl @Inject constructor(
    private val handleFirebase: HandleFirebase,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : RegisterRepository {

    override suspend fun registerUser(
        user: RegisterUserInfo,
        email: String,
        password: String
    ): Flow<Resource<Unit>> {
        return handleFirebase.safeCall {
            val authResult = firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()

            val userId = authResult.user?.uid
                ?: throw IllegalStateException("User creation failed, UID is null.")

            val finalUser = user.copy(userId = userId)

            firestore.collection("users").document(userId)
                .set(finalUser)
                .await()
        }
    }
}
