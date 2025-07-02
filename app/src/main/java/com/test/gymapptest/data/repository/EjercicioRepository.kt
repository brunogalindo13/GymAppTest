package com.test.gymapptest.data.repository

import com.test.gymapptest.data.room.Daos.EjercicioDao
import com.test.gymapptest.data.room.Entitys.EjercicioEntity
import com.test.gymapptest.ui.model.EjercicioM
import com.test.gymapptest.ui.model.TipoM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EjercicioRepository @Inject constructor(private val ejercicioDao: EjercicioDao) {

    suspend fun add(EjercicioM: EjercicioM) {
        ejercicioDao.addEjercicio(
            EjercicioEntity(
                idEjercicio = 0,
                nombre = EjercicioM.nombre,
                descripcion = EjercicioM.descripcion,
                fk_IdTipo = EjercicioM.tipo.id,
                duracion = EjercicioM.duracion,
                distancia = EjercicioM.distancia,
                peso = EjercicioM.peso,
                serie = EjercicioM.serie,
                repeticion = EjercicioM.repeticion,
                gradual = EjercicioM.gradual,
                incdec = EjercicioM.incdec,
                orden = EjercicioM.orden
            )
        )
    }

    val getEjercicios: Flow<List<EjercicioM>> = ejercicioDao.getEjercicioTipo().map { list ->
        list.map {
            EjercicioM(
                id = it.ejercicio.idEjercicio,
                nombre = it.ejercicio.nombre,
                descripcion = it.ejercicio.descripcion,
                tipo = TipoM(
                    id = it.tipo.idTipo,
                    nombre = it.tipo.nombre,
                    descripcion = it.tipo.descripcion
                ),
                duracion = it.ejercicio.duracion,
                distancia = it.ejercicio.distancia,
                peso = it.ejercicio.peso,
                serie = it.ejercicio.serie,
                repeticion = it.ejercicio.repeticion,
            )
        }
    }
    fun getEjerciciosByName(name : String): Flow<List<EjercicioM>> = ejercicioDao.getEjercicioTipoByName(name).map { list ->
        list.map {
            EjercicioM(
                id = it.ejercicio.idEjercicio,
                nombre = it.ejercicio.nombre,
                descripcion = it.ejercicio.descripcion,
                tipo = TipoM(
                    id = it.tipo.idTipo,
                    nombre = it.tipo.nombre,
                    descripcion = it.tipo.descripcion
                ),
                duracion = it.ejercicio.duracion,
                distancia = it.ejercicio.distancia,
                peso = it.ejercicio.peso,
                serie = it.ejercicio.serie,
                repeticion = it.ejercicio.repeticion,
            )
        }
    }

}