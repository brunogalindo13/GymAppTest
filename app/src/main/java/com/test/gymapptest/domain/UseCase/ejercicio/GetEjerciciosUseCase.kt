package com.test.gymapptest.domain.UseCase.ejercicio

import com.test.gymapptest.data.repository.EjercicioRepository
import com.test.gymapptest.ui.model.EjercicioM
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEjerciciosUseCase @Inject constructor(private val ejercicioRepository: EjercicioRepository)
{
    operator fun invoke(): Flow<List<EjercicioM>> = ejercicioRepository.getEjercicios
}