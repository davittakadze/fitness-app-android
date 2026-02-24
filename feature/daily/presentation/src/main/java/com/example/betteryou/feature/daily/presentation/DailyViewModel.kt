package com.example.betteryou.feature.daily.presentation

import androidx.lifecycle.viewModelScope
import com.example.betteryou.domain.common.DatastoreKeys
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.domain.usecase.GetPreferencesUseCase
import com.example.betteryou.domain.usecase.GetUserIdUseCase
import com.example.betteryou.domain.usecase.SetPreferencesUseCase
import com.example.betteryou.feature.daily.domain.model.intake.Intake
import com.example.betteryou.feature.daily.domain.usecase.user.GetUserUseCase
import com.example.betteryou.feature.daily.domain.usecase.intake.GetDailyIntakeUseCase
import com.example.betteryou.feature.daily.domain.usecase.intake.UpdateDailyIntakeUseCase
import com.example.betteryou.feature.daily.domain.usecase.product.ProductUseCase
import com.example.betteryou.feature.daily.domain.usecase.user.CalculateNutritionUseCase
import com.example.betteryou.feature.daily.domain.usecase.user.RefreshUserUseCase
import com.example.betteryou.feature.daily.domain.usecase.user_daily_product.AddUserDailyProductUseCase
import com.example.betteryou.feature.daily.domain.usecase.user_daily_product.DeleteProductByIdUseCase
import com.example.betteryou.feature.daily.domain.usecase.user_daily_product.GetTodayUserProductsUseCase
import com.example.betteryou.feature.daily.presentation.DailyEvent.*
import com.example.betteryou.feature.daily.presentation.mapper.toDomain
import com.example.betteryou.feature.daily.presentation.mapper.toPresentation
import com.example.betteryou.feature.daily.presentation.model.UserDailyProductUi
import com.example.betteryou.presentation.common.BaseViewModel
import com.example.betteryou.presentation.common.UiText
import com.example.betteryou.util.getStartOfDayMillis
import com.example.betteryou.util.getTodayStartTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    //nutrients
    private val getDailyDataUseCase: GetUserUseCase,
    private val calculateNutritionUseCase: CalculateNutritionUseCase,
    //intake
    private val updateDailyIntakeUseCase: UpdateDailyIntakeUseCase,
    private val getDailyIntakeUseCase: GetDailyIntakeUseCase,
    //id
    private val getUserIdUseCase: GetUserIdUseCase,
    private val refreshUserUseCase: RefreshUserUseCase,
    //products
    private val getProductsUseCase: ProductUseCase,
    private val getUserDailyProduct: GetTodayUserProductsUseCase,
    private val addUserDailyProductUseCase: AddUserDailyProductUseCase,
    private val deleteProductByIdUseCase: DeleteProductByIdUseCase,
    //datastore
    private val getPreferencesUseCase: GetPreferencesUseCase
) : BaseViewModel<DailyState, DailyEvent, DailySideEffect>(DailyState()) {

    init {
        viewModelScope.launch {
            val currentLang = getPreferencesUseCase(
                DatastoreKeys.USER_LANGUAGE_KEY,
                ""
            ).first()
            getProducts(currentLang)
        }
        getDailyData()
        loadUserDailyProducts()
        loadInitialIntake()
        viewModelScope.launch {
            refreshUserUseCase()
        }
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
                        calories = state.value.consumedCalories,
                        protein = state.value.protein,
                        fat = state.value.fat,
                        carbs = state.value.carbs
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
                        calories = state.value.consumedCalories,
                        protein = state.value.protein,
                        fat = state.value.fat,
                        carbs = state.value.carbs,
                    )
                }
            }

            OnNavigateToNotifications -> emitSideEffect(DailySideEffect.NavigateToNotifications)
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
                        val nutrient=calculateNutritionUseCase(resource.data)
                        updateState {
                            copy(
                                totalCaloriesGoal = nutrient.calories.toDouble(),
                                totalProteinGoal = nutrient.protein.toDouble(),
                                totalFatGoal = nutrient.fats.toDouble(),
                                totalCarbsGoal = nutrient.carbs.toDouble(),
                                waterGoal = nutrient.waterLiters
                            )
                        }
                    }

                    is Resource.Error -> {
                        updateState {
                            copy(isLoading = false)
                        }
                        emitSideEffect(DailySideEffect.ShowError(UiText.DynamicString(resource.errorMessage)))
                    }
                }
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

    private fun getProducts(currentLang:String) {
        viewModelScope.launch {
            getProductsUseCase(currentLang).collect {resource->
                when (resource) {
                    is Resource.Loader -> {
                        updateState { copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        updateState {
                            copy(products = resource.data.map { product ->
                                product.toPresentation()
                            })
                        }
                    }

                    is Resource.Error -> {
                        updateState {
                            copy(isLoading = false)
                        }
                        emitSideEffect(DailySideEffect.ShowError(UiText.DynamicString(resource.errorMessage)))
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
                        updateState { copy(isLoading = false) }
                    }
                }
            }
        }
    }

    private fun loadUserDailyProducts() {
        viewModelScope.launch {
            val userId = getUserIdUseCase() ?: return@launch
            handleResponse(
                apiCall = { getUserDailyProduct.invoke(userId) },
                onLoading = { loader ->
                    updateState {
                        copy(isLoading = true)
                    }
                },
                onSuccess = { resource ->
                    updateState { copy(consumedProducts = resource.map { it.toPresentation() }) }
                },
                onError = { resource ->
                    updateState {
                        copy(isLoading = false)
                    }

                    emitSideEffect(
                        DailySideEffect.ShowError(
                            UiText.DynamicString(resource)
                        )
                    )
                }
            )
        }
    }
}



