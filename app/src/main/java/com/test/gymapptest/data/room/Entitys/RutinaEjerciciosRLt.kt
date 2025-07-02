package com.test.gymapptest.data.room.Entitys

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class RutinaEjerciciosRLt(
    @Embedded val rutina: RutinaEntity,
    @Relation(
        parentColumn = "idRutina",
        entity = EjercicioEntity::class,
        entityColumn = "idEjercicio",
        associateBy = Junction(
            value = RutinaEjercicioEntity::class,
            parentColumn = "fkIdRutina",
            entityColumn = "fkIdEjercicio"
            )
    )
    val ejercicios: List<EjercicioEntity>
)
