package com.example.betteryou.feature.menu.data.repository

import com.example.betteryou.data.common.HandleFirebase
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.menu.domain.repository.GoogleSignUpRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoogleSignUpRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val handleFirebase: HandleFirebase
) : GoogleSignUpRepository {
    override suspend fun signInWithGoogle(idToken: String): Flow<Resource<Unit>> {
        return handleFirebase.safeCall {
            val credential = GoogleAuthProvider
                .getCredential(idToken, null)

            firebaseAuth
                .signInWithCredential(credential)
                .await()
        }
    }

    override fun signOut() {
        //...
    }
}