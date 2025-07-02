package com.test.gymapptest.data.room.Entitys

import androidx.room.Embedded
import androidx.room.Relation

data class EjercicioTipoEnity (
    @Embedded val ejercicio: EjercicioEntity,

    @Relation(
        parentColumn = "fk_IdTipo",
        entityColumn = "idTipo"
    )
    val tipo: TipoEntity
)