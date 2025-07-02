package com.test.gymapptest.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.graphics.vector.ImageVector

enum class RailDestination (
    val icon: ImageVector,
    val label: String,
    val route: String
){
    RUTINAS(Icons.Default.Refresh, "Rutinas", "rutinas"),
    EJERCICIOS(Icons.Filled.Add, "Ejercicios", "ejercicios"),

}
