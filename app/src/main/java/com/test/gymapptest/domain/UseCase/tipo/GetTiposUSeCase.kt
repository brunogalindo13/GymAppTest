package com.test.gymapptest.domain.UseCase.tipo

import com.test.gymapptest.data.repository.TipoRepository
import com.test.gymapptest.ui.model.TipoM
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTiposUSeCase @Inject constructor(private val tipoRepository: TipoRepository) {
    operator fun invoke(): Flow<List<TipoM>> = tipoRepository.tipos
}