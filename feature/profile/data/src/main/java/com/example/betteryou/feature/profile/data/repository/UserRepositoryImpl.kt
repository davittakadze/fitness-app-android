package com.example.betteryou.feature.profile.data.repository

import com.example.betteryou.data.common.HandleFirebase
import com.example.betteryou.data.local.room.dao.UserDao
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.profile.data.remote.mapper.toDto
import com.example.betteryou.feature.profile.data.remote.mapper.toEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import androidx.core.net.toUri
import com.example.betteryou.feature.profile.domain.model.User
import com.example.betteryou.feature.profile.domain.repository.UserProfileRepository

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val handleFirebase: HandleFirebase,
    private val auth: FirebaseAuth,
    private val userDao: UserDao,
) : UserProfileRepository {

    //upload on firestore function
    override fun uploadUserProfile(user: User): Flow<Resource<Unit>> = handleFirebase.safeCall {
        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")

        var entity = user.toDto().copy(id = userId).toEntity()

        var photoUrl: String? = null
        user.photoUrl?.let { uriString ->
            val uri = uriString.toUri()
            val ref = storage.reference.child("profile_photos/$userId.jpg")
            ref.putFile(uri).await()
            photoUrl = ref.downloadUrl.await().toString()
        }

        entity = entity.copy(profilePhotoUrl = photoUrl)

        val existingUser = userDao.getUser(userId).firstOrNull()
        if (existingUser == null) {
            userDao.insertUser(entity)
        } else {
            userDao.updateUser(entity)
        }

        val userMap = mapOf(
            "firstName" to user.firstName,
            "lastName" to user.lastName,
            "age" to user.age,
            "sex" to user.gender,
            "weight" to user.weight,
            "height" to user.height,
            "profilePhotoUrl" to photoUrl
        )

        firestore.collection("users")
            .document(userId)
            .set(userMap, SetOptions.merge())
            .await()
    }

    //get from firestore function
    override fun getUser(): Flow<Resource<User?>> = handleFirebase.safeCall {
        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
        val doc = firestore.collection("users")
            .document(userId)
            .get()
            .await()

        if (!doc.exists()) return@safeCall null

        User(
            firstName = doc.getString("firstName"),
            lastName = doc.getString("lastName"),
            age = doc.getLong("age")?.toInt(),
            gender = doc.getString("sex"),
            height = doc.getDouble("height")?.toFloat(),
            weight = doc.getDouble("weight")?.toFloat(),
            photoUrl = doc.getString("profilePhotoUrl")
        )
    }

}