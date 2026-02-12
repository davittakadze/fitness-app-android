package com.example.betteryou.feature.daily.data.repository

import android.util.Log
import com.example.betteryou.data.local.room.dao.UserNutritionDao
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.data.remote.mapper.toDomain
import com.example.betteryou.feature.daily.data.remote.mapper.toEntity
import com.example.betteryou.feature.daily.data.remote.model.UserDto
import com.example.betteryou.feature.daily.domain.model.User
import com.example.betteryou.feature.daily.domain.repository.GetDataRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetDataRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val userNutritionDao: UserNutritionDao,
) : GetDataRepository {

    override fun getUserNutrition(): Flow<Resource<User>> = flow {

        emit(Resource.Loader(true))

        val currentUserId = auth.currentUser?.uid
            ?: throw Exception("No logged-in user")

        try {
            // 1️⃣ Firestore → DTO
            val snapshot = firestore
                .collection("users")
                .document(currentUserId)
                .get()
                .await()

            val dto = snapshot.toObject(UserDto::class.java)
                ?: throw Exception("User data is null")

            // 2️⃣ DTO → Entity
            val entity = dto.toEntity()

            // 3️⃣ Save to Room
            userNutritionDao.insertUserNutrition(entity)

            // დროებით log
            val saved = userNutritionDao.getUserNutritionById(currentUserId).first()
            Log.d("ROOM_AFTER_SAVE", saved.toString())

        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }

        // 4️⃣ ALWAYS return Room Flow
        userNutritionDao
            .getUserNutritionById(currentUserId)
            .map { entity ->
                entity?.toDomain()
                    ?: throw Exception("User not found in Room")
            }
            .collect { user ->
                emit(Resource.Success(user))
            }
        val allData = userNutritionDao.getUserNutritionById(currentUserId).first()

    }
}
