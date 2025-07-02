package com.test.gymapptest.domain.UseCase.rutinas

import com.test.gymapptest.data.repository.RutinaRepository
import com.test.gymapptest.ui.model.RutinaM
import javax.inject.Inject

class AddRutinaUSeCAse @Inject constructor(private val rutinaRepository: RutinaRepository) {
    suspend operator fun invoke(rutina: RutinaM) {
        rutinaRepository.add(rutina)
    }


}