package com.test.gymapptest.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.gymapptest.domain.UseCase.ejercicio.AddEjercicioUseCase
import com.test.gymapptest.domain.UseCase.ejercicio.GetEjerciciosUseCase
import com.test.gymapptest.domain.UseCase.tipo.GetTiposUSeCase
import com.test.gymapptest.ui.model.EjercicioM
import com.test.gymapptest.ui.model.TipoM
import com.test.gymapptest.ui.states.EjerciciosUiState
import com.test.gymapptest.ui.states.EjerciciosUiState.SuccessEjercicio
import com.test.gymapptest.ui.states.TipoUiState
import com.test.gymapptest.ui.states.TipoUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EjercicioViewModel @Inject constructor(
    private val getTiposUSeCase: GetTiposUSeCase,
    private val addEjercicioUseCase: AddEjercicioUseCase,
    private val getEjercicioUseCase: GetEjerciciosUseCase

) : ViewModel() {
    private val _showAddEjercicioDialog = MutableLiveData<Boolean>()
    val showAddEjercicioDialog: LiveData<Boolean> = _showAddEjercicioDialog


    private val _uiStatte = MutableStateFlow(EjercicioM())
    val uiState: MutableStateFlow<EjercicioM> = _uiStatte


    val iuStateList: StateFlow<TipoUiState> = getTiposUSeCase()
            .map ( ::Success )
            .catch { TipoUiState.Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TipoUiState.Loading)

    val iuStateListEjercicio: StateFlow<EjerciciosUiState> = getEjercicioUseCase()
        .map ( ::SuccessEjercicio )
        .catch { EjerciciosUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EjerciciosUiState.Loading)

    private fun validarNombre(nombre: String): String? {

        if (nombre.isBlank()) return "El nombre es Obligatorio"
        if (nombre.length < 3) return "El nombre debe tener al menos 3 caracteres"
        return null
    }

    fun onNombreChange(tipo: String) {
        _uiStatte.update { currentState ->
            currentState.copy(
                nombre = tipo,
                nombreError = validarNombre(tipo),
                mensajeOk = null
            )
        }
        updateToSend()

    }

    fun onDescripcionChange(tipo: String) {
        _uiStatte.update { currentState ->
            currentState.copy(
                descripcion = tipo
            )
        }
        updateToSend()

    }

    fun onTipoChange(tipo: TipoM) {
        _uiStatte.update { currentState ->
            currentState.copy(
                tipo = tipo
            )
        }
    }

    fun onRepeticionesChange(repeticiones: Int) {
        _uiStatte.update { currentState ->
            currentState.copy(
                repeticion = repeticiones
            )
        }
    }

    fun onPesoChange(peso: Double) {
        _uiStatte.update { currentState ->
            currentState.copy(
                peso = peso
            )
        }
    }

    fun onSeriesChange(series: Int) {
        _uiStatte.update { currentState ->
            currentState.copy(
                serie = series
            )
        }
    }

    fun onDuracionChange(duracion: Double) {
        _uiStatte.update { currentState ->
            currentState.copy(
                duracion = duracion
            )
        }
    }


    private fun updateToSend() {
        _uiStatte.update {
            it.copy(
                isOk = it.nombreError == null &&
                        it.nombre.isNotBlank()
                        && it.tipo != null
            )
        }
    }

    fun sendForm() {
        val nombreActual = _uiStatte.value.nombre

        val errornombre = _uiStatte.value.nombreError

        if (errornombre != null) {
            _uiStatte.update {
                it.copy(nombreError = errornombre)
            }
            updateToSend()
            return
        }
        if (_uiStatte.value.isOk) {
            onEjercicioCreated(uiState.value)

        }

    }
    fun resetDialog() {
        _uiStatte.value = EjercicioM()
    }

    fun DialogClose() {
        _showAddEjercicioDialog.value = false
    }

    fun ShowDialog() {
        _showAddEjercicioDialog.value = true
    }

    fun onEjercicioCreated(ejercicioM: EjercicioM) {
        _showAddEjercicioDialog.value = false
        //_ejercicios.add(ejercicioM)
        viewModelScope.launch {
            addEjercicioUseCase(ejercicioM)
        }
        resetDialog()
    }


}


