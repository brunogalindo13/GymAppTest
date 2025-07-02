package com.test.gymapptest.ui.states

import com.test.gymapptest.ui.model.EjercicioM

sealed interface EjerciciosUiState {
    object Loading : EjerciciosUiState
    data class SuccessEjercicio(val ejercicios: List<EjercicioM>) : EjerciciosUiState
    data class Error(val exception: Throwable) : EjerciciosUiState

}