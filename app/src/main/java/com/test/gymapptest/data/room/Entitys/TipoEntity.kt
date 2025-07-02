package com.test.gymapptest.data.room.Entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TipoEntity (
    @PrimaryKey(autoGenerate = true)
    val idTipo: Int,
    val nombre: String,
    val descripcion: String
)