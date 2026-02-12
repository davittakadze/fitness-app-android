package com.example.betteryou.feature.daily.presentation

import androidx.lifecycle.viewModelScope
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.domain.usecase.GetDataUseCase
import com.example.betteryou.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val getDailyDataUseCase: GetDataUseCase
) : BaseViewModel<DailyState, DailyEvent, DailySideEffect>(DailyState()) {
    override fun onEvent(event: DailyEvent) {
        when (event) {
            //water events
            is DailyEvent.ChangeWater -> {
                updateState { copy(currentWater = event.water.coerceIn(0f, waterGoal)) }
            }

            DailyEvent.LoadUserNutrition -> getDailyData()
        }
    }
    private fun getDailyData() {
        viewModelScope.launch {
            getDailyDataUseCase().collect{ resource ->
                when (resource) {
                    is Resource.Loader -> {

                    }

                    is Resource.Success -> {
                        updateState {
                            copy(
                                totalCaloriesGoal = resource.data.dailyCalories,
                                totalProteinGoal = resource.data.protein,
                                totalFatGoal = resource.data.fats,
                                totalCarbsGoal = resource.data.carbs
                            )
                        }
                    }

                    is Resource.Error -> {

                    }
                }
            }
        }
    }
}