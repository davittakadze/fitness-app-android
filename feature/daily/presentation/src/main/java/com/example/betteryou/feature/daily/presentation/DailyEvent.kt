package com.example.betteryou.feature.daily.presentation

import com.example.betteryou.feature.daily.presentation.model.ProductUi
import com.example.betteryou.feature.daily.presentation.model.UserDailyProductUi

sealed interface DailyEvent {
    //water events
    data class ChangeWater(val water: Float) : DailyEvent
    data class SaveWaterToDb(val water:Float) : DailyEvent

    //necessary nutrition event
    data object LoadUserNutrition : DailyEvent

    //pager event
    data class ChangePage(val page:Int): DailyEvent

    //bottom sheet events
    data class OpenBottomSheet(val product: ProductUi) : DailyEvent
    object CloseBottomSheet : DailyEvent

    //chosen product
    data class AddProductQuantity(
        val product: ProductUi,
        val quantity: Int
    ) : DailyEvent

    //delete product
    data class DeleteProduct(val product: UserDailyProductUi) : DailyEvent
}
