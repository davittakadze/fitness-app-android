package com.example.betteryou.data.local.room.dao.notification

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.betteryou.data.local.room.entity.notification.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("SELECT * FROM notifications_history ORDER BY timestamp DESC")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Query("DELETE FROM notifications_history")
    suspend fun clearHistory()

    @Query("DELETE FROM notifications_history WHERE id = :notificationId")
    suspend fun deleteNotificationById(notificationId: Int)

    @Query("UPDATE notifications_history SET isRead = :isRead WHERE id = :id")
    suspend fun updateReadStatus(id: Int, isRead: Boolean)
}