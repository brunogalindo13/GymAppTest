package com.test.gymapptest.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.gymapptest.domain.UseCase.tipo.*
import com.test.gymapptest.ui.model.TipoM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TipoViewModel @Inject constructor(
    private val addtipoUseCase: AddTiposUseCase
): ViewModel() {

    private val _showAddTipoDialog = MutableLiveData<Boolean>()
    val showAddTipoDialog: LiveData<Boolean> = _showAddTipoDialog



    private val _uiStatte = MutableStateFlow(TipoM())
    val uiState: StateFlow<TipoM> = _uiStatte.asStateFlow()

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

    private fun updateToSend() {
        _uiStatte.update {
            it.copy(
                isOk = it.nombreError == null && it.nombre.isNotBlank()
            )
        }
    }
    fun sendForm(){
        val nombreActual = _uiStatte.value.nombre

        val errornombre = _uiStatte.value.nombreError

        if (errornombre != null) {
            _uiStatte.update {
                it.copy(nombreError = errornombre)
            }
            updateToSend()
            return
        }
        if (_uiStatte.value.isOk){
            onTipoCreated(uiState.value)

        }

    }


    fun DialogClose() {
       _showAddTipoDialog.value = false
    }
    fun ShowDialog() {
        _showAddTipoDialog.value = true
    }
    fun resetDialog() {
        _uiStatte.value = TipoM()
    }

    fun onTipoCreated(tipoM: TipoM) {
        _showAddTipoDialog.value = false
        //_tipos.add(tipoM)
        viewModelScope.launch {
            addtipoUseCase(tipoM)
        }
        resetDialog()



    }


}