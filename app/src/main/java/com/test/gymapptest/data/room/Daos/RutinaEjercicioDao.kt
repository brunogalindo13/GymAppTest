package com.test.gymapptest.data.room.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.gymapptest.data.room.Entitys.RutinaEjercicioEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface RutinaEjercicioDao {

    @Query("SELECT * FROM rutina_ejercicio_ref WHERE fkIdRutina = :rutinaId")
    fun getRutinaEjercicios(rutinaId: Int): Flow<List<RutinaEjercicioEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRutinaEjercicio(rutinaEjercicio: RutinaEjercicioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRutinaEjercicios(rutinaEjercicio: List<RutinaEjercicioEntity>)



}