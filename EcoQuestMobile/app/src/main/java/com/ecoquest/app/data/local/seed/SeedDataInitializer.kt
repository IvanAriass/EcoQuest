package com.ecoquest.app.data.local.seed

import com.ecoquest.app.data.local.dao.ComunidadDao
import com.ecoquest.app.data.local.dao.EventoDao
import com.ecoquest.app.data.local.dao.UsuarioComunidadDao
import com.ecoquest.app.data.local.dao.UsuarioDao
import com.ecoquest.app.data.local.dao.UsuarioEventoDao
import com.ecoquest.app.data.local.entity.ComunidadEntity
import com.ecoquest.app.data.local.entity.EventoEntity
import com.ecoquest.app.data.local.entity.UsuarioComunidadEntity
import com.ecoquest.app.data.local.entity.UsuarioEntity
import com.ecoquest.app.data.local.entity.UsuarioEventoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeedDataInitializer @Inject constructor(
    private val eventoDao: EventoDao,
    private val comunidadDao: ComunidadDao,
    private val usuarioDao: UsuarioDao,
    private val usuarioEventoDao: UsuarioEventoDao,
    private val usuarioComunidadDao: UsuarioComunidadDao
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun initialize() {
        scope.launch {
            val existing = comunidadDao.getAll().firstOrNull()
            if (existing.isNullOrEmpty()) {
                seedData()
            }
        }
    }

    private suspend fun seedData() {
        val comunidades = listOf(
            ComunidadEntity(id = 1, nombre = "EcoGuardianes", descripcion = "Protegiendo el medio ambiente entre todos. Únete a nuestras actividades de limpieza y concienciación.", creadorId = 1),
            ComunidadEntity(id = 2, nombre = "ReciclaYa", descripcion = "Comunidad dedicada al reciclaje y la economía circular. Aprende a reducir tus residuos.", creadorId = 1),
            ComunidadEntity(id = 3, nombre = "BosquesVivos", descripcion = "Reforestación urbana y cuidado de espacios verdes. Plantamos futuro.", creadorId = 1)
        )
        comunidadDao.insertAllIfAbsent(comunidades)

        val eventos = listOf(
            EventoEntity(id = 1, nombre = "Limpieza de Playa", descripcion = "Jornada de limpieza en la playa de la ciudad. Recogeremos residuos y separaremos para reciclar.", fechaHora = "15 Jun 2026, 10:00", ubicacion = "Playa del Carmen", imagen = "", estado = "noticia", comunidadId = 1, creadorId = 1),
            EventoEntity(id = 2, nombre = "Taller de Reciclaje", descripcion = "Aprende técnicas básicas de reciclaje y reutilización de materiales cotidianos.", fechaHora = "20 Jun 2026, 17:00", ubicacion = "Centro Cultural", imagen = "", estado = "urgente", comunidadId = 2, creadorId = 1),
            EventoEntity(id = 3, nombre = "Plantación de Árboles", descripcion = "Reforestación del parque urbano. Plantaremos 50 árboles autóctonos.", fechaHora = "25 Jun 2026, 09:00", ubicacion = "Parque Central", imagen = "", estado = "", comunidadId = 3, creadorId = 1),
            EventoEntity(id = 4, nombre = "Charla sobre Cambio Climático", descripcion = "Expertos hablarán sobre el impacto del cambio climático y cómo podemos actuar.", fechaHora = "30 Jun 2026, 18:30", ubicacion = "Auditorio Municipal", imagen = "", estado = "", comunidadId = 1, creadorId = 1)
        )
        eventoDao.insertAllIfAbsent(eventos)

        usuarioDao.upsert(
            UsuarioEntity(id = 1, nombreUsuario = "ecoquest_user", nombre = "Usuario", apellido = "EcoQuest", email = "user@ecoquest.com", descripcion = "Amante de la naturaleza")
        )

        usuarioEventoDao.upsertAll(
            listOf(
                UsuarioEventoEntity(usuarioId = 1, eventoId = 1),
                UsuarioEventoEntity(usuarioId = 1, eventoId = 2),
                UsuarioEventoEntity(usuarioId = 1, eventoId = 3),
                UsuarioEventoEntity(usuarioId = 1, eventoId = 4)
            )
        )

        usuarioComunidadDao.upsertAll(
            listOf(
                UsuarioComunidadEntity(usuarioId = 1, comunidadId = 1, rol = "miembro"),
                UsuarioComunidadEntity(usuarioId = 1, comunidadId = 2, rol = "miembro"),
                UsuarioComunidadEntity(usuarioId = 1, comunidadId = 3, rol = "miembro")
            )
        )
    }
}
