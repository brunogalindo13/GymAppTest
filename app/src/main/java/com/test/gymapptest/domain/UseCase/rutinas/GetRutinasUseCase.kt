package com.test.gymapptest.domain.UseCase.rutinas

import com.test.gymapptest.data.repository.RutinaRepository
import com.test.gymapptest.ui.model.RutinaM
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRutinasUseCase @Inject constructor(private val rutinaRepository: RutinaRepository)
{
    suspend operator fun invoke(): Flow<List<RutinaM>> = rutinaRepository.getAllRutinasFromApi()
}