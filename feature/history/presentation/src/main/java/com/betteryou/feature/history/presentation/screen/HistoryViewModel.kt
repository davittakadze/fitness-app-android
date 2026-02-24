package com.betteryou.feature.history.presentation.screen

import androidx.lifecycle.viewModelScope
import com.betteryou.feature.history.domain.model.GetHistory
import com.betteryou.feature.history.domain.usecase.DeleteHistoryUseCase
import com.betteryou.feature.history.domain.usecase.GetAllHistoryUseCase
import com.betteryou.feature.history.presentation.screen.mapper.toPresentation
import com.example.betteryou.domain.usecase.GetUserIdUseCase
import com.example.betteryou.presentation.common.BaseViewModel
import com.example.betteryou.presentation.common.UiText
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getAllHistoryUseCase: GetAllHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
) : BaseViewModel<HistoryState, HistoryEvent, HistorySideEffect>(HistoryState()) {

    override fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.DeleteHistory -> deleteHistory(event.id)
            is HistoryEvent.OnBackClick -> emitSideEffect(HistorySideEffect.NavigateBack)
        }

    }

    init {
        loadHistory()
    }

    private fun deleteHistory(id: Long) {
        viewModelScope.launch {
            deleteHistoryUseCase.invoke(id)
        }
    }

    private fun loadHistory() {

        handleResponse(
            apiCall = {
                val userId = getUserIdUseCase.invoke() ?: ""

                getAllHistoryUseCase.invoke(userId)
            },
            onSuccess = {
                updateState { copy(history = it.map(GetHistory::toPresentation), loader = false) }
            },
            onError = { message ->
                updateState { copy(loader = false) }

                HistorySideEffect.ShowError(
                    UiText.DynamicString(message)
                )
            },
            onLoading = { loader ->
                updateState { copy(loader = loader.isLoading) }
            }
        )
    }
}