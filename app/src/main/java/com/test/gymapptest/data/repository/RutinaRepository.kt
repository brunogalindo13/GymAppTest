package com.test.gymapptest.data.repository

import com.test.gymapptest.data.network.ApiService
import com.test.gymapptest.data.network.RutinaPojoItem
import com.test.gymapptest.data.room.Daos.RutinaDao
import com.test.gymapptest.data.room.Daos.RutinaEjercicioDao
import com.test.gymapptest.data.room.Entitys.RutinaEjercicioEntity
import com.test.gymapptest.data.room.Entitys.RutinaEntity
import com.test.gymapptest.ui.model.RutinaM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import javax.inject.Inject
import retrofit2.Response
import com.test.gymapptest.data.mappers.*

class RutinaRepository @Inject constructor(
    private val rutinaDao: RutinaDao,
    private val rutinaEjercicioDao: RutinaEjercicioDao,
    private val apiService: ApiService
) {
    suspend fun add(rutina: RutinaM) {
        val rutinaID = rutinaDao.addRutina(
            RutinaEntity(
                nombre = rutina.nombre,
                descripcion = rutina.descripcion,
                dia = rutina.diaAsignado,
                diaCreacion = 0
            )
        )
        if (rutinaID > 0 && rutina.ejercicios.isNotEmpty()){
            val rutinaEjercicios = rutina.ejercicios.map { ejercicio ->
                if (ejercicio.id <= 0){
                    throw Exception("Ejercicio sin id")
                }
                RutinaEjercicioEntity(
                    fkIdRutina = rutinaID,
                    fkIdEjercicio = ejercicio.id.toLong()
                )
            }
            if (rutinaEjercicios.isNotEmpty()){
                rutinaEjercicioDao.addRutinaEjercicios(rutinaEjercicios)
            }
        }
        else if (rutinaID <= 0){
            throw RuntimeException("No se pudo agregar la rutina")
        }

    }


    suspend fun getAllRutinasFromApi(): Flow<List<RutinaM>> = flow {
      try {
          val response: Response<List<RutinaPojoItem>> = apiService.getRutinas()

          if (response.isSuccessful){
              response.body()?.let { rutinasPojo ->
                  emit(rutinasPojo.toRutinaMList())
              } ?: throw IOException("Response body is null")

          }
          else{
              throw IOException("Error en la solicitud: ${response.code()} - ${response.message()}")
          }
      }
      catch (e: IOException){
          println("Error de red/IO al obtener rutinas: ${e.message}")
          throw e
      }
      catch (e: Exception){
            println("Excepción inesperada al obtener rutinas: ${e.message}")
            throw IOException("Error inesperado al obtener rutinas: ${e.message}")
      }


    }.flowOn(Dispatchers.IO)

}