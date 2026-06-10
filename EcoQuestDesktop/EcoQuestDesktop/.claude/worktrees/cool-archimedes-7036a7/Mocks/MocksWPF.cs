using System;
using System.Collections.Generic;

public static class MockData
{
    // Mock de Roles
    public static List<Rol> Roles = new List<Rol>
    {
        new Rol(1, "Administrador"),
        new Rol(2, "Moderador"),
        new Rol(3, "Miembro"),
        new Rol(4, "Colaborador"),
        new Rol(5, "Invitado")
    };

    // Mock de Niveles
    public static List<Nivel> Niveles = new List<Nivel>
    {
        new Nivel(1, "Principiante", "Nivel inicial para nuevos usuarios", 0, "beginner_icon.png"),
        new Nivel(2, "Intermedio", "Usuario con cierta experiencia", 100, "intermediate_icon.png"),
        new Nivel(3, "Avanzado", "Usuario con amplia experiencia", 500, "advanced_icon.png"),
        new Nivel(4, "Experto", "Usuario experto en la plataforma", 1000, "expert_icon.png"),
        new Nivel(5, "Maestro", "Nivel máximo de experiencia", 2000, "master_icon.png")
    };

    // Mock de Productos
    public static List<Producto> Productos = new List<Producto>
    {
        new Producto(1, "Camiseta Ecológica", "Camiseta 100% algodón orgánico", 250),
        new Producto(2, "Taza Reutilizable", "Taza de cerámica con diseño ecológico", 150),
        new Producto(3, "Kit de Jardinería", "Set completo para huerto urbano", 500),
        new Producto(4, "Bolsa de Tela", "Bolsa reutilizable para compras", 100),
        new Producto(5, "Libro Guía Verde", "Guía práctica de sostenibilidad", 300),
        new Producto(6, "Compostera Doméstica", "Compostera para hogar", 750),
        new Producto(7, "Panel Solar Portátil", "Panel solar pequeño para dispositivos", 1200)
    };

    // Mock de Retos
    public static List<Reto> Retos = new List<Reto>
    {
        new Reto(1, "7 Días Sin Plástico", "Evita el uso de plásticos de un solo uso durante una semana", 200),
        new Reto(2, "Huerto en Casa", "Cultiva al menos 3 vegetales en tu hogar", 350),
        new Reto(3, "Transporte Verde", "Usa transporte sostenible por 30 días consecutivos", 500),
        new Reto(4, "Zero Waste Semanal", "Reduce tu basura a lo mínimo indispensable", 400),
        new Reto(5, "Ahorro de Agua", "Reduce tu consumo de agua en un 25% por un mes", 300),
        new Reto(6, "Energía Renovable", "Compensa tu consumo eléctrico con energía verde", 600)
    };

    // Mock de Eventos
    public static List<Evento> Eventos = new List<Evento>
    {
        new Evento(1, "Limpieza de Playa", "Playa Malvarrosa, Valencia", 
                  "Jornada de limpieza comunitaria de playa", "beach_cleanup.jpg", 
                  new DateTime(2024, 6, 15, 10, 0, 0)),
        
        new Evento(2, "Taller de Compostaje", "Centro Comunitario Central", 
                  "Aprende técnicas de compostaje doméstico", "composting_workshop.jpg", 
                  new DateTime(2024, 6, 22, 17, 30, 0)),
        
        new Evento(3, "Plantación de Árboles", "Parque Nacional Sierra", 
                  "Reforestación participativa de área degradada", "tree_planting.jpg", 
                  new DateTime(2024, 7, 5, 9, 0, 0)),
        
        new Evento(4, "Mercado Ecológico", "Plaza Mayor", 
                  "Mercado de productos locales y ecológicos", "eco_market.jpg", 
                  new DateTime(2024, 6, 30, 11, 0, 0)),
        
        new Evento(5, "Conferencia Sostenibilidad", "Auditorio Municipal", 
                  "Conferencia sobre desarrollo sostenible", "sustainability_conference.jpg", 
                  new DateTime(2024, 7, 12, 18, 30, 0))
    };

    // Mock de Noticias
    public static List<Noticia> Noticias = new List<Noticia>
    {
        new Noticia(1, "María González", "La comunidad alcanzó un récord de reciclaje este mes, superando las 2 toneladas de materiales recuperados.", 
                   "recycling_record.jpg", "Récord de Reciclaje Alcanzado"),
        
        new Noticia(2, "Carlos Ruiz", "Se inauguró el nuevo parque urbano con más de 500 árboles nativos plantados por la comunidad.", 
                   "new_park.jpg", "Nuevo Parque Urbano Inaugurado"),
        
        new Noticia(3, "Ana López", "Guía completa con 10 consejos prácticos para reducir tu huella de carbono desde casa.", 
                   "carbon_footprint.jpg", "Cómo Reducir tu Huella de Carbono"),
        
        new Noticia(4, "Pedro Martínez", "La iniciativa de huertos urbanos ha beneficiado a más de 200 familias este año.", 
                   "urban_gardens.jpg", "Huertos Urbanos Transforman Comunidades"),
        
        new Noticia(5, "Laura Sánchez", "Nuevo sistema de compostaje comunitario implementado en 5 barrios de la ciudad.", 
                   "community_compost.jpg", "Compostaje Comunitario en Expansión")
    };

    // Mock de Comunidades (primero sin usuarios ni eventos)
    public static List<Comunidad> Comunidades = new List<Comunidad>
    {
        new Comunidad(1, "EcoValencia", "valencia_eco.jpg", 
                     "Comunidad dedicada a la sostenibilidad en Valencia y alrededores.", Roles[0]),
        
        new Comunidad(2, "Madrid Sostenible", "madrid_sustainable.jpg", 
                     "Iniciativas ecológicas y sostenibles en la capital.", Roles[1]),
        
        new Comunidad(3, "Barcelona Verde", "barcelona_green.jpg", 
                     "Promoviendo prácticas ecológicas en Barcelona.", Roles[2]),
        
        new Comunidad(4, "Andalucía Natural", "andalucia_natural.jpg", 
                     "Conservación del medio ambiente en Andalucía.", Roles[3]),
        
        new Comunidad(5, "Canarias Eco", "canarias_eco.jpg", 
                     "Protección del entorno natural canario.", Roles[4])
    };

    // Mock de Usuarios
    public static List<Usuario> Usuarios = new List<Usuario>
    {
        new Usuario(
            1, "admin123", true, "admin_maria", "María", "González Pérez",
            "maria.gonzalez@email.com", "maria_profile.jpg", 
            "Administradora de la plataforma y activista ambiental.",
            new DateTime(1990, 5, 15), 1250, 1800, Niveles[4]),
        
        new Usuario(
            2, "pass456", false, "carlos_eco", "Carlos", "Ruiz Martínez",
            "carlos.ruiz@email.com", "carlos_profile.jpg", 
            "Amante de la naturaleza y entusiasta del reciclaje.",
            new DateTime(1985, 8, 22), 850, 1100, Niveles[3]),
        
        new Usuario(
            3, "user789", false, "ana_verde", "Ana", "López Sánchez",
            "ana.lopez@email.com", "ana_profile.jpg", 
            "Especialista en huertos urbanos y agricultura sostenible.",
            new DateTime(1992, 3, 10), 650, 900, Niveles[2]),
        
        new Usuario(
            4, "green123", false, "pedro_nature", "Pedro", "Martín García",
            "pedro.martin@email.com", "pedro_profile.jpg", 
            "Promotor de energías renovables y movilidad sostenible.",
            new DateTime(1988, 11, 30), 450, 600, Niveles[2]),
        
        new Usuario(
            5, "nature456", false, "laura_planet", "Laura", "Sánchez Ruiz",
            "laura.sanchez@email.com", "laura_profile.jpg", 
            "Educadora ambiental con enfoque en comunidades.",
            new DateTime(1995, 7, 18), 300, 350, Niveles[1])
    };

    // Mock de UsuarioComunidad
    public static List<UsuarioComunidad> UsuarioComunidades = new List<UsuarioComunidad>
    {
        new UsuarioComunidad(1, 1, 1, Roles[0]), // María es Admin en EcoValencia
        new UsuarioComunidad(2, 1, 2, Roles[1]), // María es Moderador en Madrid Sostenible
        new UsuarioComunidad(3, 2, 1, Roles[2]), // Carlos es Miembro en EcoValencia
        new UsuarioComunidad(4, 3, 1, Roles[3]), // Ana es Colaborador en EcoValencia
        new UsuarioComunidad(5, 3, 3, Roles[2]), // Ana es Miembro en Barcelona Verde
        new UsuarioComunidad(6, 4, 2, Roles[2]), // Pedro es Miembro en Madrid Sostenible
        new UsuarioComunidad(7, 5, 4, Roles[2]), // Laura es Miembro en Andalucía Natural
        new UsuarioComunidad(8, 5, 5, Roles[3])  // Laura es Colaborador en Canarias Eco
    };

    // Mock de UsuarioEvento
    public static List<UsuarioEvento> UsuarioEventos = new List<UsuarioEvento>
    {
        new UsuarioEvento(1, 1, 1, new DateTime(2024, 6, 10, 14, 30, 0), true),
        new UsuarioEvento(2, 1, 2, new DateTime(2024, 6, 18, 16, 45, 0), false),
        new UsuarioEvento(3, 2, 1, new DateTime(2024, 6, 11, 10, 15, 0), true),
        new UsuarioEvento(4, 3, 2, new DateTime(2024, 6, 20, 12, 0, 0), true),
        new UsuarioEvento(5, 3, 3, new DateTime(2024, 7, 1, 11, 20, 0), true),
        new UsuarioEvento(6, 4, 4, new DateTime(2024, 6, 25, 15, 30, 0), true),
        new UsuarioEvento(7, 5, 5, new DateTime(2024, 7, 5, 18, 0, 0), false)
    };

    // Mock de UsuarioReto
    public static List<UsuarioReto> UsuarioRetos = new List<UsuarioReto>
    {
        new UsuarioReto(1, 1, 1, true, new DateTime(2024, 5, 15, 10, 0, 0)),
        new UsuarioReto(2, 1, 2, true, new DateTime(2024, 4, 20, 14, 30, 0)),
        new UsuarioReto(3, 2, 1, true, new DateTime(2024, 5, 10, 9, 15, 0)),
        new UsuarioReto(4, 2, 3, false, DateTime.MinValue),
        new UsuarioReto(5, 3, 2, true, new DateTime(2024, 6, 1, 16, 45, 0)),
        new UsuarioReto(6, 4, 4, true, new DateTime(2024, 5, 25, 11, 20, 0)),
        new UsuarioReto(7, 5, 5, false, DateTime.MinValue)
    };

    // Métodos de inicialización para establecer relaciones
    public static void InitializeRelationships()
    {
        // Asignar productos a usuarios
        Usuarios[0].Productos.AddRange(new List<Producto> { Productos[0], Productos[2], Productos[6] });
        Usuarios[1].Productos.AddRange(new List<Producto> { Productos[1], Productos[4] });
        Usuarios[2].Productos.AddRange(new List<Producto> { Productos[3] });
        Usuarios[3].Productos.AddRange(new List<Producto> { Productos[5] });
        
        // Asignar eventos a comunidades
        Comunidades[0].Eventos.AddRange(new List<Evento> { Eventos[0], Eventos[1] });
        Comunidades[1].Eventos.AddRange(new List<Evento> { Eventos[2] });
        Comunidades[2].Eventos.AddRange(new List<Evento> { Eventos[3] });
        Comunidades[3].Eventos.AddRange(new List<Evento> { Eventos[4] });
        
        // Asignar usuarios a comunidades (basado en UsuarioComunidad)
        foreach (var uc in UsuarioComunidades)
        {
            var usuario = Usuarios.Find(u => u.UsuarioId == uc.UsuarioId);
            var comunidad = Comunidades.Find(c => c.ComunidadId == uc.ComunidadId);
            
            if (usuario != null && comunidad != null)
            {
                // Añadir usuario a comunidad
                if (!comunidad.Usuarios.Contains(usuario))
                    comunidad.Usuarios.Add(usuario);
                
                // Añadir comunidad a usuario
                if (!usuario.Comunidades.Contains(comunidad))
                    usuario.Comunidades.Add(comunidad);
            }
        }
    }

    // Métodos de utilidad
    public static Usuario GetUsuarioById(int id)
    {
        return Usuarios.Find(u => u.UsuarioId == id);
    }

    public static List<Evento> GetEventosProximos()
    {
        DateTime ahora = DateTime.Now;
        return Eventos.FindAll(e => e.Fecha > ahora);
    }

    public static List<Usuario> GetUsuariosPorComunidad(int comunidadId)
    {
        return Usuarios.FindAll(u => u.Comunidades.Exists(c => c.ComunidadId == comunidadId));
    }

    public static List<Reto> GetRetosCompletadosPorUsuario(int usuarioId)
    {
        var retosCompletados = new List<Reto>();
        var usuarioRetos = UsuarioRetos.FindAll(ur => ur.UsuarioId == usuarioId && ur.Completado);
        
        foreach (var ur in usuarioRetos)
        {
            var reto = Retos.Find(r => r.RetoId == ur.RetoId);
            if (reto != null)
                retosCompletados.Add(reto);
        }
        
        return retosCompletados;
    }
}

// Corrección para la clase Rol (añadir propiedades públicas)
public class Rol
{
    public int Id { get; set; }
    public string NombreRol { get; set; }
    
    public Rol() { }
    
    public Rol(int id, string nombreRol)
    {
        this.Id = id;
        this.NombreRol = nombreRol;
    }
}