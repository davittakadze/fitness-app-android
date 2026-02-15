package com.example.betteryou.feature.daily.presentation

import androidx.lifecycle.viewModelScope
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.domain.usecase.GetUserIdUseCase
import com.example.betteryou.feature.daily.domain.model.Intake
import com.example.betteryou.feature.daily.domain.usecase.data.GetNutrientsUseCase
import com.example.betteryou.feature.daily.domain.usecase.intake.GetDailyIntakeUseCase
import com.example.betteryou.feature.daily.domain.usecase.intake.UpdateDailyIntakeUseCase
import com.example.betteryou.feature.daily.domain.usecase.product.ProductUseCase
import com.example.betteryou.feature.daily.presentation.DailyEvent.*
import com.example.betteryou.feature.daily.presentation.mapper.toPresentation
import com.example.betteryou.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val getDailyDataUseCase: GetNutrientsUseCase,
    private val updateDailyIntakeUseCase: UpdateDailyIntakeUseCase,
    private val getDailyIntakeUseCase: GetDailyIntakeUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val getProductsUseCase: ProductUseCase,
) : BaseViewModel<DailyState, DailyEvent, DailySideEffect>(DailyState()) {

    init {
        getProducts()
        getDailyData()
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
            is OpenBottomSheet -> updateState{
                copy(selectedProduct=event.product, isBottomSheetOpen =true)
            }

            CloseBottomSheet -> updateState{
                copy(isBottomSheetOpen = false)
            }

            is AddProductQuantity -> {
                val factor = event.quantity / 100.0
                val addedCalories = (event.product.calories * factor).toInt()
                val addedProtein = (event.product.protein * factor).toInt()
                val addedFat = (event.product.fat * factor).toInt()
                val addedCarbs = (event.product.carbs * factor).toInt()
                updateState {
                    copy(
                        consumedCalories = state.value.consumedCalories + addedCalories,
                        protein = state.value.protein + addedProtein,
                        fat = state.value.fat + addedFat,
                        carbs = state.value.carbs + addedCarbs
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
    }

    private fun getDailyData() {
        viewModelScope.launch {
            getDailyDataUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loader -> {
                        updateState { copy(isLoading = true) }
                    }

                    is Resource.Success -> {
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
                    }
                }
            }

            val userId = getUserIdUseCase() ?: return@launch

            val date = System.currentTimeMillis()

            getDailyIntakeUseCase(userId, date).collect { resource ->
                when (resource) {
                    is Resource.Loader -> {
                        updateState { copy(isLoading = true) }
                    }

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

                    is Resource.Error -> {
                        //    emitSideEffect(DailySideEffect.ShowError(resource.errorMessage))
                    }
                }
            }
        }
    }

    private fun updateData(
        calories: Int? = null,
        protein: Int? = null,
        fat: Int? = null,
        carbs: Int? = null,
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
                when(resource) {
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
    private fun getStartOfDayMillis(): Long {
        val now = java.util.Calendar.getInstance()
        now.set(java.util.Calendar.HOUR_OF_DAY, 0)
        now.set(java.util.Calendar.MINUTE, 0)
        now.set(java.util.Calendar.SECOND, 0)
        now.set(java.util.Calendar.MILLISECOND, 0)
        return now.timeInMillis
    }
    private fun getTodayStartTimestamp(): Long {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
}


