package com.test.gymapptest.data.network

import com.google.gson.annotations.SerializedName

data class TipoPojo(
    @SerializedName("descripcion")
    val descripcion: String,
    @SerializedName("idTipo")
    val idTipo: Int,
    @SerializedName("nombre")
    val nombre: String
)