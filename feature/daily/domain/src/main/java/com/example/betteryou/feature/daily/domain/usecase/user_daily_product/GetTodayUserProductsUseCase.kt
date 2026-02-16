package com.example.betteryou.feature.daily.domain.usecase.user_daily_product

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.domain.model.UserDailyProduct
import com.example.betteryou.feature.daily.domain.repository.user_daily_product.UserDailyProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodayUserProductsUseCase @Inject constructor(
    private val repository: UserDailyProductRepository,
) {
    operator fun invoke(userId:String): Flow<Resource<List<UserDailyProduct>>> {
        return repository.getTodayProducts(userId)
    }
}