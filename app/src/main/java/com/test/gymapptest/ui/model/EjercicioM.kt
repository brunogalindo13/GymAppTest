package com.test.gymapptest.ui.model



data class EjercicioM(
    val id: Int = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val tipo: TipoM = TipoM(),
    val duracion: Double = 0.0,
    val distancia: Double = 0.0,
    val peso: Double = 0.0,
    val serie: Int = 0,
    val repeticion: Int = 0,
    val gradual: Boolean = false,
    val incdec: Double = 0.0,
    val orden: Int = 0,

    val nombreError: String? = null,
    val isOk: Boolean = false,
    val mensajeOk: String? = null,
)