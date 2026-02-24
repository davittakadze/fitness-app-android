package com.example.betteryou.feature.daily.data.repository.product

import com.example.betteryou.data.common.HandleResponse
import com.example.betteryou.data.local.room.dao.product.ProductDao
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.data.remote.mapper.toDomain
import com.example.betteryou.feature.daily.data.remote.mapper.toEntity
import com.example.betteryou.feature.daily.data.remote.service.ProductService
import com.example.betteryou.feature.daily.domain.model.product.Product
import com.example.betteryou.feature.daily.domain.repository.product.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val productDao: ProductDao,
    private val handleResponse: HandleResponse
) : ProductRepository {

    override suspend fun getProducts(currentLang: String): Flow<Resource<List<Product>>> = flow {

        val cached = productDao.getAllProducts().firstOrNull() ?: emptyList()
        if (cached.isNotEmpty()) {
            emit(Resource.Success(cached.map { it.toDomain(currentLang) }))
        }

        handleResponse.safeApiCall { productService.getProducts() }.collect { resource ->

            when (resource) {
                is Resource.Success -> {
                    val entities = resource.data.map { it.toEntity() }

                    productDao.clearProducts()
                    productDao.insertProducts(entities)

                    emit(Resource.Success(entities.map { it.toDomain(currentLang) }))
                }

                is Resource.Error -> emit(Resource.Error(resource.errorMessage))
                is Resource.Loader -> emit(Resource.Loader(resource.isLoading))
            }
        }
    }
}