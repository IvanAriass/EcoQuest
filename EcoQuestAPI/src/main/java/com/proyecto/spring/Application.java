package com.proyecto.spring;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.proyecto.spring.modelos.Comunidad;
import com.proyecto.spring.modelos.Evento;
import com.proyecto.spring.modelos.Producto;
import com.proyecto.spring.modelos.Usuario;
import com.proyecto.spring.modelos.UsuarioComunidad;
import com.proyecto.spring.repository.ComunidadRepository;
import com.proyecto.spring.repository.EventoRepository;
import com.proyecto.spring.repository.ProductoRepository;
import com.proyecto.spring.repository.UsuarioComunidadRepository;
import com.proyecto.spring.servicios.UsuarioService;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Value("${COMMANDLINERUNNER_FLAG:false}")
	boolean runCommandLineRunner;

	@Bean
	@Transactional
	CommandLineRunner initData(
			UsuarioService usuarioRepository,
			ComunidadRepository comunidadRepository,
			EventoRepository eventoRepository,
			ProductoRepository productoRepository,
			UsuarioComunidadRepository usuarioComunidadRepository) {
		return (args) -> {
			if (runCommandLineRunner) {

				// Usuarios
				Usuario u1 = new Usuario("juanito99", "1234", "Juan", "García", "Amante del deporte", 25,
						"juan@email.com",
						"ic-hombre.png");
				Usuario u2 = new Usuario("maria_lp", "abcd", "María", "López", "Fotógrafa aficionada", 30,
						"maria@email.com", "ic-mujer.webp");
				Usuario u3 = new Usuario("admin_root", "admin123", "Carlos", "Martínez", "Administrador", 35,
						"carlos@email.com", "ic-hombre.png");
				Usuario u4 = new Usuario("laura_senderos", "pass123", "Laura", "Fernández", "Guía de montaña titulada", 28,
						"laura@email.com", "ic-mujer.webp");
				Usuario u5 = new Usuario("eco_guerrero", "verde123", "Miguel", "Rodríguez",
						"Activista medioambiental y reciclador", 42,
						"miguel@email.com", "ic-hombre.png");
				Usuario u6 = new Usuario("pixel_art", "foto2026", "Ana", "Ruiz", "Fotógrafa profesional de naturaleza", 33,
						"ana@email.com", "ic-mujer.webp");
				usuarioRepository.crear(u1);
				usuarioRepository.crear(u2);
				usuarioRepository.crear(u3);
				usuarioRepository.crear(u4);
				usuarioRepository.crear(u5);
				usuarioRepository.crear(u6);

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
				usuarioRepository.crear(u1);
				usuarioRepository.crear(u2);
				usuarioRepository.crear(u3);
				usuarioRepository.crear(u4);
				usuarioRepository.crear(u5);
				usuarioRepository.crear(u6);

				// Productos
				productoRepository.save(new Producto("Cuerda de Escalada", "cuerda.jpg", 89));
				productoRepository.save(new Producto("Cámara Réflex", "camara.jpg", 450));
				productoRepository.save(new Producto("Mochila Trail", "mochila.jpg", 65));
				productoRepository.save(new Producto("Botella Reutilizable", "botella.jpg", 15));
				productoRepository.save(new Producto("Prismáticos", "prismaticos.jpg", 120));
				productoRepository.save(new Producto("Linterna Frontal LED", "linterna.jpg", 35));

				System.out.println("********************************************************************************");
				System.out.println("Datos de ejemplo cargados correctamente");
				System.out.println("********************************************************************************");

			} else {
				System.out.println("********************************************************************************");
				System.out.println("El CommandLineRunner está deshabilitado.");
				System.out.println("********************************************************************************");

			}
		};
	}
}