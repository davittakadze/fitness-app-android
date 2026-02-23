package com.example.betteryou.data.local.room.entity.notification

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications_history")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val message: String,
    val type: String,
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)