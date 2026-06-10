package com.example.myapplication

import java.time.LocalDateTime

object MockData {

    // Mock de Roles
    val roles = listOf(
        Rol(1, "Administrador"),
        Rol(2, "Moderador"),
        Rol(3, "Miembro"),
        Rol(4, "Colaborador"),
        Rol(5, "Invitado")
    )

    // Mock de Niveles
    val niveles = listOf(
        Nivel(1, "Principiante", "Nivel inicial para nuevos usuarios", 0, "beginner_icon.png"),
        Nivel(2, "Intermedio", "Usuario con cierta experiencia", 100, "intermediate_icon.png"),
        Nivel(3, "Avanzado", "Usuario con amplia experiencia", 500, "advanced_icon.png"),
        Nivel(4, "Experto", "Usuario experto en la plataforma", 1000, "expert_icon.png"),
        Nivel(5, "Maestro", "Nivel máximo de experiencia", 2000, "master_icon.png")
    )

    // Mock de Productos
    val productos = listOf(
        Producto(1, "Camiseta Ecológica", "Camiseta 100% algodón orgánico", 250),
        Producto(2, "Taza Reutilizable", "Taza de cerámica con diseño ecológico", 150),
        Producto(3, "Kit de Jardinería", "Set completo para huerto urbano", 500),
        Producto(4, "Bolsa de Tela", "Bolsa reutilizable para compras", 100),
        Producto(5, "Libro Guía Verde", "Guía práctica de sostenibilidad", 300),
        Producto(6, "Compostera Doméstica", "Compostera para hogar", 750),
        Producto(7, "Panel Solar Portátil", "Panel solar pequeño para dispositivos", 1200)
    )

    // Mock de Retos
    val retos = listOf(
        Reto(1, "7 Días Sin Plástico", "Evita el uso de plásticos de un solo uso durante una semana", 200),
        Reto(2, "Huerto en Casa", "Cultiva al menos 3 vegetales en tu hogar", 350),
        Reto(3, "Transporte Verde", "Usa transporte sostenible por 30 días consecutivos", 500),
        Reto(4, "Zero Waste Semanal", "Reduce tu basura a lo mínimo indispensable", 400),
        Reto(5, "Ahorro de Agua", "Reduce tu consumo de agua en un 25% por un mes", 300),
        Reto(6, "Energía Renovable", "Compensa tu consumo eléctrico con energía verde", 600)
    )

    // Mock de Eventos
    val eventos = listOf(
        Evento(
            1,
            "Limpieza de Playa",
            "Playa Malvarrosa, Valencia",
            "Jornada de limpieza comunitaria de playa",
            "beach_cleanup.jpg",
            LocalDateTime.of(2024, 6, 15, 10, 0, 0)
        ),
        Evento(
            2,
            "Taller de Compostaje",
            "Centro Comunitario Central",
            "Aprende técnicas de compostaje doméstico",
            "composting_workshop.jpg",
            LocalDateTime.of(2024, 6, 22, 17, 30, 0)
        ),
        Evento(
            3,
            "Plantación de Árboles",
            "Parque Nacional Sierra",
            "Reforestación participativa de área degradada",
            "tree_planting.jpg",
            LocalDateTime.of(2024, 7, 5, 9, 0, 0)
        ),
        Evento(
            4,
            "Mercado Ecológico",
            "Plaza Mayor",
            "Mercado de productos locales y ecológicos",
            "eco_market.jpg",
            LocalDateTime.of(2024, 6, 30, 11, 0, 0)
        ),
        Evento(
            5,
            "Conferencia Sostenibilidad",
            "Auditorio Municipal",
            "Conferencia sobre desarrollo sostenible",
            "sustainability_conference.jpg",
            LocalDateTime.of(2024, 7, 12, 18, 30, 0)
        )
    )

    // Mock de Noticias
    val noticias = listOf(
        Noticia(
            1,
            "María González",
            "La comunidad alcanzó un récord de reciclaje este mes, superando las 2 toneladas de materiales recuperados.",
            "recycling_record.jpg",
            "Récord de Reciclaje Alcanzado"
        ),
        Noticia(
            2,
            "Carlos Ruiz",
            "Se inauguró el nuevo parque urbano con más de 500 árboles nativos plantados por la comunidad.",
            "new_park.jpg",
            "Nuevo Parque Urbano Inaugurado"
        ),
        Noticia(
            3,
            "Ana López",
            "Guía completa con 10 consejos prácticos para reducir tu huella de carbono desde casa.",
            "carbon_footprint.jpg",
            "Cómo Reducir tu Huella de Carbono"
        ),
        Noticia(
            4,
            "Pedro Martínez",
            "La iniciativa de huertos urbanos ha beneficiado a más de 200 familias este año.",
            "urban_gardens.jpg",
            "Huertos Urbanos Transforman Comunidades"
        ),
        Noticia(
            5,
            "Laura Sánchez",
            "Nuevo sistema de compostaje comunitario implementado en 5 barrios de la ciudad.",
            "community_compost.jpg",
            "Compostaje Comunitario en Expansión"
        )
    )

    // Mock de Comunidades (primero sin usuarios ni eventos)
    val comunidades = mutableListOf(
        Comunidad(
            1,
            "EcoValencia",
            "valencia_eco.jpg",
            "Comunidad dedicada a la sostenibilidad en Valencia y alrededores.",
            roles[0],
            mutableListOf(),
            mutableListOf()
        ),
        Comunidad(
            2,
            "Madrid Sostenible",
            "madrid_sustainable.jpg",
            "Iniciativas ecológicas y sostenibles en la capital.",
            roles[1],
            mutableListOf(),
            mutableListOf()
        ),
        Comunidad(
            3,
            "Barcelona Verde",
            "barcelona_green.jpg",
            "Promoviendo prácticas ecológicas en Barcelona.",
            roles[2],
            mutableListOf(),
            mutableListOf()
        ),
        Comunidad(
            4,
            "Andalucía Natural",
            "andalucia_natural.jpg",
            "Conservación del medio ambiente en Andalucía.",
            roles[3],
            mutableListOf(),
            mutableListOf()
        ),
        Comunidad(
            5,
            "Canarias Eco",
            "canarias_eco.jpg",
            "Protección del entorno natural canario.",
            roles[4],
            mutableListOf(),
            mutableListOf()
        )
    )

    // Mock de Usuarios
    val usuarios = mutableListOf(
        Usuario(
            usuarioId = 1,
            contraseña = "admin123",
            admin = true,
            nombreUsuario = "admin_maria",
            nombre = "María",
            apellidos = "González Pérez",
            correo = "maria.gonzalez@email.com",
            imagen = "maria_profile.jpg",
            descripcion = "Administradora de la plataforma y activista ambiental.",
            edad = LocalDateTime.of(1990, 5, 15, 0, 0),
            comunidades = mutableListOf(),
            puntos = 1250,
            puntosExperiencia = 1800,
            nivel = niveles[4],
            productos = mutableListOf()
        ),
        Usuario(
            usuarioId = 2,
            contraseña = "pass456",
            admin = false,
            nombreUsuario = "carlos_eco",
            nombre = "Carlos",
            apellidos = "Ruiz Martínez",
            correo = "carlos.ruiz@email.com",
            imagen = "carlos_profile.jpg",
            descripcion = "Amante de la naturaleza y entusiasta del reciclaje.",
            edad = LocalDateTime.of(1985, 8, 22, 0, 0),
            comunidades = mutableListOf(),
            puntos = 850,
            puntosExperiencia = 1100,
            nivel = niveles[3],
            productos = mutableListOf()
        ),
        Usuario(
            usuarioId = 3,
            contraseña = "user789",
            admin = false,
            nombreUsuario = "ana_verde",
            nombre = "Ana",
            apellidos = "López Sánchez",
            correo = "ana.lopez@email.com",
            imagen = "ana_profile.jpg",
            descripcion = "Especialista en huertos urbanos y agricultura sostenible.",
            edad = LocalDateTime.of(1992, 3, 10, 0, 0),
            comunidades = mutableListOf(),
            puntos = 650,
            puntosExperiencia = 900,
            nivel = niveles[2],
            productos = mutableListOf()
        ),
        Usuario(
            usuarioId = 4,
            contraseña = "green123",
            admin = false,
            nombreUsuario = "pedro_nature",
            nombre = "Pedro",
            apellidos = "Martín García",
            correo = "pedro.martin@email.com",
            imagen = "pedro_profile.jpg",
            descripcion = "Promotor de energías renovables y movilidad sostenible.",
            edad = LocalDateTime.of(1988, 11, 30, 0, 0),
            comunidades = mutableListOf(),
            puntos = 450,
            puntosExperiencia = 600,
            nivel = niveles[2],
            productos = mutableListOf()
        ),
        Usuario(
            usuarioId = 5,
            contraseña = "nature456",
            admin = false,
            nombreUsuario = "laura_planet",
            nombre = "Laura",
            apellidos = "Sánchez Ruiz",
            correo = "laura.sanchez@email.com",
            imagen = "laura_profile.jpg",
            descripcion = "Educadora ambiental con enfoque en comunidades.",
            edad = LocalDateTime.of(1995, 7, 18, 0, 0),
            comunidades = mutableListOf(),
            puntos = 300,
            puntosExperiencia = 350,
            nivel = niveles[1],
            productos = mutableListOf()
        )
    )

    // Mock de UsuarioComunidad
    val usuarioComunidades = listOf(
        UsuarioComunidad(1, 1, 1, roles[0]), // María es Admin en EcoValencia
        UsuarioComunidad(2, 1, 2, roles[1]), // María es Moderador en Madrid Sostenible
        UsuarioComunidad(3, 2, 1, roles[2]), // Carlos es Miembro en EcoValencia
        UsuarioComunidad(4, 3, 1, roles[3]), // Ana es Colaborador en EcoValencia
        UsuarioComunidad(5, 3, 3, roles[2]), // Ana es Miembro en Barcelona Verde
        UsuarioComunidad(6, 4, 2, roles[2]), // Pedro es Miembro en Madrid Sostenible
        UsuarioComunidad(7, 5, 4, roles[2]), // Laura es Miembro en Andalucía Natural
        UsuarioComunidad(8, 5, 5, roles[3])  // Laura es Colaborador en Canarias Eco
    )

    // Mock de UsuarioEvento
    val usuarioEventos = listOf(
        UsuarioEvento(1, 1, 1, LocalDateTime.of(2024, 6, 10, 14, 30, 0), true),
        UsuarioEvento(2, 1, 2, LocalDateTime.of(2024, 6, 18, 16, 45, 0), false),
        UsuarioEvento(3, 2, 1, LocalDateTime.of(2024, 6, 11, 10, 15, 0), true),
        UsuarioEvento(4, 3, 2, LocalDateTime.of(2024, 6, 20, 12, 0, 0), true),
        UsuarioEvento(5, 3, 3, LocalDateTime.of(2024, 7, 1, 11, 20, 0), true),
        UsuarioEvento(6, 4, 4, LocalDateTime.of(2024, 6, 25, 15, 30, 0), true),
        UsuarioEvento(7, 5, 5, LocalDateTime.of(2024, 7, 5, 18, 0, 0), false)
    )

    // Mock de UsuarioReto (actualizado con la estructura correcta)
    val usuarioRetos = mutableListOf<UsuarioReto>()

    init {
        // Inicializar UsuarioReto con la estructura correcta
        usuarioRetos.addAll(listOf(
            UsuarioReto(1, 1, 1, true, LocalDateTime.of(2024, 5, 15, 10, 0, 0)),
            UsuarioReto(2, 1, 2, true, LocalDateTime.of(2024, 4, 20, 14, 30, 0)),
            UsuarioReto(3, 2, 1, true, LocalDateTime.of(2024, 5, 10, 9, 15, 0)),
            UsuarioReto(4, 2, 3, false, LocalDateTime.MIN),
            UsuarioReto(5, 3, 2, true, LocalDateTime.of(2024, 6, 1, 16, 45, 0)),
            UsuarioReto(6, 4, 4, true, LocalDateTime.of(2024, 5, 25, 11, 20, 0)),
            UsuarioReto(7, 5, 5, false, LocalDateTime.MIN)
        ))
        
        // Establecer relaciones
        initializeRelationships()
    }

    // Función para inicializar relaciones
    fun initializeRelationships() {
        // Asignar productos a usuarios
        usuarios[0].productos.addAll(listOf(productos[0], productos[2], productos[6]))
        usuarios[1].productos.addAll(listOf(productos[1], productos[4]))
        usuarios[2].productos.add(productos[3])
        usuarios[3].productos.add(productos[5])
        
        // Asignar eventos a comunidades
        comunidades[0].eventos.addAll(listOf(eventos[0], eventos[1]))
        comunidades[1].eventos.add(eventos[2])
        comunidades[2].eventos.add(eventos[3])
        comunidades[3].eventos.add(eventos[4])
        
        // Asignar usuarios a comunidades (basado en UsuarioComunidad)
        usuarioComunidades.forEach { uc ->
            val usuario = usuarios.find { it.usuarioId == uc.usuarioId }
            val comunidad = comunidades.find { it.comunidadId == uc.comunidadId }
            
            if (usuario != null && comunidad != null) {
                // Añadir usuario a comunidad
                if (!comunidad.usuarios.contains(usuario)) {
                    comunidad.usuarios.add(usuario)
                }
                
                // Añadir comunidad a usuario
                if (!usuario.comunidades.contains(comunidad)) {
                    usuario.comunidades.add(comunidad)
                }
            }
        }
    }

    // Funciones de utilidad
    fun getUsuarioById(id: Int): Usuario? {
        return usuarios.find { it.usuarioId == id }
    }

    fun getEventosProximos(): List<Evento> {
        val ahora = LocalDateTime.now()
        return eventos.filter { it.fecha.isAfter(ahora) }
            .sortedBy { it.fecha }
    }

    fun getUsuariosPorComunidad(comunidadId: Int): List<Usuario> {
        return usuarios.filter { usuario ->
            usuario.comunidades.any { it.comunidadId == comunidadId }
        }
    }

    fun getRetosCompletadosPorUsuario(usuarioId: Int): List<Reto> {
        val retosCompletados = mutableListOf<Reto>()
        val usuarioRetosFiltrados = usuarioRetos.filter { it.usuarioId == usuarioId && it.completado }
        
        usuarioRetosFiltrados.forEach { ur ->
            val reto = retos.find { it.retoId == ur.retoId }
            reto?.let { retosCompletados.add(it) }
        }
        
        return retosCompletados
    }

    fun getEventosPorComunidad(comunidadId: Int): List<Evento> {
        return eventos.filter { evento ->
            comunidades.find { it.comunidadId == comunidadId }
                ?.eventos?.any { it.eventoId == evento.eventoId } == true
        }
    }

    fun getProductosDisponiblesParaUsuario(usuarioId: Int): List<Producto> {
        val usuario = getUsuarioById(usuarioId)
        return if (usuario != null) {
            productos.filter { producto ->
                producto.puntos <= usuario.puntos && 
                !usuario.productos.any { it.productoId == producto.productoId }
            }
        } else {
            emptyList()
        }
    }

    fun getUsuarioConMasPuntos(): Usuario? {
        return usuarios.maxByOrNull { it.puntos }
    }

    fun getComunidadMasActiva(): Comunidad? {
        return comunidades.maxByOrNull { it.usuarios.size }
    }
}

// Clase UsuarioReto actualizada para Kotlin
data class UsuarioReto(
    var usuarioRetoId: Int,
    var usuarioId: Int,
    var retoId: Int,
    var completado: Boolean,
    var fechaCompletado: LocalDateTime
) {
    constructor() : this(0, 0, 0, false, LocalDateTime.now())
}