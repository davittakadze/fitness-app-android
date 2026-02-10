package com.example.betteryou.feature.settings.data.repository.logout

import com.example.betteryou.data.common.HandleFirebase
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.settings.domain.repository.logout.LogoutRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogoutRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val handleFirebase: HandleFirebase
) : LogoutRepository {

    override suspend fun logoutUser(): Flow<Resource<Unit>> {
        return handleFirebase.safeCall {
            val user = firebaseAuth.currentUser
            firebaseAuth.signOut()
        }
    }
}