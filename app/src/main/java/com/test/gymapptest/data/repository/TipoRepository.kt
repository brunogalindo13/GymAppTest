package com.test.gymapptest.data.repository

import com.test.gymapptest.data.room.Daos.TipoDao
import com.test.gymapptest.data.room.Entitys.TipoEntity
import com.test.gymapptest.ui.model.TipoM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TipoRepository @Inject constructor(private val tipoDao: TipoDao) {
    val tipos: Flow<List<TipoM>> =
        tipoDao.getAllTipos().map { list -> list.map { TipoM(it.idTipo, it.nombre, it.descripcion) } }

    suspend fun add(tipoM: TipoM) {
        tipoDao.addTipo(TipoEntity(idTipo = 0, nombre = tipoM.nombre, descripcion = tipoM.descripcion))
    }
}



