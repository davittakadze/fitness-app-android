package com.example.betteryou.feature.daily.data.remote.service

import com.example.betteryou.feature.daily.data.remote.model.product.ProductDto
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun getProducts(): Response<List<ProductDto>>
}
