package com.example.betteryou.data.repository

import com.example.betteryou.domain.repository.GetUserIdRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class GetUserIdRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : GetUserIdRepository {
    override fun getUserId(): String? {
        return auth.currentUser?.uid
    }
}