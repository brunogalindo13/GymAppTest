package com.test.gymapptest.data.room.Entitys

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "ejercicio_t"
, foreignKeys = [
        ForeignKey(
            entity = TipoEntity::class,
            parentColumns = ["idTipo"],
            childColumns = ["fk_IdTipo"]
        )
]
)
data class EjercicioEntity (
    @PrimaryKey(autoGenerate = true)
    val idEjercicio : Int,
    val nombre : String,
    val descripcion : String,
    val fk_IdTipo: Int,
    val duracion: Double,
    val distancia: Double,
    val peso: Double,
    val serie: Int,
    val repeticion: Int,
    val gradual: Boolean= false,
    val incdec: Double,
    val orden : Int
)