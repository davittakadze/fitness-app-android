package com.example.betteryou.feature.daily.presentation

import com.example.betteryou.feature.daily.presentation.model.ProductUi
import com.example.betteryou.feature.daily.presentation.model.UserDailyProductUi

data class DailyState(
    //calories
    val consumedCalories: Double=0.0,
    val totalCaloriesGoal:Double=0.0,

    //proteins
    val protein:Double=0.0,
    val totalProteinGoal: Double=0.0,

    //fats
    val fat: Double=0.0,
    val totalFatGoal: Double=0.0,

    //carbs
    val carbs: Double=0.0,
    val totalCarbsGoal: Double=0.0,

    //water
    val currentWater: Float=0f,
    val waterGoal: Double=0.0,

    //pager
    val currentPage: Int = 0,

    //products
    val products: List<ProductUi> = emptyList(),
    val consumedProducts: List<UserDailyProductUi> = emptyList(),

    //is loading?
    val isLoading: Boolean = false,

    //bottom sheet state
    val selectedProduct:ProductUi?=null,
    val isBottomSheetOpen:Boolean=false
)
