package com.example.betteryou.feature.daily.domain.usecase.user_daily_product

import com.example.betteryou.feature.daily.domain.repository.user_daily_product.UserDailyProductRepository
import javax.inject.Inject

class ClearOldUserDailyProductsUseCase @Inject constructor(
    private val repository: UserDailyProductRepository,
) {
    suspend operator fun invoke(userId: String) {
        repository.clearOldProducts(userId)
    }
}