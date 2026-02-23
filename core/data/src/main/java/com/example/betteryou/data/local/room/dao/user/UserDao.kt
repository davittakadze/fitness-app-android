package com.example.betteryou.data.local.room.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.betteryou.data.local.room.entity.user.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    //for getting user
    @Query("SELECT * FROM user_profile WHERE id = :userId")
    fun getUser(userId: String): Flow<UserEntity?>

    //for new users
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertUser(user: UserEntity)

    //for existing users
    @Update
    suspend fun updateUser(user: UserEntity)

    //for deleting user
    @Query("DELETE FROM user_profile WHERE id = :userId")
    suspend fun deleteUser(userId: String)

    @Query(
        """
UPDATE user_profile SET
firstName = :firstName,
lastName = :lastName,
age = :age,
birthDate = :birthDate,
gender = :gender,
height = :height,
weight = :weight,
profilePhotoUrl = :photoUrl
WHERE id = :id
"""
    )
    suspend fun updateBasicInfo(
        id: String,
        firstName: String?,
        lastName: String?,
        age: Int?,
        birthDate:String?,
        gender: String?,
        height: Double?,
        weight: Double?,
        photoUrl: String?,
    ):Int
}