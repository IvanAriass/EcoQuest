package com.proyecto.apirest.demo;

import com.proyecto.apirest.entity.Comunidad;
import com.proyecto.apirest.entity.Evento;
import com.proyecto.apirest.repositories.ComunidadRepository;
import com.proyecto.apirest.repositories.EventoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication(scanBasePackages = "com.proyecto.apirest")
@EnableJpaRepositories("com.proyecto.apirest.repositories")
@EntityScan(basePackages = "com.proyecto.apirest.entity")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner initEventos(ComunidadRepository comunidadRepository,
	                              EventoRepository eventoRepository) {
		return args -> {
			if (eventoRepository.count() > 0) {
				return;
			}

			Optional<Comunidad> backend = comunidadRepository.findByNombre("Comunidad Backend");
			Optional<Comunidad> frontend = comunidadRepository.findByNombre("Comunidad Frontend");

			backend.ifPresent(comunidad -> {
				Evento e1 = new Evento(null,
						"Lanzamiento Plataforma (CLR)",
						"Online",
						"Evento de presentación de la plataforma (desde código)",
						null,
						LocalDateTime.now().plusDays(7));
				e1.setComunidad(comunidad);
				eventoRepository.save(e1);
			});

			frontend.ifPresent(comunidad -> {
				Evento e2 = new Evento(null,
						"Meetup Frontend (CLR)",
						"Madrid",
						"Encuentro para desarrolladores frontend (desde código)",
						null,
						LocalDateTime.now().plusDays(14));
				e2.setComunidad(comunidad);
				eventoRepository.save(e2);
			});
		};
	}
}
