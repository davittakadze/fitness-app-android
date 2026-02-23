package com.bettetyou.feature.notification.presentation.screen

import androidx.lifecycle.viewModelScope
import com.bettetyou.feature.notification.domain.repository.NotificationRepository
import com.bettetyou.feature.notification.domain.usecase.history.DeleteNotificationUseCase
import com.bettetyou.feature.notification.domain.usecase.history.UpdateReadStatusUseCase
import com.bettetyou.feature.notification.domain.usecase.reminder.GetWaterReminderStatusUseCase
import com.bettetyou.feature.notification.domain.usecase.reminder.ManageWaterReminderUseCase
import com.bettetyou.feature.notification.presentation.mapper.toPresentation
import com.example.betteryou.presentation.common.BaseViewModel
import com.example.betteryou.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.betteryou.core_ui.R

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val manageWaterReminderUseCase: ManageWaterReminderUseCase,
    private val getWaterReminderStatusUseCase: GetWaterReminderStatusUseCase,
    private val notificationRepository: NotificationRepository,
    private val deleteNotificationUseCase: DeleteNotificationUseCase,
    private val updateReadStatusUseCase: UpdateReadStatusUseCase,
) :
    BaseViewModel<NotificationState, NotificationEvent, NotificationSideEffect>(NotificationState()) {

    override fun onEvent(event: NotificationEvent) {
        when (event) {

            is NotificationEvent.OnWaterReminderToggled -> {
                if (event.isChecked) {
                    emitSideEffect(NotificationSideEffect.RequestNotificationPermission)
                } else {
                    viewModelScope.launch {
                        manageWaterReminderUseCase(ManageWaterReminderUseCase.ReminderAction.Disable)
                    }
                }
            }

            is NotificationEvent.OnPermissionResultReceived -> {
                updateState { copy(isNotificationPermissionGranted = event.isGranted) }
                if (event.isGranted) {
                    viewModelScope.launch {
                        manageWaterReminderUseCase(ManageWaterReminderUseCase.ReminderAction.Enable)
                    }
                } else {
                    updateState { copy(isWaterReminderEnabled = false) }
                    emitSideEffect(
                        NotificationSideEffect.ShowPermissionRequiredMessage(
                            UiText.StringResource(R.string.enable_notifications)
                        )
                    )
                }
            }

            is NotificationEvent.OnBackClick -> emitSideEffect(NotificationSideEffect.NavigateBack)
            is NotificationEvent.ObserveWaterReminderStatus -> observeWaterReminderStatus()
            is NotificationEvent.GetNotifications -> getNotifications()
            is NotificationEvent.DeleteNotificationById -> deleteNotificationById(event.id)
            is NotificationEvent.OnWaterNotificationClicked -> onWaterNotificationClicked(event.id)
        }
    }
    private fun observeWaterReminderStatus() {
        getWaterReminderStatusUseCase()
            .onEach { isEnabled ->
                updateState { copy(isWaterReminderEnabled = isEnabled) }
            }
            .launchIn(viewModelScope)
    }

    private fun getNotifications() {
        notificationRepository.getNotifications()
            .map { list -> list.map { it.toPresentation() } }
            .onEach { uiNotifications ->
                updateState { copy(notifications = uiNotifications) }
            }
            .launchIn(viewModelScope)


    }

    private fun onWaterNotificationClicked(id: String) {
        viewModelScope.launch {
            updateReadStatusUseCase.invoke(id.toInt(), true)
            emitSideEffect(NotificationSideEffect.NavigateToDaily)
        }
    }

    private fun deleteNotificationById(id: Int) {
        viewModelScope.launch {
            deleteNotificationUseCase.invoke(id)
        }
    }
}