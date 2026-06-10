package com.example.myapplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockData {
    
    // Mock de Roles
    public static final List<Rol> ROLES = List.of(
        new Rol(1, "Administrador"),
        new Rol(2, "Moderador"),
        new Rol(3, "Miembro"),
        new Rol(4, "Colaborador"),
        new Rol(5, "Invitado")
    );
    
    // Mock de Niveles
    public static final List<Nivel> NIVELES = List.of(
        new Nivel(1, "Principiante", "Nivel inicial para nuevos usuarios", 0, "beginner_icon.png"),
        new Nivel(2, "Intermedio", "Usuario con cierta experiencia", 100, "intermediate_icon.png"),
        new Nivel(3, "Avanzado", "Usuario con amplia experiencia", 500, "advanced_icon.png"),
        new Nivel(4, "Experto", "Usuario experto en la plataforma", 1000, "expert_icon.png"),
        new Nivel(5, "Maestro", "Nivel máximo de experiencia", 2000, "master_icon.png")
    );
    
    // Mock de Productos
    public static final List<Producto> PRODUCTOS = List.of(
        new Producto(1, "Camiseta Ecológica", "Camiseta 100% algodón orgánico", 250),
        new Producto(2, "Taza Reutilizable", "Taza de cerámica con diseño ecológico", 150),
        new Producto(3, "Kit de Jardinería", "Set completo para huerto urbano", 500),
        new Producto(4, "Bolsa de Tela", "Bolsa reutilizable para compras", 100),
        new Producto(5, "Libro Guía Verde", "Guía práctica de sostenibilidad", 300),
        new Producto(6, "Compostera Doméstica", "Compostera para hogar", 750),
        new Producto(7, "Panel Solar Portátil", "Panel solar pequeño para dispositivos", 1200)
    );
    
    // Mock de Retos
    public static final List<Reto> RETOS = List.of(
        new Reto(1, "7 Días Sin Plástico", "Evita el uso de plásticos de un solo uso durante una semana", 200),
        new Reto(2, "Huerto en Casa", "Cultiva al menos 3 vegetales en tu hogar", 350),
        new Reto(3, "Transporte Verde", "Usa transporte sostenible por 30 días consecutivos", 500),
        new Reto(4, "Zero Waste Semanal", "Reduce tu basura a lo mínimo indispensable", 400),
        new Reto(5, "Ahorro de Agua", "Reduce tu consumo de agua en un 25% por un mes", 300),
        new Reto(6, "Energía Renovable", "Compensa tu consumo eléctrico con energía verde", 600)
    );
    
    // Mock de Eventos
    public static final List<Evento> EVENTOS = List.of(
        new Evento(1, "Limpieza de Playa", "Playa Malvarrosa, Valencia",
                  "Jornada de limpieza comunitaria de playa", "beach_cleanup.jpg",
                  LocalDateTime.of(2024, 6, 15, 10, 0, 0)),
        new Evento(2, "Taller de Compostaje", "Centro Comunitario Central",
                  "Aprende técnicas de compostaje doméstico", "composting_workshop.jpg",
                  LocalDateTime.of(2024, 6, 22, 17, 30, 0)),
        new Evento(3, "Plantación de Árboles", "Parque Nacional Sierra",
                  "Reforestación participativa de área degradada", "tree_planting.jpg",
                  LocalDateTime.of(2024, 7, 5, 9, 0, 0)),
        new Evento(4, "Mercado Ecológico", "Plaza Mayor",
                  "Mercado de productos locales y ecológicos", "eco_market.jpg",
                  LocalDateTime.of(2024, 6, 30, 11, 0, 0)),
        new Evento(5, "Conferencia Sostenibilidad", "Auditorio Municipal",
                  "Conferencia sobre desarrollo sostenible", "sustainability_conference.jpg",
                  LocalDateTime.of(2024, 7, 12, 18, 30, 0))
    );
    
    // Mock de Noticias
    public static final List<Noticia> NOTICIAS = List.of(
        new Noticia(1, "María González",
                   "La comunidad alcanzó un récord de reciclaje este mes, superando las 2 toneladas de materiales recuperados.",
                   "recycling_record.jpg", "Récord de Reciclaje Alcanzado"),
        new Noticia(2, "Carlos Ruiz",
                   "Se inauguró el nuevo parque urbano con más de 500 árboles nativos plantados por la comunidad.",
                   "new_park.jpg", "Nuevo Parque Urbano Inaugurado"),
        new Noticia(3, "Ana López",
                   "Guía completa con 10 consejos prácticos para reducir tu huella de carbono desde casa.",
                   "carbon_footprint.jpg", "Cómo Reducir tu Huella de Carbono"),
        new Noticia(4, "Pedro Martínez",
                   "La iniciativa de huertos urbanos ha beneficiado a más de 200 familias este año.",
                   "urban_gardens.jpg", "Huertos Urbanos Transforman Comunidades"),
        new Noticia(5, "Laura Sánchez",
                   "Nuevo sistema de compostaje comunitario implementado en 5 barrios de la ciudad.",
                   "community_compost.jpg", "Compostaje Comunitario en Expansión")
    );
    
    // Mock de Comunidades (primero sin usuarios ni eventos)
    public static final List<Comunidad> COMUNIDADES = new ArrayList<>(List.of(
        new Comunidad(1, "EcoValencia", "valencia_eco.jpg",
                     "Comunidad dedicada a la sostenibilidad en Valencia y alrededores.", ROLES.get(0)),
        new Comunidad(2, "Madrid Sostenible", "madrid_sustainable.jpg",
                     "Iniciativas ecológicas y sostenibles en la capital.", ROLES.get(1)),
        new Comunidad(3, "Barcelona Verde", "barcelona_green.jpg",
                     "Promoviendo prácticas ecológicas en Barcelona.", ROLES.get(2)),
        new Comunidad(4, "Andalucía Natural", "andalucia_natural.jpg",
                     "Conservación del medio ambiente en Andalucía.", ROLES.get(3)),
        new Comunidad(5, "Canarias Eco", "canarias_eco.jpg",
                     "Protección del entorno natural canario.", ROLES.get(4))
    ));
    
    // Mock de Usuarios
    public static final List<Usuario> USUARIOS = new ArrayList<>(List.of(
        new Usuario(1, "admin123", true, "admin_maria", "María", "González Pérez",
                   "maria.gonzalez@email.com", "maria_profile.jpg",
                   "Administradora de la plataforma y activista ambiental.",
                   LocalDateTime.of(1990, 5, 15, 0, 0), 1250, 1800, NIVELES.get(4)),
        new Usuario(2, "pass456", false, "carlos_eco", "Carlos", "Ruiz Martínez",
                   "carlos.ruiz@email.com", "carlos_profile.jpg",
                   "Amante de la naturaleza y entusiasta del reciclaje.",
                   LocalDateTime.of(1985, 8, 22, 0, 0), 850, 1100, NIVELES.get(3)),
        new Usuario(3, "user789", false, "ana_verde", "Ana", "López Sánchez",
                   "ana.lopez@email.com", "ana_profile.jpg",
                   "Especialista en huertos urbanos y agricultura sostenible.",
                   LocalDateTime.of(1992, 3, 10, 0, 0), 650, 900, NIVELES.get(2)),
        new Usuario(4, "green123", false, "pedro_nature", "Pedro", "Martín García",
                   "pedro.martin@email.com", "pedro_profile.jpg",
                   "Promotor de energías renovables y movilidad sostenible.",
                   LocalDateTime.of(1988, 11, 30, 0, 0), 450, 600, NIVELES.get(2)),
        new Usuario(5, "nature456", false, "laura_planet", "Laura", "Sánchez Ruiz",
                   "laura.sanchez@email.com", "laura_profile.jpg",
                   "Educadora ambiental con enfoque en comunidades.",
                   LocalDateTime.of(1995, 7, 18, 0, 0), 300, 350, NIVELES.get(1))
    ));
    
    // Mock de UsuarioComunidad
    public static final List<UsuarioComunidad> USUARIO_COMUNIDADES = List.of(
        new UsuarioComunidad(1, 1, 1, ROLES.get(0)), // María es Admin en EcoValencia
        new UsuarioComunidad(2, 1, 2, ROLES.get(1)), // María es Moderador en Madrid Sostenible
        new UsuarioComunidad(3, 2, 1, ROLES.get(2)), // Carlos es Miembro en EcoValencia
        new UsuarioComunidad(4, 3, 1, ROLES.get(3)), // Ana es Colaborador en EcoValencia
        new UsuarioComunidad(5, 3, 3, ROLES.get(2)), // Ana es Miembro en Barcelona Verde
        new UsuarioComunidad(6, 4, 2, ROLES.get(2)), // Pedro es Miembro en Madrid Sostenible
        new UsuarioComunidad(7, 5, 4, ROLES.get(2)), // Laura es Miembro en Andalucía Natural
        new UsuarioComunidad(8, 5, 5, ROLES.get(3))  // Laura es Colaborador en Canarias Eco
    );
    
    // Mock de UsuarioEvento
    public static final List<UsuarioEvento> USUARIO_EVENTOS = List.of(
        new UsuarioEvento(1, 1, 1, LocalDateTime.of(2024, 6, 10, 14, 30, 0), true),
        new UsuarioEvento(2, 1, 2, LocalDateTime.of(2024, 6, 18, 16, 45, 0), false),
        new UsuarioEvento(3, 2, 1, LocalDateTime.of(2024, 6, 11, 10, 15, 0), true),
        new UsuarioEvento(4, 3, 2, LocalDateTime.of(2024, 6, 20, 12, 0, 0), true),
        new UsuarioEvento(5, 3, 3, LocalDateTime.of(2024, 7, 1, 11, 20, 0), true),
        new UsuarioEvento(6, 4, 4, LocalDateTime.of(2024, 6, 25, 15, 30, 0), true),
        new UsuarioEvento(7, 5, 5, LocalDateTime.of(2024, 7, 5, 18, 0, 0), false)
    );
    
    // Mock de UsuarioReto
    public static final List<UsuarioReto> USUARIO_RETOS = List.of(
        new UsuarioReto(1, 1, 1, true, LocalDateTime.of(2024, 5, 15, 10, 0, 0)),
        new UsuarioReto(2, 1, 2, true, LocalDateTime.of(2024, 4, 20, 14, 30, 0)),
        new UsuarioReto(3, 2, 1, true, LocalDateTime.of(2024, 5, 10, 9, 15, 0)),
        new UsuarioReto(4, 2, 3, false, LocalDateTime.MIN),
        new UsuarioReto(5, 3, 2, true, LocalDateTime.of(2024, 6, 1, 16, 45, 0)),
        new UsuarioReto(6, 4, 4, true, LocalDateTime.of(2024, 5, 25, 11, 20, 0)),
        new UsuarioReto(7, 5, 5, false, LocalDateTime.MIN)
    );
    
    // Bloque estático para inicializar relaciones
    static {
        initializeRelationships();
    }
    
    // Método para inicializar relaciones
    private static void initializeRelationships() {
        // Asignar productos a usuarios
        USUARIOS.get(0).getProductos().addAll(List.of(PRODUCTOS.get(0), PRODUCTOS.get(2), PRODUCTOS.get(6)));
        USUARIOS.get(1).getProductos().addAll(List.of(PRODUCTOS.get(1), PRODUCTOS.get(4)));
        USUARIOS.get(2).getProductos().add(PRODUCTOS.get(3));
        USUARIOS.get(3).getProductos().add(PRODUCTOS.get(5));
        
        // Asignar eventos a comunidades
        COMUNIDADES.get(0).getEventos().addAll(List.of(EVENTOS.get(0), EVENTOS.get(1)));
        COMUNIDADES.get(1).getEventos().add(EVENTOS.get(2));
        COMUNIDADES.get(2).getEventos().add(EVENTOS.get(3));
        COMUNIDADES.get(3).getEventos().add(EVENTOS.get(4));
        
        // Asignar usuarios a comunidades (basado en UsuarioComunidad)
        for (UsuarioComunidad uc : USUARIO_COMUNIDADES) {
            Usuario usuario = findUsuarioById(uc.getUsuarioId());
            Comunidad comunidad = findComunidadById(uc.getComunidadId());
            
            if (usuario != null && comunidad != null) {
                // Añadir usuario a comunidad
                if (!comunidad.getUsuarios().contains(usuario)) {
                    comunidad.getUsuarios().add(usuario);
                }
                
                // Añadir comunidad a usuario
                if (!usuario.getComunidades().contains(comunidad)) {
                    usuario.getComunidades().add(comunidad);
                }
            }
        }
    }
    
    // Métodos de utilidad
    public static Usuario findUsuarioById(int id) {
        for (Usuario usuario : USUARIOS) {
            if (usuario.getUsuarioId() == id) {
                return usuario;
            }
        }
        return null;
    }
    
    public static Comunidad findComunidadById(int id) {
        for (Comunidad comunidad : COMUNIDADES) {
            if (comunidad.getComunidadId() == id) {
                return comunidad;
            }
        }
        return null;
    }
    
    public static List<Evento> getEventosProximos() {
        LocalDateTime ahora = LocalDateTime.now();
        List<Evento> eventosProximos = new ArrayList<>();
        
        for (Evento evento : EVENTOS) {
            if (evento.getFecha().isAfter(ahora)) {
                eventosProximos.add(evento);
            }
        }
        
        eventosProximos.sort((e1, e2) -> e1.getFecha().compareTo(e2.getFecha()));
        return eventosProximos;
    }
    
    public static List<Usuario> getUsuariosPorComunidad(int comunidadId) {
        List<Usuario> usuariosComunidad = new ArrayList<>();
        
        for (Usuario usuario : USUARIOS) {
            for (Comunidad comunidad : usuario.getComunidades()) {
                if (comunidad.getComunidadId() == comunidadId) {
                    usuariosComunidad.add(usuario);
                    break;
                }
            }
        }
        
        return usuariosComunidad;
    }
    
    public static List<Reto> getRetosCompletadosPorUsuario(int usuarioId) {
        List<Reto> retosCompletados = new ArrayList<>();
        
        for (UsuarioReto usuarioReto : USUARIO_RETOS) {
            if (usuarioReto.getUsuarioId() == usuarioId && usuarioReto.isCompletado()) {
                Reto reto = findRetoById(usuarioReto.getRetoId());
                if (reto != null) {
                    retosCompletados.add(reto);
                }
            }
        }
        
        return retosCompletados;
    }
    
    public static Reto findRetoById(int id) {
        for (Reto reto : RETOS) {
            if (reto.getRetoId() == id) {
                return reto;
            }
        }
        return null;
    }
    
    public static List<Evento> getEventosPorComunidad(int comunidadId) {
        List<Evento> eventosComunidad = new ArrayList<>();
        
        for (Comunidad comunidad : COMUNIDADES) {
            if (comunidad.getComunidadId() == comunidadId) {
                eventosComunidad.addAll(comunidad.getEventos());
                break;
            }
        }
        
        return eventosComunidad;
    }
    
    public static List<Producto> getProductosDisponiblesParaUsuario(int usuarioId) {
        Usuario usuario = findUsuarioById(usuarioId);
        List<Producto> productosDisponibles = new ArrayList<>();
        
        if (usuario != null) {
            for (Producto producto : PRODUCTOS) {
                // Verificar si el usuario tiene suficientes puntos y no tiene ya el producto
                boolean yaTieneProducto = false;
                for (Producto productoUsuario : usuario.getProductos()) {
                    if (productoUsuario.getProductoId() == producto.getProductoId()) {
                        yaTieneProducto = true;
                        break;
                    }
                }
                
                if (producto.getPuntos() <= usuario.getPuntos() && !yaTieneProducto) {
                    productosDisponibles.add(producto);
                }
            }
        }
        
        return productosDisponibles;
    }
    
    public static Usuario getUsuarioConMasPuntos() {
        Usuario usuarioTop = null;
        int maxPuntos = -1;
        
        for (Usuario usuario : USUARIOS) {
            if (usuario.getPuntos() > maxPuntos) {
                maxPuntos = usuario.getPuntos();
                usuarioTop = usuario;
            }
        }
        
        return usuarioTop;
    }
    
    public static Comunidad getComunidadMasActiva() {
        Comunidad comunidadMasActiva = null;
        int maxUsuarios = -1;
        
        for (Comunidad comunidad : COMUNIDADES) {
            if (comunidad.getUsuarios().size() > maxUsuarios) {
                maxUsuarios = comunidad.getUsuarios().size();
                comunidadMasActiva = comunidad;
            }
        }
        
        return comunidadMasActiva;
    }
    
    public static List<Noticia> getNoticiasRecientes(int cantidad) {
        if (cantidad >= NOTICIAS.size()) {
            return new ArrayList<>(NOTICIAS);
        }
        
        return new ArrayList<>(NOTICIAS.subList(0, cantidad));
    }
    
    public static List<Evento> getEventosPorUsuario(int usuarioId) {
        List<Evento> eventosUsuario = new ArrayList<>();
        
        for (UsuarioEvento usuarioEvento : USUARIO_EVENTOS) {
            if (usuarioEvento.getUsuarioId() == usuarioId && usuarioEvento.isAsistencia()) {
                Evento evento = findEventoById(usuarioEvento.getEventoId());
                if (evento != null) {
                    eventosUsuario.add(evento);
                }
            }
        }
        
        return eventosUsuario;
    }
    
    public static Evento findEventoById(int id) {
        for (Evento evento : EVENTOS) {
            if (evento.getEventoId() == id) {
                return evento;
            }
        }
        return null;
    }
    
    // Método para imprimir resumen de datos (útil para debugging)
    public static void printResumen() {
        System.out.println("=== RESUMEN DE DATOS MOCK ===");
        System.out.println("Total Usuarios: " + USUARIOS.size());
        System.out.println("Total Comunidades: " + COMUNIDADES.size());
        System.out.println("Total Eventos: " + EVENTOS.size());
        System.out.println("Total Retos: " + RETOS.size());
        System.out.println("Total Productos: " + PRODUCTOS.size());
        System.out.println("Total Noticias: " + NOTICIAS.size());
        System.out.println("=============================");
        
        System.out.println("\nUsuario con más puntos:");
        Usuario topUsuario = getUsuarioConMasPuntos();
        if (topUsuario != null) {
            System.out.println("- " + topUsuario.getNombre() + " " + topUsuario.getApellidos() + 
                             " (" + topUsuario.getPuntos() + " puntos)");
        }
        
        System.out.println("\nComunidad más activa:");
        Comunidad topComunidad = getComunidadMasActiva();
        if (topComunidad != null) {
            System.out.println("- " + topComunidad.getNombre() + 
                             " (" + topComunidad.getUsuarios().size() + " usuarios)");
        }
        
        System.out.println("\nEventos próximos:");
        List<Evento> eventosProximos = getEventosProximos();
        for (Evento evento : eventosProximos) {
            System.out.println("- " + evento.getNombre() + " - " + evento.getFecha());
        }
    }
}