package com.test.gymapptest.data.room.Entitys

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "rutina_ejercicio_ref",
    primaryKeys = ["fkIdRutina", "fkIdEjercicio"],
    foreignKeys = [
        ForeignKey(
            entity = RutinaEntity::class,
            parentColumns = ["idRutina"],
            childColumns = ["fkIdRutina"],
            onDelete = ForeignKey.CASCADE,
        ), ForeignKey(
            entity = EjercicioEntity::class,
            parentColumns = ["idEjercicio"],
            childColumns = ["fkIdEjercicio"],
            onDelete = ForeignKey.CASCADE
        )
    ], indices = [Index(value = ["fkIdRutina"]), Index(value = ["fkIdEjercicio"])]
)
data class RutinaEjercicioEntity(
    val fkIdRutina: Long,
    val fkIdEjercicio: Long
)
