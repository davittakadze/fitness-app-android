package com.example.betteryou.feature.profile.data.repository

import android.util.Log
import com.example.betteryou.data.local.room.dao.user.UserDao
import com.example.betteryou.domain.common.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import androidx.core.net.toUri
import com.example.betteryou.feature.profile.data.remote.mapper.toDomain
import com.example.betteryou.feature.profile.domain.model.User
import com.example.betteryou.feature.profile.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val auth: FirebaseAuth,
    private val userDao: UserDao,
) : UserProfileRepository {
    override fun getUser(): Flow<Resource<User?>> {
        val userId = auth.currentUser?.uid
            ?: return flowOf(Resource.Error("User not authenticated"))

        return userDao.getUser(userId)
            .map { entity ->
                Resource.Success(entity?.toDomain())
            }
    }

    override fun uploadUserProfile(user: User): Flow<Resource<Unit>> = flow {

        emit(Resource.Loader(true))

        val userId = auth.currentUser?.uid
            ?: throw Exception("User not authenticated")

        try {
            var photoUrl: String? = user.photoUrl

            user.photoUrl?.let { uriString ->
                if (uriString.startsWith("content://") || uriString.startsWith("file://")) {
                    val uri = uriString.toUri()
                    val ref = storage.reference.child("profile_photos/$userId.jpg")
                    ref.putFile(uri).await()
                    photoUrl = ref.downloadUrl.await().toString()
                }
            }

            val rows=userDao.updateBasicInfo(
                id = userId,
                firstName = user.firstName,
                lastName = user.lastName,
                age = user.age,
                birthDate=user.birthDate,
                gender = user.gender,
                height = user.height,
                weight = user.weight,
                photoUrl = photoUrl
            )
            Log.d("ROOM_ROWS", "Updated rows = $rows")

            val userMap = mapOf(
                "name" to user.firstName,
                "lastName" to user.lastName,
                "age" to user.age,
                "sex" to user.gender,
                "weight" to user.weight,
                "height" to user.height,
                "birthDate" to user.birthDate,
                "profilePhotoUrl" to photoUrl
            )

            firestore.collection("users")
                .document(userId)
                .set(userMap, SetOptions.merge())
                .await()

            emit(Resource.Success(Unit))

        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Sync failed."))
            Log.e("UPLOAD_ERROR", e.message ?: "error")
        }

        emit(Resource.Loader(false))
    }
}