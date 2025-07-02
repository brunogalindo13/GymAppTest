package com.test.gymapptest.ui.model


data class TipoM (
    var id : Int =0,
    var nombre : String = "",
    var descripcion : String = "",
    var isSelected: Boolean = false,

    val nombreError: String? = null,
    val isOk: Boolean = false,
    val mensajeOk: String? = null,
)
