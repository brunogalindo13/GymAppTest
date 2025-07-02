package com.test.gymapptest.ui.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.test.gymapptest.ui.viewmodels.TipoViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.test.gymapptest.ui.states.TipoUiState
import com.test.gymapptest.ui.viewmodels.EjercicioViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEjercicioScreen(
    show: Boolean, onDismiss: () -> Unit,
    tipoViewModel: TipoViewModel = hiltViewModel(),
    addEjercicioViewModel: EjercicioViewModel = hiltViewModel(),
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    // valores para tipo
    var expanded by remember { mutableStateOf(false) }
    val uiStateList by produceState<TipoUiState>(
        initialValue = TipoUiState.Loading,
        key1 = lifecycle,
        key2 = addEjercicioViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            addEjercicioViewModel.iuStateList.collect { value = it }
        }
    }


    val showDialogAddTipo: Boolean by tipoViewModel.showAddTipoDialog.observeAsState(false)
    val uiState by addEjercicioViewModel.uiState.collectAsState()


    var selectedTextInDropdown by remember { mutableStateOf("") }
    LaunchedEffect(uiStateList)
    {
        if (uiStateList is TipoUiState.Success){
            val tipos = (uiStateList as TipoUiState.Success).tipos
            if (tipos.isNotEmpty()) {
                val primerTipo = tipos.first()
                if (uiState.tipo != primerTipo){
                    addEjercicioViewModel.onTipoChange(primerTipo)
                }
                selectedTextInDropdown = tipos.first().nombre
            }
            else{
                selectedTextInDropdown = ""
            }
        }
    }


    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            MaterialTheme {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Agregar Ejercicio",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        OutlinedTextField(
                            value = uiState.nombre,
                            onValueChange = { addEjercicioViewModel.onNombreChange(it) },
                            label = { Text("Nombre") },
                            singleLine = true,
                            isError = uiState.nombreError != null,
                            supportingText = {
                                uiState.nombreError?.let {
                                    Text(
                                        it,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        )
                        OutlinedTextField(
                            value = uiState.descripcion,
                            onValueChange = { addEjercicioViewModel.onDescripcionChange(it) },
                            label = { Text("Dscripcion") },
                            singleLine = true
                        )
                        when (uiStateList) {
                            is TipoUiState.Error -> {}
                            TipoUiState.Loading -> {}
                            is TipoUiState.Success -> {
                                var tiposList = (uiStateList as TipoUiState.Success).tipos
                                if (tiposList.isNotEmpty()) {
                                    ExposedDropdownMenuBox(
                                        expanded = expanded,
                                        onExpandedChange = { expanded = !expanded }) {
                                        OutlinedTextField(
                                            value = selectedTextInDropdown,
                                            onValueChange = {},
                                            readOnly = true,
                                            label = { Text("Selecciona una opción") },
                                            trailingIcon = {
                                                ExposedDropdownMenuDefaults.TrailingIcon(
                                                    expanded = expanded
                                                )
                                            },
                                            modifier = Modifier
                                                .menuAnchor()
                                                .fillMaxWidth()
                                        )
                                        ExposedDropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = { expanded = false }) {


                                            tiposList.forEach { itemSelected ->
                                                DropdownMenuItem(
                                                    text = { Text(itemSelected.nombre) },
                                                    onClick = {
                                                        addEjercicioViewModel.onTipoChange(
                                                            itemSelected
                                                        )
                                                        selectedTextInDropdown = itemSelected.nombre
                                                        expanded = false
                                                    })
                                            }
                                        }
                                    }
                                }

                            }
                        }



                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { tipoViewModel.ShowDialog() }) {
                            Text("Agregar Tipo Ejercicio")
                        }

                        if (showDialogAddTipo) {

                            TipoDialogMn(
                                showDialogAddTipo,
                                onDismiss = { tipoViewModel.DialogClose() }
                            )
                        }
                        rowDatosVarios(
                            value = uiState.peso,
                            "Agregar Peso?",
                            "Introduce Peso",
                            onValueChange = { addEjercicioViewModel.onPesoChange(it.toDouble()) })
                        rowDatosVarios(
                            uiState.repeticion,
                            "Agregar Repeticiones?",
                            "Numero de Repeticiones"
                        ) { addEjercicioViewModel.onRepeticionesChange(it.toInt()) }
                        rowDatosVarios(
                            uiState.serie,
                            "Agregar Series?",
                            "Numero de Series"
                        ) { addEjercicioViewModel.onSeriesChange(it.toInt()) }
                        rowDatosVarios(
                            uiState.duracion,
                            "Agregar Tiempo?",
                            "Tiempo en Minutos"
                        ) { addEjercicioViewModel.onDuracionChange(it.toDouble()) }

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick =
                                {
                                    addEjercicioViewModel.sendForm()
                                }
                        ) {
                            Text("Agregar Ejercicio")
                        }

                    }
                }
            }

        }
    }

}

@Composable
fun rowDatosVarios(
    value: Any,
    nombreSwitch: String,
    hintTf: String,
    onValueChange: (String) -> Unit
) {

    // Valores del text field peso
    var peso: Float? by remember { mutableStateOf(null) }
    var textValue by remember { mutableStateOf(peso.toString()) }
    var isError by remember { mutableStateOf(false) }

    //valores para los swich
    var isPEsoEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(peso) {
        // Si peso es null y textValue no está vacío, o si peso tiene un valor y textValue no lo refleja
        if (peso == null && textValue.isNotEmpty()) {

            textValue = ""
        } else if (peso != null) {
            val currentTextAsFloat = textValue.toFloatOrNull()
            if (currentTextAsFloat == null || currentTextAsFloat != peso) {
                textValue = peso.toString()
            }
        } else {
            if (textValue.isNotEmpty()) {
                //textValue = ""
            }
        }
    }

    Spacer(modifier = Modifier.size(20.dp))
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Box(
            modifier = Modifier

        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    isPEsoEnabled,
                    onCheckedChange = { isPEsoEnabled = it },
                    modifier = Modifier,
                )
                if (!isPEsoEnabled) {
                    Spacer(Modifier.size(10.dp))
                    Text(nombreSwitch)
                }

            }


        }
        Box(
            modifier = Modifier

        ) {
            if (isPEsoEnabled) {
                OutlinedTextField(
                    value = textValue,
                    onValueChange = { newText ->
                        textValue = newText
                        if (newText.isEmpty()) {
                            peso = null
                            isError = false
                        } else {
                            val newFloat = newText.toFloatOrNull()
                            if (newFloat != null) {
                                peso = newFloat
                                isError = false
                            } else {
                                if (newText == "." || newText == "-") {
                                    isError =
                                        false
                                    peso =
                                        null
                                } else {
                                    isError = true
                                    peso = null
                                }
                            }
                        }
                    }, label = { Text(hintTf) }, keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ), singleLine = true, isError = isError, supportingText = {
                        if (isError && textValue.isNotEmpty() && textValue != "." && textValue != "-") {
                            Text("Entrada inválida. Introduce un número.")
                        }
                    }, modifier = Modifier
                )
            }
        }


    }

}
