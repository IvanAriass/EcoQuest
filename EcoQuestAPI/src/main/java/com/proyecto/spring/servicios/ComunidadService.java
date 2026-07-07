package com.proyecto.spring.servicios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.proyecto.spring.modelos.Comunidad;
import com.proyecto.spring.modelos.Usuario;
import com.proyecto.spring.modelos.UsuarioComunidad;
import com.proyecto.spring.repository.ComunidadRepository;
import com.proyecto.spring.repository.UsuarioComunidadRepository;
import com.proyecto.spring.repository.UsuarioRepository;

@Service
public class ComunidadService {

    @Autowired
    private ComunidadRepository comunidadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioComunidadRepository usuarioComunidadRepository;

    @Value("${app.base.url}")
    private String baseUrl;

    public List<Comunidad> obtenerTodos() {
        return comunidadRepository.findAll().stream()
                .map(c -> {
                    if (c.getImagen() != null && !c.getImagen().isBlank() && !c.getImagen().startsWith("http")) {
                        c.setImagen(baseUrl + "/api/comunidades/imagen/" + c.getImagen());
                    }
                    return c;
                })
                .toList();
    }

    public Optional<Comunidad> obtenerPorId(Long id) {
        return comunidadRepository.findById(id).map(c -> {
            if (c.getImagen() != null && !c.getImagen().isBlank() && !c.getImagen().startsWith("http")) {
                c.setImagen(baseUrl + "/api/comunidades/imagen/" + c.getImagen());
            }
            return c;
        });
    }

    public Comunidad crear(Comunidad comunidad) {
        return comunidadRepository.save(comunidad);
    }

    public Comunidad crearConCreador(Comunidad comunidad, Long creadorId) {
        Comunidad guardada = comunidadRepository.save(comunidad);
        if (creadorId != null && creadorId > 0) {
            usuarioRepository.findById(creadorId).ifPresent(usuario -> {
                UsuarioComunidad relacion = new UsuarioComunidad(usuario, guardada, "FUNDADOR");
                usuarioComunidadRepository.save(relacion);
            });
        }
        return guardada;
    }

    public Optional<Comunidad> actualizar(Long id, Comunidad comunidadActualizada) {
        return comunidadRepository.findById(id).map(comunidad -> {
            comunidad.setNombre(comunidadActualizada.getNombre());
            comunidad.setDescripcion(comunidadActualizada.getDescripcion());
            comunidad.setImagen(comunidadActualizada.getImagen());
            comunidad.setEstado(comunidadActualizada.getEstado());
            return comunidadRepository.save(comunidad);
        });
    }

    public Optional<Comunidad> actualizar(Long id, String nombre, String descripcion) {
        return comunidadRepository.findById(id).map(comunidad -> {
            if (nombre != null && !nombre.isBlank()) comunidad.setNombre(nombre);
            if (descripcion != null) comunidad.setDescripcion(descripcion);
            return comunidadRepository.save(comunidad);
        });
    }

    public boolean eliminar(Long id) {
        Optional<Comunidad> comunidadOpt = comunidadRepository.findById(id);
        if (comunidadOpt.isEmpty()) return false;

        String nombreImagen = comunidadOpt.get().getImagen();
        if (nombreImagen != null && !nombreImagen.isBlank()) {
            Path rutaImagen = Paths.get("src/main/resources/static/imagenes/comunidades", nombreImagen);
            try {
                Files.deleteIfExists(rutaImagen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        comunidadRepository.deleteById(id);
        return true;
    }

    public Optional<Comunidad> cambiarEstado(Long id, String nuevoEstado) {
        return comunidadRepository.findById(id).map(comunidad -> {
            comunidad.setEstado(nuevoEstado);
            return comunidadRepository.save(comunidad);
        });
    }

    public Optional<Comunidad> enviarARevision(Long id, String motivo) {
        return comunidadRepository.findById(id).map(comunidad -> {
            comunidad.setEstado("PENDIENTE");
            comunidad.setMotivoCancelacion(motivo);
            return comunidadRepository.save(comunidad);
        });
    }

    public Optional<Comunidad> revisar(Long id, String motivo) {
        return comunidadRepository.findById(id).map(comunidad -> {
            comunidad.setEstado("REVISION");
            comunidad.setMotivoCancelacion(motivo);
            return comunidadRepository.save(comunidad);
        });
    }

    public Optional<Comunidad> cancelarConMotivo(Long id, String motivo) {
        return comunidadRepository.findById(id).map(comunidad -> {
            comunidad.setEstado("CANCELADO");
            comunidad.setMotivoCancelacion(motivo);
            return comunidadRepository.save(comunidad);
        });
    }

    public List<Comunidad> buscarPorEstado(String estado) {
        return comunidadRepository.findByEstado(estado).stream()
                .map(c -> {
                    if (c.getImagen() != null && !c.getImagen().isBlank() && !c.getImagen().startsWith("http")) {
                        c.setImagen(baseUrl + "/api/comunidades/imagen/" + c.getImagen());
                    }
                    return c;
                })
                .toList();
    }
}
