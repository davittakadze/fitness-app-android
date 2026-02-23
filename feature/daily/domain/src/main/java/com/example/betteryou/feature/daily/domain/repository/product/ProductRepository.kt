package com.example.betteryou.feature.daily.domain.repository.product

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.domain.model.product.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<Resource<List<Product>>>
}