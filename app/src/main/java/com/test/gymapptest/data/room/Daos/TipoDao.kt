package com.test.gymapptest.data.room.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.test.gymapptest.data.room.Entitys.TipoEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface TipoDao {
    @Query("SELECT * FROM TipoEntity")
    fun getAllTipos(): Flow<List<TipoEntity>>

    @Query("SELECT * FROM TipoEntity WHERE idTipo = :id")
    fun getTipoById(id: Int): Flow<TipoEntity>

    @Insert
    suspend fun addTipo(tipo: TipoEntity)

}