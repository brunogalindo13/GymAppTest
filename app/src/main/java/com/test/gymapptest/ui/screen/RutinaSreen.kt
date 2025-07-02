package com.test.gymapptest.ui.screen


import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.test.gymapptest.ui.model.RutinaM
import com.test.gymapptest.ui.viewmodels.SearchUiState
import com.test.gymapptest.ui.viewmodels.SearchViewModel

@Composable
fun RutinaScreen(searchViewModel: SearchViewModel) {


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
                is SearchUiState.RutinasSuccess -> {
                    LazyColumn {
                        items(state.rutinas) { rutina ->
                            RutinaCard(rutina)
                        }
                    }
                }
                is SearchUiState.Loading -> Text("Cargando rutinas...")
                is SearchUiState.Error -> Text(state.message)
                is SearchUiState.Idle -> Text("Realiza una búsqueda para ver rutinas o explora todas.")

                is SearchUiState.EjerciciosSuccess -> { }
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
                onClick =
                    {

                    }

            )
            {
                Text("Agregar Rutina")
            }

        }

    }

}

@Composable
fun RutinaCard(rutinaM: RutinaM)
{
    Spacer(modifier = Modifier.size(10.dp))
    Card(modifier = Modifier.fillMaxWidth().height(100.dp).padding(5.dp))
    {
        Column (modifier = Modifier.padding(5.dp))
        {
            Row(modifier = Modifier.background(Color.Red).fillMaxWidth().weight(.3f), verticalAlignment = Alignment.CenterVertically){
                Text(rutinaM.nombre)
                Spacer(Modifier.weight(1f))
                if (rutinaM.isFromServer){
                    Text("servidor", fontStyle = FontStyle.Italic, color = Color.Green)
                }

            }
            Row(modifier = Modifier.background(Color.Red).fillMaxSize().weight(.7f))
            {
                Box(modifier = Modifier.weight(.7f).background(Color.Blue).fillMaxSize()) { Text(rutinaM.descripcion)  }
                Box(modifier = Modifier.weight(.3f).background(Color.Green).fillMaxSize()) { Text("# Ejercicios: ${rutinaM.ejercicios.size}") }

            }
        }
    }
}



