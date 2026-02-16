package com.example.betteryou.feature.daily.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.domain.usecase.GetUserIdUseCase
import com.example.betteryou.feature.daily.domain.model.Intake
import com.example.betteryou.feature.daily.domain.usecase.data.GetNutrientsUseCase
import com.example.betteryou.feature.daily.domain.usecase.intake.GetDailyIntakeUseCase
import com.example.betteryou.feature.daily.domain.usecase.intake.UpdateDailyIntakeUseCase
import com.example.betteryou.feature.daily.domain.usecase.product.ProductUseCase
import com.example.betteryou.feature.daily.domain.usecase.user_daily_product.AddUserDailyProductUseCase
import com.example.betteryou.feature.daily.domain.usecase.user_daily_product.DeleteProductByIdUseCase
import com.example.betteryou.feature.daily.domain.usecase.user_daily_product.GetTodayUserProductsUseCase
import com.example.betteryou.feature.daily.presentation.DailyEvent.*
import com.example.betteryou.feature.daily.presentation.mapper.toDomain
import com.example.betteryou.feature.daily.presentation.mapper.toPresentation
import com.example.betteryou.feature.daily.presentation.model.UserDailyProductUi
import com.example.betteryou.presentation.common.BaseViewModel
import com.example.betteryou.util.getStartOfDayMillis
import com.example.betteryou.util.getTodayStartTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    //nutrients
    private val getDailyDataUseCase: GetNutrientsUseCase,
    //intake
    private val updateDailyIntakeUseCase: UpdateDailyIntakeUseCase,
    private val getDailyIntakeUseCase: GetDailyIntakeUseCase,
    //id
    private val getUserIdUseCase: GetUserIdUseCase,
    //products
    private val getProductsUseCase: ProductUseCase,
    private val getUserDailyProduct: GetTodayUserProductsUseCase,
    private val addUserDailyProductUseCase: AddUserDailyProductUseCase,
    private val deleteProductByIdUseCase: DeleteProductByIdUseCase,
) : BaseViewModel<DailyState, DailyEvent, DailySideEffect>(DailyState()) {

    init {
        getProducts()
        getDailyData()
        loadUserDailyProducts()
        loadInitialIntake()
    }

    override fun onEvent(event: DailyEvent) {
        when (event) {
            //water events
            is ChangeWater -> {
                updateState { copy(currentWater = event.water.coerceAtLeast(0f)) }
                onEvent(SaveWaterToDb(event.water))
            }

            is SaveWaterToDb -> updateData(water = event.water.toDouble())

            //nutrition event
            LoadUserNutrition -> getDailyData()

            //horizontal pager event
            is ChangePage -> {
                updateState { copy(currentPage = event.page) }
            }

            //bottom sheet events
            is OpenBottomSheet -> updateState {
                copy(selectedProduct = event.product, isBottomSheetOpen = true)
            }

            CloseBottomSheet -> updateState {
                copy(isBottomSheetOpen = false)
            }

            is AddProductQuantity -> {
                val factor = event.quantity / 100.0
                val addedCalories = (event.product.calories * factor)
                val addedProtein = (event.product.protein * factor)
                val addedFat = (event.product.fat * factor)
                val addedCarbs = (event.product.carbs * factor)

                viewModelScope.launch {
                    val userId = getUserIdUseCase() ?: return@launch

                    val newDailyProduct = UserDailyProductUi(
                        userId = userId,
                        name = event.product.name,
                        photo = event.product.photo,
                        calories = addedCalories,
                        protein = addedProtein,
                        carbs = addedCarbs,
                        fat = addedFat,
                        description = event.product.description,
                        quantity = event.quantity,
                        date = getStartOfDayMillis()
                    )

                    addUserDailyProductUseCase(newDailyProduct.toDomain())

                    updateState {
                        copy(
                            consumedCalories = state.value.consumedCalories + addedCalories,
                            protein = state.value.protein + addedProtein,
                            fat = state.value.fat + addedFat,
                            carbs = state.value.carbs + addedCarbs,
                            consumedProducts = state.value.consumedProducts + newDailyProduct
                        )
                    }

                    updateData(
                        calories = state.value.consumedCalories + addedCalories,
                        protein = state.value.protein + addedProtein,
                        fat = state.value.fat + addedFat,
                        carbs = state.value.carbs + addedCarbs
                    )
                }
            }

            is DeleteProduct -> {
                viewModelScope.launch {
                    val productId = event.item.id
                    deleteProductByIdUseCase(productId, getUserIdUseCase() ?: return@launch)

                    updateState {
                        copy(
                            consumedProducts = state.value.consumedProducts
                                .filterNot { it.id == productId },
                            consumedCalories = state.value.consumedCalories - event.item.calories,
                            protein = state.value.protein - event.item.protein,
                            fat = state.value.fat - event.item.fat,
                            carbs = state.value.carbs - event.item.carbs
                        )
                    }
                    updateData(
                        calories = state.value.consumedCalories - event.item.calories,
                        protein = state.value.protein - event.item.protein,
                        fat = state.value.fat - event.item.fat,
                        carbs = state.value.carbs - event.item.carbs
                    )
                }
            }
        }
    }

    private fun getDailyData() {
        viewModelScope.launch {
            Log.d("DailyViewModel", "getDailyData: start collecting nutrients")
            getDailyDataUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loader -> {
                        Log.d("DailyViewModel", "getDailyData: loading")
                        updateState { copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        Log.d("DailyViewModel", "getDailyData: success ${resource.data}")
                        updateState {
                            copy(
                                totalCaloriesGoal = resource.data.dailyCalories,
                                totalProteinGoal = resource.data.protein,
                                totalFatGoal = resource.data.fats,
                                totalCarbsGoal = resource.data.carbs,
                                waterGoal = resource.data.water
                            )
                        }
                    }

                    is Resource.Error -> {
                        //   emitSideEffect(DailySideEffect.ShowError(resource.errorMessage))
                        Log.e("DailyViewModel", "getDailyData: error ${resource.errorMessage}")
                    }
                }
                Log.d("DailyViewModel", "getDailyData: finished collecting nutrients")
            }
        }
    }

    private fun updateData(
        calories: Double? = null,
        protein: Double? = null,
        fat: Double? = null,
        carbs: Double? = null,
        water: Double? = null,
    ) {
        viewModelScope.launch {
            val userId = getUserIdUseCase() ?: return@launch
            val today = getStartOfDayMillis()

            val intakeCalories = calories ?: state.value.consumedCalories
            val intakeProtein = protein ?: state.value.protein
            val intakeFat = fat ?: state.value.fat
            val intakeCarbs = carbs ?: state.value.carbs
            val intakeWater = water ?: state.value.currentWater.toDouble()

            updateDailyIntakeUseCase(
                userId = userId,
                intake = Intake(
                    dailyCalories = intakeCalories,
                    protein = intakeProtein,
                    fats = intakeFat,
                    carbs = intakeCarbs,
                    water = intakeWater
                ),
                date = today
            )

            updateState {
                copy(
                    consumedCalories = intakeCalories,
                    protein = intakeProtein,
                    fat = intakeFat,
                    carbs = intakeCarbs,
                    currentWater = intakeWater.toFloat()
                )
            }
        }
    }

    private fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect {
                when (it) {
                    is Resource.Loader -> {
                        updateState { copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        updateState {
                            copy(products = it.data.map { product ->
                                product.toPresentation()
                            })
                        }
                    }

                    is Resource.Error -> {
                        //  emitSideEffect(DailySideEffect.ShowError(it.errorMessage))
                    }
                }
            }
        }
    }

    private fun loadInitialIntake() {
        viewModelScope.launch {
            val userId = getUserIdUseCase() ?: return@launch

            val today = getTodayStartTimestamp()

            getDailyIntakeUseCase(userId, today).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data.let { intake ->
                            updateState {
                                copy(
                                    consumedCalories = intake.dailyCalories,
                                    protein = intake.protein,
                                    fat = intake.fats,
                                    carbs = intake.carbs,
                                    currentWater = intake.water.toFloat()
                                )
                            }
                        }
                    }

                    is Resource.Loader -> {
                        updateState { copy(isLoading = true) }
                    }

                    is Resource.Error -> {
                        //...
                    }
                }
            }
        }
    }

    private fun loadUserDailyProducts() {
        viewModelScope.launch {
            val userId = getUserIdUseCase() ?: return@launch
            getUserDailyProduct(userId).collect { resource ->
                when (resource) {
                    is Resource.Loader -> {
                        updateState { copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        updateState { copy(consumedProducts = resource.data.map { it.toPresentation() }) }
                    }

                    is Resource.Error -> {
                        // emitSideEffect(DailySideEffect.ShowError(resource.errorMessage))
                    }
                }
            }
        }
    }
}


