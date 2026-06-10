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
						"juan.jpg");
				Usuario u2 = new Usuario("maria_lp", "abcd", "María", "López", "Fotógrafa aficionada", 30,
						"maria@email.com", "maria.jpg");
				Usuario u3 = new Usuario("admin_root", "admin123", "Carlos", "Martínez", "Administrador", 35,
						"carlos@email.com", "carlos.jpg");
				usuarioRepository.crear(u1);
				usuarioRepository.crear(u2);
				usuarioRepository.crear(u3);

				// Comunidades
				Comunidad c1 = new Comunidad("Deportes Extremos", "Comunidad para amantes del deporte extremo",
						"deportes.jpg");
				Comunidad c2 = new Comunidad("Fotografía Urbana", "Comparte tus fotos de la ciudad", "fotografia.jpg");
				comunidadRepository.save(c1);
				comunidadRepository.save(c2);

				// Usuarios en comunidades con roles
				usuarioComunidadRepository.save(new UsuarioComunidad(u1, c1, "ADMIN"));
				usuarioComunidadRepository.save(new UsuarioComunidad(u2, c1, "MIEMBRO"));
				usuarioComunidadRepository.save(new UsuarioComunidad(u2, c2, "ADMIN"));
				usuarioComunidadRepository.save(new UsuarioComunidad(u3, c2, "MIEMBRO"));

				// Eventos
				Evento e1 = new Evento("Carrera de Montaña", "Ruta de 20km por la sierra",
						LocalDateTime.of(2026, 5, 10, 9, 0), "Sierra Norte", "carrera.jpg", "Activo");
				Evento e2 = new Evento("Taller de Fotografía", "Aprende técnicas de fotografía nocturna",
						LocalDateTime.of(2026, 6, 15, 20, 0), "Centro Cultural", "taller.jpg", "Activo");
				Evento e3 = new Evento("Escalada en Roca", "Sesión de escalada para todos los niveles",
						LocalDateTime.of(2026, 7, 20, 10, 0), "Pedriza", "escalada.jpg", "Activo");
				e1.setComunidad(c1);
				e2.setComunidad(c2);
				e3.setComunidad(c1);
				eventoRepository.save(e1);
				eventoRepository.save(e2);
				eventoRepository.save(e3);
				e1.setComunidad(c1);
				e2.setComunidad(c2);
				e3.setComunidad(c1);
				eventoRepository.save(e1);
				eventoRepository.save(e2);
				eventoRepository.save(e3);

				// Usuarios en eventos
				u1.setEventos(new ArrayList<>(List.of(e1, e3)));
				u2.setEventos(new ArrayList<>(List.of(e2)));
				u3.setEventos(new ArrayList<>(List.of(e1, e2)));
				usuarioRepository.crear(u1);
				usuarioRepository.crear(u2);
				usuarioRepository.crear(u3);

				// Productos
				productoRepository.save(new Producto("Cuerda de Escalada", "cuerda.jpg", 89));
				productoRepository
						.save(new Producto("Cámara Réflex", "camara.jpg", 450));
				productoRepository.save(new Producto("Mochila Trail", "mochila.jpg", 65));

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