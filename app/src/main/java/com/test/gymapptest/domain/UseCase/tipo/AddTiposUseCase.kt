package com.test.gymapptest.domain.UseCase.tipo

import com.test.gymapptest.data.repository.TipoRepository
import com.test.gymapptest.ui.model.TipoM
import javax.inject.Inject

class AddTiposUseCase @Inject constructor(private val tipoRepository: TipoRepository)  {

    suspend operator fun invoke(tipoM: TipoM) {
        tipoRepository.add(tipoM)
    }

}