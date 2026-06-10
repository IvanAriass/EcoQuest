package com.ecoquest.app.domain.usecase.eventos

import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.repository.EventoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventosUseCase @Inject constructor(
    private val eventoRepository: EventoRepository
) {
    operator fun invoke(): Flow<List<Evento>> = eventoRepository.getAll()
}
