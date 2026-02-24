package com.example.betteryou.feature.settings.data.repository.delete_account

import com.example.betteryou.data.common.HandleFirebase
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.settings.domain.repository.delete_account.DeleteAccountRepository
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DeleteAccountRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val handleFirebase: HandleFirebase,
) : DeleteAccountRepository {

    override suspend fun deleteAccount(password:String): Flow<Resource<Unit>> {
        return handleFirebase.safeCall {

            val user = firebaseAuth.currentUser
                ?: throw Exception("User not logged in")

            val credential = EmailAuthProvider.getCredential(user.email!!, password)
            user.reauthenticate(credential).await()

            user.delete().await()

            firebaseAuth.signOut()
        }
    }
}