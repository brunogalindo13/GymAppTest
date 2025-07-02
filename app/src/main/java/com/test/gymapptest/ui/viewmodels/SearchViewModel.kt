package com.test.gymapptest.ui.viewmodels


import androidx.lifecycle.ViewModel
import com.test.gymapptest.domain.UseCase.ejercicio.GetEjerciciosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.test.gymapptest.domain.UseCase.ejercicio.GetEjerciciosByNameUseCase
import com.test.gymapptest.domain.UseCase.rutinas.GetRutinasUseCase
import com.test.gymapptest.ui.model.EjercicioM
import com.test.gymapptest.ui.model.RutinaM
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

enum class SearchType {
    EJERCICIOS,
    RUTINAS,
    NONE
}

sealed interface SearchUiState {
    object Idle : SearchUiState // No hay búsqueda activa o resultados
    object Loading : SearchUiState
    data class EjerciciosSuccess(val ejercicios: List<EjercicioM>) : SearchUiState
    data class RutinasSuccess(val rutinas: List<RutinaM>) : SearchUiState
    data class Error(val message: String) : SearchUiState
}

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private  val getEjerciciosUseCase: GetEjerciciosUseCase,
    private val getEjerciciosByNameUseCase: GetEjerciciosByNameUseCase,
    private val getRutinasUseCase: GetRutinasUseCase
): ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _currentSearchType = MutableStateFlow(SearchType.NONE) // que es lo que esta buscando
    val currentSearchType: StateFlow<SearchType> = _currentSearchType.asStateFlow()


    private val _searchResultsUiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val searchResultsUiState: StateFlow<SearchUiState> = _searchResultsUiState.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(300L) // Solo busca después de que el usuario deja de escribir por 300ms
                .distinctUntilChanged() // Solo si la query realmente cambió
                .collect { query ->
                    performSearch(query)
                }
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _searchResultsUiState.value = SearchUiState.Loading
            try {
                when (_currentSearchType.value) {
                    SearchType.EJERCICIOS -> {
                        val ejercicios = if (query.isBlank()) {
                            getEjerciciosUseCase()
                        } else {

                            getEjerciciosByNameUseCase(query)
                        }

                        ejercicios
                            .catch { e ->
                                _searchResultsUiState.value = SearchUiState.Error("Error al obtener ejercicios: ${e.message}")
                            }
                            .collect { ejerciciosList ->
                                _searchResultsUiState.value = SearchUiState.EjerciciosSuccess(ejerciciosList)
                            }
                    }
                    SearchType.RUTINAS -> {
                        val rutinas = if (query.isBlank()) {
                            getRutinasUseCase()
                        } else {
                            getRutinasUseCase()
                        }
                        rutinas
                            .catch { e ->
                                _searchResultsUiState.value = SearchUiState.Error("Error al obtener ejercicios: ${e.message}")
                            }
                            .collect { rutinaList ->
                                _searchResultsUiState.value = SearchUiState.RutinasSuccess(rutinaList)
                            }


                    }
                    SearchType.NONE -> {
                        _searchResultsUiState.value = SearchUiState.Idle
                    }
                }
            } catch (e: Exception) {
                _searchResultsUiState.value = SearchUiState.Error("Error en la búsqueda: ${e.message}")
            }


        }

    }
    fun onSearchTextChange(text: String) {
        _searchQuery.value = text
    }

    fun setCurrentSearchType(type: SearchType) {
        _currentSearchType.value = type

        if (_searchQuery.value.isNotEmpty()) {
            performSearch(_searchQuery.value)
        } else {
            performSearch("")
        }
    }
    fun onSearchConfirmed() {
        println("Búsqueda confirmada para ${_currentSearchType.value} con query: ${_searchQuery.value}")
    }


//    val iuStateListEjercicio: StateFlow<EjerciciosUiState> = getEjerciciosByNameUseCase(_searchText.value)
//        .map ( ::SuccessEjercicio )
//        .catch { EjerciciosUiState.Error(it) }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EjerciciosUiState.Loading)
//
//    fun onSearchTextChange(text: String) {
//        if (text.length >= 3){
//            _searchText.value = text
//        }
//    }



}