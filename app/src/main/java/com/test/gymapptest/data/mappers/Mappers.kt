package com.test.gymapptest.data.mappers

import com.test.gymapptest.data.network.EjercicioPojo
import com.test.gymapptest.data.network.RutinaPojoItem
import com.test.gymapptest.data.network.TipoPojo
import com.test.gymapptest.ui.model.EjercicioM
import com.test.gymapptest.ui.model.RutinaM
import com.test.gymapptest.ui.model.TipoM

fun RutinaPojoItem.toRutinaM(): RutinaM{
    val ejerciciosMapeados: List<EjercicioM> = ejerciciosIntermedios?.mapNotNull { intermedio -> intermedio.ejercicio.toEjercicioM() }?: emptyList()
    return RutinaM(
        idRutina = idRutina,
        nombre = nombre,
        descripcion = descripcion ?: "",
        ejercicios = ejerciciosMapeados,
        diaAsignado = dia?: 0,
        diaCreacion = timestampCreacion?: 0,
        isFromServer = true
    )

}
fun List<RutinaPojoItem>.toRutinaMList(): List<RutinaM> {
    return this.map { it.toRutinaM() }
}

fun EjercicioPojo.toEjercicioM(): EjercicioM {
    return EjercicioM(
        id = idEjercicio,
        nombre = nombre,
        descripcion = descripcion,
        tipo = this.tipo.toTipoM(),//.let { TipoM(id = 0, nombre = it.nombre, descripcion = it.descripcion)},
        orden = orden,
        repeticion = repeticion,
        serie = serie,
        peso = peso,
        gradual = if (gradual == 1) true else false,
        incdec = incdesc,
        duracion = duracion,
        distancia = distancia

    )
}
fun List<EjercicioPojo>.toEjercicioMList(): List<EjercicioM> {
    return this.map { it.toEjercicioM() }
}

fun TipoPojo.toTipoM(): TipoM {
    return TipoM(
        id = idTipo,
        nombre = nombre,
        descripcion = descripcion
    )
}