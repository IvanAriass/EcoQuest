package com.proyecto.spring;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.proyecto.spring.modelos.Categoria;
import com.proyecto.spring.modelos.Comunidad;
import com.proyecto.spring.modelos.Evento;
import com.proyecto.spring.modelos.Producto;
import com.proyecto.spring.modelos.Reto;
import com.proyecto.spring.modelos.Usuario;
import com.proyecto.spring.modelos.UsuarioComunidad;
import com.proyecto.spring.repository.CategoriaRepository;
import com.proyecto.spring.repository.ComunidadRepository;
import com.proyecto.spring.repository.EventoRepository;
import com.proyecto.spring.repository.ProductoRepository;
import com.proyecto.spring.repository.RetoRepository;
import com.proyecto.spring.repository.UsuarioComunidadRepository;
import com.proyecto.spring.repository.UsuarioRepository;
import com.proyecto.spring.servicios.UsuarioService;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner initData(
			UsuarioService usuarioService,
			UsuarioRepository usuarioRepository,
			ComunidadRepository comunidadRepository,
			EventoRepository eventoRepository,
			ProductoRepository productoRepository,
			CategoriaRepository categoriaRepository,
			UsuarioComunidadRepository usuarioComunidadRepository,
			RetoRepository retoRepository) {
		return (args) -> {
			if (comunidadRepository.count() > 0) {
				System.out.println("********************************************************************************");
				System.out.println("La base de datos ya tiene datos. Se omite la carga inicial.");
				System.out.println("********************************************************************************");
				return;
			}

			// Usuarios
			Usuario u1 = new Usuario("juanito99", "1234", "Juan", "García", "Amante del deporte", LocalDate.of(2001, 1, 15),
					"juan@email.com",
					"ic-hombre.png");
			Usuario u2 = new Usuario("maria_lp", "abcd", "María", "López", "Fotógrafa aficionada", LocalDate.of(1996, 3, 20),
					"maria@email.com", "ic-mujer.webp");
			Usuario u3 = new Usuario("admin_root", "admin123", "Carlos", "Martínez", "Administrador", LocalDate.of(1991, 5, 10),
					"carlos@email.com", "ic-hombre.png");
			Usuario u4 = new Usuario("laura_senderos", "pass123", "Laura", "Fernández", "Guía de montaña titulada", LocalDate.of(1998, 7, 8),
					"laura@email.com", "ic-mujer.webp");
			Usuario u5 = new Usuario("eco_guerrero", "verde123", "Miguel", "Rodríguez",
					"Activista medioambiental y reciclador", LocalDate.of(1984, 2, 28),
					"miguel@email.com", "ic-hombre.png");
			Usuario u6 = new Usuario("pixel_art", "foto2026", "Ana", "Ruiz", "Fotógrafa profesional de naturaleza", LocalDate.of(1993, 9, 14),
					"ana@email.com", "ic-mujer.webp");
			usuarioService.crear(u1);
			usuarioService.crear(u2);
			usuarioService.crear(u3);
			usuarioService.crear(u4);
			usuarioService.crear(u5);
			usuarioService.crear(u6);

			// Comunidades
			Comunidad c1 = new Comunidad("Deportes Extremos", "Comunidad para amantes del deporte extremo",
					"deportes.jpg");
			Comunidad c2 = new Comunidad("Fotografía Urbana", "Comparte tus fotos de la ciudad", "fotografia.jpg");
			Comunidad c3 = new Comunidad("Eco Guerreros", "Luchamos por un planeta más limpio y sostenible",
					"eco-guerreros.jpeg");
			Comunidad c4 = new Comunidad("Senderistas del Norte", "Rutas de senderismo por el norte de España",
					"senderismo.jpg");
			c4.setEstado("PENDIENTE");
			Comunidad c5 = new Comunidad("Huertos Urbanos", "Cultiva tus propios alimentos en la ciudad",
					"huerto.jpg");
			c5.setEstado("CANCELADO");
			Comunidad c6 = new Comunidad("Buceo y Snorkel",
					"Exploramos los fondos marinos. Actividades de buceo para todos los niveles",
					"buceo.jpg");
			c6.setEstado("REVISION");
			c6.setMotivoCancelacion("Pendiente de verificar titulaciones del instructor");
			Comunidad c7 = new Comunidad("Club de Lectura Verde",
					"Leemos y debatimos libros sobre ecología y sostenibilidad", "lectura.png");
			c7.setEstado("PENDIENTE");
			Comunidad c8 = new Comunidad("Running Nocturno",
					"Salidas a correr por la ciudad al anochecer. Todos los ritmos son bienvenidos",
					"running.jpg");
			c8.setEstado("ACTIVO");
			comunidadRepository.save(c1);
			comunidadRepository.save(c2);
			comunidadRepository.save(c3);
			comunidadRepository.save(c4);
			comunidadRepository.save(c5);
			comunidadRepository.save(c6);
			comunidadRepository.save(c7);
			comunidadRepository.save(c8);

			// Usuarios en comunidades con roles
			usuarioComunidadRepository.save(new UsuarioComunidad(u1, c1, "ADMIN"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u2, c1, "MIEMBRO"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u2, c2, "ADMIN"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u3, c2, "MIEMBRO"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u1, c3, "ADMIN"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u3, c3, "MIEMBRO"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u2, c4, "ADMIN"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u1, c5, "ADMIN"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u2, c5, "MIEMBRO"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u4, c4, "ADMIN"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u4, c6, "ADMIN"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u1, c6, "MIEMBRO"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u5, c3, "ADMIN"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u5, c7, "ADMIN"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u6, c2, "ADMIN"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u6, c7, "MIEMBRO"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u3, c8, "ADMIN"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u2, c8, "MIEMBRO"));
			usuarioComunidadRepository.save(new UsuarioComunidad(u4, c8, "MIEMBRO"));

			// Eventos
			Evento e1 = new Evento("Carrera de Montaña", "Ruta de 20km por la sierra",
					LocalDateTime.of(2026, 5, 10, 9, 0), "Sierra Norte", "carrera.jpg", "ACTIVO");
			Evento e2 = new Evento("Taller de Fotografía", "Aprende técnicas de fotografía nocturna",
					LocalDateTime.of(2026, 6, 15, 20, 0), "Centro Cultural", "taller-foto.jpg", "ACTIVO");
			Evento e3 = new Evento("Escalada en Roca", "Sesión de escalada para todos los niveles",
					LocalDateTime.of(2026, 7, 20, 10, 0), "Pedriza", "escalada.jpg", "ACTIVO");
			Evento e4 = new Evento("Ruta en Kayak", "Excursión guiada en kayak por la costa con parada para snorkel",
					LocalDateTime.of(2026, 8, 5, 9, 30), "Cabo de Gata","kayak.jpg","PENDIENTE");
			Evento e5 = new Evento("Recogida de Plásticos en la Playa",
					"Jornada de limpieza costera. Bolsas y guantes incluidos",
					LocalDateTime.of(2026, 9, 12, 10, 0), "Playa de la Malvarrosa", "recogida.jpg", "ACTIVO");
			Evento e6 = new Evento("Taller de Reciclaje Creativo",
					"Aprende a transformar residuos en objetos útiles y decorativos",
					LocalDateTime.of(2026, 10, 5, 17, 30), "Centro Cívico", "taller-reciclaje.jpg", "PENDIENTE");
			Evento e7 = new Evento("Carrera Nocturna Benéfica",
					"5km nocturnos a beneficio de la reforestación urbana",
					LocalDateTime.of(2026, 11, 1, 21, 0), "Parque del Retiro", "carrera-nocturna.jpeg", "CANCELADO");
			e1.setComunidad(c1);
			e2.setComunidad(c2);
			e3.setComunidad(c1);
			e4.setComunidad(c2);
			e5.setComunidad(c3);
			e6.setComunidad(c7);
			e7.setComunidad(c8);
			eventoRepository.save(e1);
			eventoRepository.save(e2);
			eventoRepository.save(e3);
			eventoRepository.save(e4);
			eventoRepository.save(e5);
			eventoRepository.save(e6);
			eventoRepository.save(e7);
			// Usuarios en eventos
			u1.setEventos(new ArrayList<>(List.of(e1, e3, e4)));
			u2.setEventos(new ArrayList<>(List.of(e2, e5)));
			u3.setEventos(new ArrayList<>(List.of(e1, e2, e7)));
			u4.setEventos(new ArrayList<>(List.of(e1, e4, e5)));
			u5.setEventos(new ArrayList<>(List.of(e5, e6)));
			u6.setEventos(new ArrayList<>(List.of(e2, e6)));
			usuarioRepository.save(u1);
			usuarioRepository.save(u2);
			usuarioRepository.save(u3);
			usuarioRepository.save(u4);
			usuarioRepository.save(u5);
			usuarioRepository.save(u6);

			// Categorías
			Categoria catCosmeticos = categoriaRepository.save(new Categoria("Cosméticos"));
			Categoria catMarcos = categoriaRepository.save(new Categoria("Marcos"));
			Categoria catTemas = categoriaRepository.save(new Categoria("Temas"));
			Categoria catInsignias = categoriaRepository.save(new Categoria("Insignias"));
			Categoria catEstilos = categoriaRepository.save(new Categoria("Estilos de Nombre"));

			// Productos (cosméticos para personalización del perfil)
			productoRepository.save(new Producto(
				"Marco Dorado", "Un elegante marco dorado para tu foto de perfil",
				"", 80, "MARCO", catMarcos));
			productoRepository.save(new Producto(
				"Marco Premium", "Marco exclusivo con detalles brillantes para destacar",
				"", 200, "MARCO", catMarcos));
			productoRepository.save(new Producto(
				"Tema Bosque", "Tema de perfil con tonos verdes y naturales",
				"", 120, "TEMA", catTemas));
			productoRepository.save(new Producto(
				"Tema Atardecer", "Tema con degradado naranja y púrpura para tu perfil",
				"", 150, "TEMA", catTemas));
			productoRepository.save(new Producto(
				"Insignia EcoGuerrero", "Una insignia especial para los verdaderos guerreros ecológicos",
				"", 50, "INSIGNIA", catInsignias));
			productoRepository.save(new Producto(
				"Estilo Arcoíris", "Nombre de usuario con colores arcoíris",
				"", 100, "ESTILO_NOMBRE", catEstilos));

			// Retos (desafios de puntos)
			retoRepository.save(new Reto("Inicio de sesion diario", "Inicia sesion una vez al dia", 10, "LOGIN", 1, "DIARIA"));
			retoRepository.save(new Reto("Escribir un comentario", "Escribe un comentario en una publicacion", 5, "COMENTARIO", 1, "ILIMITADA"));
			retoRepository.save(new Reto("Unirse a un evento", "Apuntate a un evento de la comunidad", 50, "EVENTO", 1, "UNICA"));
			retoRepository.save(new Reto("Unirse a una comunidad", "Forma parte de una nueva comunidad", 30, "COMUNIDAD", 1, "UNICA"));
			retoRepository.save(new Reto("Crear un evento", "Crea y publica un nuevo evento", 100, "CREAR_EVENTO", 1, "UNICA"));
			retoRepository.save(new Reto("Crear una comunidad", "Crea y publica una nueva comunidad", 80, "CREAR_COMUNIDAD", 1, "UNICA"));
			retoRepository.save(new Reto("Participar en 3 eventos", "Completa la participacion en 3 eventos distintos", 200, "EVENTOS_3", 3, "UNICA"));

			System.out.println("********************************************************************************");
			System.out.println("Datos de ejemplo cargados correctamente");
			System.out.println("********************************************************************************");
		};
	}
}