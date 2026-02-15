package com.example.betteryou.feature.daily.domain.usecase.product

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.domain.model.Product
import com.example.betteryou.feature.daily.domain.repository.product.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {
    suspend operator fun invoke(): Flow<Resource<List<Product>>> {
        return productRepository.getProducts()
    }
}