package com.test.gymapptest.domain.UseCase.ejercicio

import com.test.gymapptest.data.repository.EjercicioRepository
import com.test.gymapptest.ui.model.EjercicioM
import javax.inject.Inject

class AddEjercicioUseCase @Inject constructor(private val ejercicioRepository: EjercicioRepository){
    suspend operator fun invoke(ejercicioM: EjercicioM) {
        ejercicioRepository.add(ejercicioM)
    }

}