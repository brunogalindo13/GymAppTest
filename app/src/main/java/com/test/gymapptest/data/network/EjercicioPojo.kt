package com.test.gymapptest.data.network

import com.google.gson.annotations.SerializedName



data class EjercicioPojo(
    @SerializedName("descripcion")
    val descripcion: String,
    @SerializedName("distancia")
    val distancia: Double,
    @SerializedName("duracion")
    val duracion: Double,
    @SerializedName("fk_idTipo")
    val fk_idTipo: Int,
    @SerializedName("gradual")
    val gradual: Int,
    @SerializedName("idEjercicio")
    val idEjercicio: Int,
    @SerializedName("incdesc")
    val incdesc: Double,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("orden")
    val orden: Int,
    @SerializedName("peso")
    val peso: Double,
    @SerializedName("repeticion")
    val repeticion: Int,
    @SerializedName("serie")
    val serie: Int,
    @SerializedName("tipo")
    val tipo: TipoPojo
)