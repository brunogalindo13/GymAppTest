package com.test.gymapptest.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.gymapptest.ui.viewmodels.TipoViewModel

@Composable
fun TipoDialogMn(
    show: Boolean,
    onDismiss: () -> Unit,
    tipoViewModel: TipoViewModel = hiltViewModel()
) {


    val uiState by tipoViewModel.uiState.collectAsState()




    if (show) {
        androidx.compose.ui.window.Dialog(onDismissRequest = { onDismiss() })
        {
            MaterialTheme {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                            text = "Agregar Tipo",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        TextField(
                            value = uiState.nombre,
                            onValueChange = { tipoViewModel.onNombreChange(it) },
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
                        Spacer(Modifier.height(10.dp))
                        TextField(
                            value = uiState.descripcion,
                            onValueChange = { tipoViewModel.onDescripcionChange(it) },
                            label = { Text("Descripcion") }, singleLine = true
                        )

                        Spacer(Modifier.height(10.dp))

                        Button(
                            onClick = {
                                tipoViewModel.sendForm()
                            }
                        ) { Text("Agregar Tipo") }


                    }
                }
            }

        }

    }
}