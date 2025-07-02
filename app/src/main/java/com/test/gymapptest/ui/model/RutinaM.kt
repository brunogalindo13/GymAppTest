package com.test.gymapptest.ui.model

data class RutinaM(
    val idRutina : Int,
    val nombre : String,
    val descripcion : String,
    val ejercicios : List<EjercicioM>,
    val diaAsignado : Int,
    val diaCreacion : Long,
    val isFromServer : Boolean = false


)
