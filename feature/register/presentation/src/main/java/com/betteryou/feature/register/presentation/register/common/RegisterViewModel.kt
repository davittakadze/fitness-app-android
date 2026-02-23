package com.betteryou.feature.register.presentation.register.common

import androidx.lifecycle.viewModelScope
import com.betteryou.feature.register.domain.model.BodyData
import com.betteryou.feature.register.domain.model.RegisterUserInfo
import com.betteryou.feature.register.domain.usecase.RegisterUseCase
import com.betteryou.feature.register.domain.validation.EmailValidatorUseCase
import com.betteryou.feature.register.domain.validation.EmptyFieldsValidatorUseCase
import com.betteryou.feature.register.domain.validation.PasswordValidatorUseCase
import com.betteryou.feature.register.domain.validation.RepeatPasswordValidatorUseCase
import com.example.betteryou.core_ui.R
import com.example.betteryou.presentation.common.BaseViewModel
import com.example.betteryou.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val emailValidatorUseCase: EmailValidatorUseCase,
    private val passwordValidatorUseCase: PasswordValidatorUseCase,
    private val repeatPasswordValidatorUseCase: RepeatPasswordValidatorUseCase,
    private val emptyFieldsValidatorUseCase: EmptyFieldsValidatorUseCase,
) : BaseViewModel<RegisterState, RegisterEvent, RegisterSideEffect>(RegisterState()) {

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnRegisterButtonClick -> register()

            is RegisterEvent.OnEmailChange -> updateState { copy(email = event.email) }
            is RegisterEvent.OnPasswordChange -> updateState { copy(password = event.password) }
            is RegisterEvent.OnRepeatPasswordChange -> updateState { copy(password2 = event.password) }
            is RegisterEvent.OnAgeChange -> updateState { copy(age = event.age) }
            is RegisterEvent.OnHeightChange -> updateState { copy(height = event.height) }
            is RegisterEvent.OnNameChange -> updateState { copy(name = event.name) }
            is RegisterEvent.OnWeightChange -> updateState { copy(weight = event.weight) }
            is RegisterEvent.OnGenderChange -> updateState { copy(gender = event.gender) }
            is RegisterEvent.OnActivityLevelChange -> {
                updateState { copy(activityLevel = event.level) }
            }

            is RegisterEvent.OnGoalTypeChange -> {
                updateState { copy(goal = event.goalType) }
            }

            is RegisterEvent.OnTogglePassword1Visibility -> {
                updateState { copy(isPassword1Visible = event.isVisible) }
            }

            is RegisterEvent.OnTogglePassword2Visibility -> {
                updateState { copy(isPassword2Visible = event.isVisible) }
            }

            is RegisterEvent.OnBackClick -> {
                viewModelScope.launch {
                    emitSideEffect(RegisterSideEffect.NavigateBack)
                }
            }

            is RegisterEvent.OnLastNameChange -> updateState{
                copy(lastName = event.lastName)
            }
        }
    }

    private fun register() {
        val model = state.value

        val authFieldsList = listOf(
            model.email,
            model.password,
            model.password2
        )

        val isFieldsEmpty = emptyFieldsValidatorUseCase.invoke(authFieldsList)
        val isEmailValid = emailValidatorUseCase.invoke(model.email)
        val isPassword1Valid = passwordValidatorUseCase.invoke(model.password)
        val isRepeatPasswordValid =
            repeatPasswordValidatorUseCase.invoke(model.password, model.password2)

        val validationResId = when {
            isFieldsEmpty -> R.string.empty_fields
            !isEmailValid -> R.string.invalid_email
            isPassword1Valid -> R.string.invalid_password
            !isRepeatPasswordValid -> R.string.password_mismatch
            else -> null
        }

        validationResId?.let {
            emitSideEffect(
                RegisterSideEffect.ShowError(
                    UiText.StringResource(
                        it
                    )
                )
            )
            return
        }

        val bodyData = BodyData(
            weight = model.weight.toInt(),
            height = model.height.toInt(),
            age = model.age.toInt(),
            gender = model.gender,
            activityLevel = model.activityLevel!!,
            goal = model.goal!!
        )
//        val nutritionResults = calculateNutritionUseCase.invoke(bodyData)

        val userInfo = RegisterUserInfo(
            name = model.name,
            lastName = model.lastName,
            age = model.age.toInt(),
            gender = model.gender.name,
            height = model.height.toDouble(),
            weight = model.weight.toDouble(),
            activityLevel = model.activityLevel.name,
            goal = model.goal.name,
//            dailyCalories = nutritionResults.calories,
//            protein = nutritionResults.protein,
 //           carbs = nutritionResults.carbs,
//            fats = nutritionResults.fats,
 //           water = nutritionResults.waterLiters
        )

        handleResponse(
            apiCall = {
                registerUseCase.invoke(userInfo, model.email, model.password)
            },
            onSuccess = {
                updateState {
                    copy(
                        isPassword1Visible = false,
                        isPassword2Visible = false,
                        loader = false
                    )
                }
                emitSideEffect(RegisterSideEffect.NavigateToHome)
            },
            onError = {
                updateState {
                    copy(
                        isPassword1Visible = false,
                        isPassword2Visible = false,
                        loader = false
                    )
                }
                emitSideEffect(RegisterSideEffect.ShowError(UiText.DynamicString(it)))
            },
            onLoading = { loader ->
                updateState { copy(loader = loader.isLoading) }
            }
        )
    }

}
