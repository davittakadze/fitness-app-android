package com.example.betteryou.feature.daily.presentation

import androidx.lifecycle.viewModelScope
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.domain.usecase.GetUserIdUseCase
import com.example.betteryou.feature.daily.domain.model.Intake
import com.example.betteryou.feature.daily.domain.usecase.data.GetNutrientsUseCase
import com.example.betteryou.feature.daily.domain.usecase.intake.GetDailyIntakeUseCase
import com.example.betteryou.feature.daily.domain.usecase.intake.UpdateDailyIntakeUseCase
import com.example.betteryou.feature.daily.presentation.DailyEvent.*
import com.example.betteryou.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val getDailyDataUseCase: GetNutrientsUseCase,
    private val updateDailyIntakeUseCase: UpdateDailyIntakeUseCase,
    private val getDailyIntakeUseCase: GetDailyIntakeUseCase,
    private val getUserIdUseCase: GetUserIdUseCase
) : BaseViewModel<DailyState, DailyEvent, DailySideEffect>(DailyState()) {

    init {
        // ჩატვირთე მხოლოდ ერთხელ ViewModel-ის სიცოცხლეში
        getDailyData()
    }
    override fun onEvent(event: DailyEvent) {
        when (event) {
            //water events
            is ChangeWater -> {
                updateState { copy(currentWater = event.water.coerceAtLeast(0f)) }
                onEvent(SaveWaterToDb(event.water))
            }
            LoadUserNutrition -> getDailyData()
            is SaveWaterToDb -> updateData(water=event.water.toDouble())
            is ChangePage -> {
                    updateState{ copy(currentPage = event.page) }
            }
        }
    }
    private fun getDailyData() {
        viewModelScope.launch {

            // პირველი UseCase
            getDailyDataUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loader -> {
                        // Loader state შეიძლება დაემატოს UI-ში
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
                        // Error state handle
                    }
                }
            }

            val userId = getUserIdUseCase() ?: return@launch
            val date = System.currentTimeMillis()
            getDailyIntakeUseCase(userId, date).collect { resource ->
                when (resource) {
                    is Resource.Loader -> {
                        // Loader handle (თუ გინდა, ცალკე)
                    }

                    is Resource.Success -> {
                        resource.data?.let { intake ->
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
                        // Error handle
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
        water: Double? = null
    ) {
        viewModelScope.launch {
            val userId = getUserIdUseCase() ?: return@launch
            val today = System.currentTimeMillis()

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
}