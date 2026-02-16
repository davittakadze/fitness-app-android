package com.example.betteryou.feature.daily.domain.usecase.user_daily_product

import com.example.betteryou.feature.daily.domain.repository.user_daily_product.UserDailyProductRepository
import javax.inject.Inject

class DeleteProductByIdUseCase @Inject constructor(
    private val productRepository: UserDailyProductRepository,
) {
    suspend operator fun invoke(productId: Long, userId: String) {
        productRepository.deleteProductById(userId,productId)
    }
}