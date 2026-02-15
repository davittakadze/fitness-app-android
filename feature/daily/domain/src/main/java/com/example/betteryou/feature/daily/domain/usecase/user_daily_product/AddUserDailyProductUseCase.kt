package com.example.betteryou.feature.daily.domain.usecase.user_daily_product

import com.example.betteryou.feature.daily.domain.model.UserDailyProduct
import com.example.betteryou.feature.daily.domain.repository.user_daily_product.UserDailyProductRepository
import javax.inject.Inject

class AddUserDailyProductUseCase @Inject constructor(
    private val repository: UserDailyProductRepository,
) {
    suspend operator fun invoke(product: UserDailyProduct) {
        repository.addProduct(product)
    }
}