package com.example.betteryou.feature.daily.data.repository.product

import com.example.betteryou.data.common.HandleResponse
import com.example.betteryou.data.common.asResource
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.data.remote.mapper.toDomain
import com.example.betteryou.feature.daily.data.remote.service.ProductService
import com.example.betteryou.feature.daily.domain.model.Product
import com.example.betteryou.feature.daily.domain.repository.product.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
   private val productService: ProductService,
    private val handleResponse: HandleResponse,
) : ProductRepository {
    override suspend fun getProducts(): Flow<Resource<List<Product>>> {
        return handleResponse.safeApiCall {
           productService.getProducts()
        }.asResource { list ->
            list.map {
                it.toDomain()
            }
        }
    }
}