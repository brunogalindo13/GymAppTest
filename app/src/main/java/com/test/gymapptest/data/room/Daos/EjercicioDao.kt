package com.test.gymapptest.data.room.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.test.gymapptest.data.room.Entitys.EjercicioEntity
import com.test.gymapptest.data.room.Entitys.EjercicioTipoEnity
import kotlinx.coroutines.flow.Flow

@Dao
interface EjercicioDao {
    @Query("SELECT * FROM ejercicio_t")
    fun getAllEjercicios(): Flow<List<EjercicioEntity>>

    @Query("SELECT * FROM ejercicio_t WHERE idEjercicio = :id")
    fun getEjercicioById(id: Int): Flow<EjercicioEntity>

    @Query("SELECT * FROM ejercicio_t WHERE fk_IdTipo = :tipoId")
    fun getEjerciciosByTipo(tipoId: Int): Flow<List<EjercicioEntity>>

    @Insert
    suspend fun addEjercicio(ejercicio: EjercicioEntity)

    @Transaction
    @Query("SELECT * FROM ejercicio_t")
    fun getEjercicioTipo(): Flow<List<EjercicioTipoEnity>>

    @Transaction
    @Query("SELECT * FROM ejercicio_t WHERE nombre LIKE '%' || :nombre || '%' COLLATE NOCASE")
    fun getEjercicioTipoByName(nombre: String): Flow<List<EjercicioTipoEnity>>



}