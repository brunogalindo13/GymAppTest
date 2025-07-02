package com.test.gymapptest.data.network

import com.google.gson.annotations.SerializedName

data class RutinaEjercicioIntermedioPojo(
    @SerializedName("fhIdRutina")
    val fhIdRutina: Int,
    @SerializedName("fkIdEjercicio")
    val fkIdEjercicio: Int,
    @SerializedName("ejercicio")
    val ejercicio: EjercicioPojo
)
