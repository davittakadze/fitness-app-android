package com.example.betteryou.feature.daily.data.remote.mapper

import com.example.betteryou.data.local.room.entity.product.ProductEntity
import com.example.betteryou.feature.daily.data.remote.model.product.ProductDto
import com.example.betteryou.feature.daily.domain.model.product.Product

fun ProductDto.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        nameEn = name["en"] ?: "",
        nameKa = name["ka"] ?: "",
        photo = photo,
        calories = calories,
        protein = protein,
        carbs = carbs,
        fat = fat,
        descriptionEn = description["en"] ?: "",
        descriptionKa = description["ka"] ?: ""
    )
}

fun ProductEntity.toDomain(currentLang: String): Product {
    return Product(
        id = id,
        name = if(currentLang == "ka") nameKa.ifEmpty { nameEn } else nameEn,
        description = if(currentLang == "ka") descriptionKa.ifEmpty { descriptionEn } else descriptionEn,
        photo = photo,
        calories = calories,
        protein = protein,
        carbs = carbs,
        fat = fat
    )
}