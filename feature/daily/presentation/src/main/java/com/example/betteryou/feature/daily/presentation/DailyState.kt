package com.example.betteryou.feature.daily.presentation

import com.example.betteryou.feature.daily.presentation.model.ProductUi
import com.example.betteryou.feature.daily.presentation.model.UserDailyProductUi

data class DailyState(
    //calories
    val consumedCalories: Int=0,
    val totalCaloriesGoal:Int=0,

    //proteins
    val protein:Int=0,
    val totalProteinGoal: Int=0,

    //fats
    val fat: Int=0,
    val totalFatGoal: Int=0,

    //carbs
    val carbs: Int=0,
    val totalCarbsGoal: Int=0,

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
