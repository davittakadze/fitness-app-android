package com.example.betteryou.feature.daily.data.repository.user_info

import com.example.betteryou.data.local.room.dao.user.UserDao
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.data.remote.mapper.toDomain
import com.example.betteryou.feature.daily.data.remote.mapper.toEntity
import com.example.betteryou.feature.daily.data.remote.model.user.UserDto
import com.example.betteryou.feature.daily.domain.model.user.User
import com.example.betteryou.feature.daily.domain.repository.user.GetUserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UserInfoRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userDao: UserDao,
) : GetUserRepository {
    override fun getUser(): Flow<Resource<User>> = flow {

        val userId = auth.currentUser?.uid
            ?: run {
                emit(Resource.Error("User not authenticated"))
                return@flow
            }

        emit(Resource.Loader(true))

        val localFlow = userDao.getUser(userId)

        emitAll(
            localFlow.map { entity ->
                if (entity != null) {
                    Resource.Success(entity.toDomain())
                } else {
                    Resource.Loader(true)
                }
            }
        )
    }

    override suspend fun refreshUser() {
        val userId = auth.currentUser?.uid ?: return

        val doc = firestore.collection("users")
            .document(userId)
            .get()
            .await()

        if (!doc.exists()) return

        val remoteUser = UserDto(
            userId = userId,
            name = doc.getString("name"),
            lastName = doc.getString("lastName"),
            age = doc.getLong("age")?.toInt(),
            gender = doc.getString("sex"),
            height = doc.getDouble("height"),
            weight = doc.getDouble("weight"),
            profilePhotoUrl = doc.getString("profilePhotoUrl"),
            activityLevel = doc.getString("activityLevel"),
            goal = doc.getString("goal")
        )

        userDao.insertUser(remoteUser.toEntity())
    }
}