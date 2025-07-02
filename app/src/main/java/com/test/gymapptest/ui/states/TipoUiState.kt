package com.test.gymapptest.ui.states

import com.test.gymapptest.ui.model.TipoM

sealed interface TipoUiState {
    object Loading : TipoUiState
    data class Success(val tipos: List<TipoM>) : TipoUiState
    data class Error(val exception: Throwable) : TipoUiState
}