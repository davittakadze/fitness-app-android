package com.example.betteryou.feature.daily.domain.repository.user_daily_product

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.domain.model.intake.UserDailyProduct
import kotlinx.coroutines.flow.Flow

interface UserDailyProductRepository {

    suspend fun addProduct(product: UserDailyProduct)

    fun getTodayProducts(userId: String): Flow<Resource<List<UserDailyProduct>>>

    suspend fun clearOldProducts(userId: String)

    suspend fun deleteProductById(userId: String, productId: Long)
}