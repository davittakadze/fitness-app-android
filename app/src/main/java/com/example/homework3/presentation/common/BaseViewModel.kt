package com.example.homework3.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework3.domain.common.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Event, SideEffect>(
    initialState: State,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _sideEffect by lazy { Channel<SideEffect>() }
    val sideEffect: Flow<SideEffect> by lazy { _sideEffect.receiveAsFlow() }

    open fun onEvent(event: Event) = Unit

    protected fun updateState(newState: State) {
        _state.update { newState }
    }

    protected fun updateState(block: State.() -> State) {
        _state.update(block)
    }

    protected fun emitSideEffect(sideEffect: SideEffect) {
        viewModelScope.launch {
            _sideEffect.send(sideEffect)
        }
    }

    protected fun <T : Any> handleResponse(
        apiCall: suspend () -> Flow<Resource<T>>,
        onSuccess: (T) -> Unit,
        onError: ((String) -> Unit)? = null,
        onLoading: (Resource.Loader<T>) -> Unit,
    ) {
        viewModelScope.launch {
            apiCall.invoke().collect { resource ->
                getResourceType(
                    resource = resource,
                    onSuccess = {
                        onSuccess.invoke(it)
                    },
                    onError = {
                        onError?.invoke(it.errorMessage)
                    },
                    onLoading = {
                        onLoading.invoke(it)
                    }
                )
            }
        }
    }

    protected fun <T : Any> getResourceType(
        resource: Resource<T>,
        onSuccess: (T) -> Unit,
        onLoading: (Resource.Loader<T>) -> Unit,
        onError: ((Resource.Error<T>) -> Unit)? = null,
    ) {
        when (resource) {
            is Resource.Error -> {
                onError?.invoke(resource)
            }

            is Resource.Loader -> {
                onLoading.invoke(resource)
            }

            is Resource.Success -> {
                onSuccess.invoke(resource.data)
            }
        }
    }
}