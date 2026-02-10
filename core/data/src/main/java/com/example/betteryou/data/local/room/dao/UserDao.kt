package com.example.betteryou.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.betteryou.data.local.room.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    //for getting user
    @Query("SELECT * FROM user_profile WHERE id = :userId")
    fun getUser(userId: String): Flow<UserEntity?>

    //for new users
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    //for existing users
    @Update
    suspend fun updateUser(user: UserEntity)

    //for deleting user
    @Query("DELETE FROM user_profile WHERE id = :userId")
    suspend fun deleteUser(userId: String)

}