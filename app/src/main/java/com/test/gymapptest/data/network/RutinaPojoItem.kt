package com.test.gymapptest.data.network

import com.google.gson.annotations.SerializedName

data class RutinaPojoItem(
    @SerializedName("idRutina")
    val idRutina: Int,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("descripcion")
    val descripcion: String?,
    @SerializedName("dia")
    val dia: Int?,
    @SerializedName("diacreacion")
    val timestampCreacion: Long?,
    @SerializedName("ejercicios")
    val ejerciciosIntermedios: List<RutinaEjercicioIntermedioPojo>?
)

