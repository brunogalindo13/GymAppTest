package com.test.gymapptest.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.test.gymapptest.ui.model.EjercicioM
import com.test.gymapptest.ui.viewmodels.EjercicioViewModel
import com.test.gymapptest.ui.viewmodels.SearchUiState


import com.test.gymapptest.ui.viewmodels.SearchViewModel

@Composable
fun EjercicioScrenn(searchViewModel: SearchViewModel, ejercicioViewModel: EjercicioViewModel= hiltViewModel()) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val showDialogAddEjercicio: Boolean by ejercicioViewModel.showAddEjercicioDialog.observeAsState(
        false
    )

    val searchUiState by searchViewModel.searchResultsUiState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Box(
            modifier = Modifier
                .weight(.9f)
                .fillMaxSize()

        ) {


            when (val state = searchUiState) {
                is SearchUiState.EjerciciosSuccess -> {
                    var ejerciciosList =
                       state.ejercicios
                    if (ejerciciosList.isNotEmpty()) {
                        EjercicoList(ejerciciosList)
                    }
                }
                is SearchUiState.Loading -> Text("Cargando ejercicios...")
                is SearchUiState.Error -> Text(state.message)
                is SearchUiState.Idle -> Text("Realiza una búsqueda para ver ejercicios o explora todos.")
                is SearchUiState.RutinasSuccess -> {  }
                SearchUiState.Idle -> {}
                SearchUiState.Loading -> {}
            }




        }
        Box(
            modifier = Modifier
                .weight(.1f)
                .fillMaxSize()
                .padding(16.dp)
        ) {


            Button(
                modifier = Modifier.fillMaxSize(),
                onClick = { ejercicioViewModel.ShowDialog() }

            )
            {
                Text("Agregar Ejercico")
            }

            if (showDialogAddEjercicio) {

                AddEjercicioScreen(
                    true,
                    onDismiss = { ejercicioViewModel.DialogClose() },
                )
            }
        }

    }

}

@Composable
fun EjercicoList(ejercicios: List<EjercicioM>) {
    val ejerciosEx: List<EjercicioM> = ejercicios
    LazyColumn {
        items(items = ejerciosEx)
        {
            Spacer(modifier = Modifier.size(5.dp))
            ItemList(it)

        }
    }
}

@Composable
fun ItemList(ejercio: EjercicioM) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .clickable(enabled = true, onClick = {
                Toast.makeText(
                    context,
                    "Ejercicio ${ejercio.id}",
                    Toast.LENGTH_SHORT
                ).show()
            })
            .fillMaxWidth()
            .height(100.dp),
        elevation = CardDefaults.cardElevation(),
        shape = CardDefaults.elevatedShape
    )
    {
        Row(modifier = Modifier.padding(5.dp))
        {
            Column(
                modifier = Modifier
                    .weight(.5f)
                    .padding(start = 20.dp)
            ) {
                Text(ejercio.nombre, fontStyle = FontStyle.Normal, fontWeight = FontWeight.Bold)
                Text(ejercio.descripcion, fontStyle = FontStyle.Italic)
            }
            Spacer(
                modifier = Modifier
                    .size(5.dp)
                    .padding(start = 20.dp)
            )
            Column(modifier = Modifier.weight(.5f)) {
                Text(ejercio.tipo.nombre)
            }
        }


    }


}

