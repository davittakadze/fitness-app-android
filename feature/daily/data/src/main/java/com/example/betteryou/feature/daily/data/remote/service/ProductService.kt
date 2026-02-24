package com.example.betteryou.feature.daily.data.remote.service

import com.example.betteryou.feature.daily.data.remote.model.product.ProductDto
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("e60919c5-a3cf-4d01-9ec3-17ac47ad13fc")
    suspend fun getProducts(): Response<List<ProductDto>>
}
