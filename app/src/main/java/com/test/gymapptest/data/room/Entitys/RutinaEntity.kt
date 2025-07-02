package com.test.gymapptest.data.room.Entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RutinaEntity(
    @PrimaryKey(autoGenerate = true)
    val idRutina: Long = 0,
    val nombre: String,
    val descripcion: String?,
    val dia: Int?,
    val diaCreacion: Long?
)
