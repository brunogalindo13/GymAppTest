package com.test.gymapptest.data.room.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.test.gymapptest.data.room.Entitys.RutinaEjerciciosRLt
import com.test.gymapptest.data.room.Entitys.RutinaEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RutinaDao {

    @Insert
    suspend fun addRutina(rutina: RutinaEntity): Long

    @Transaction
    @Query("SELECT * FROM RutinaEntity WHERE nombre LIKE '%' || :nombre || '%' COLLATE NOCASE")
    fun getEjercicioTipoByName(nombre: String): Flow<List<RutinaEjerciciosRLt>>

    @Transaction
    @Query("SELECT * FROM RutinaEntity")
    fun getAllRutinas(): Flow<List<RutinaEjerciciosRLt>>

}