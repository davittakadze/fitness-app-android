package com.example.betteryou.feature.daily.data.repository

import com.example.betteryou.data.common.HandleFirebase
import com.example.betteryou.data.local.room.dao.nutrition.NutritionDao
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.data.remote.mapper.toDomain
import com.example.betteryou.feature.daily.data.remote.mapper.toEntity
import com.example.betteryou.feature.daily.data.remote.model.UserDto
import com.example.betteryou.feature.daily.domain.model.Nutrient
import com.example.betteryou.feature.daily.domain.repository.data.GetNutrientsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetNutrientsRepositoryImpl @Inject constructor(
    private val handleFirebase: HandleFirebase,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val userNutritionDao: NutritionDao,
) : GetNutrientsRepository {
    override fun getUserNutrition(): Flow<Resource<Nutrient>> =
        handleFirebase.safeCall {

            val currentUserId = auth.currentUser?.uid
                ?: throw Exception("No logged-in user")

            val snapshot = firestore
                .collection("users")
                .document(currentUserId)
                .get()
                .await()

            val dto = snapshot.toObject(UserDto::class.java)
                ?: throw Exception("DTO is null")

            val entity = dto.copy(userId = currentUserId).toEntity()

            userNutritionDao.insertUserNutrition(entity)

            userNutritionDao
                .getUserNutritionById(currentUserId)
                .first()
                ?.toDomain()
                ?: throw Exception("Room returned null")
        }
}
