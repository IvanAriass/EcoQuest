package com.proyecto.spring.servicios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.spring.modelos.Comunidad;
import com.proyecto.spring.repository.ComunidadRepository;

@Service
public class ComunidadService {

    @Autowired
    private ComunidadRepository comunidadRepository;

    public List<Comunidad> obtenerTodos() {
        return comunidadRepository.findAll();
    }

    public Optional<Comunidad> obtenerPorId(Long id) {
        return comunidadRepository.findById(id);
    }

    public Comunidad crear(Comunidad comunidad) {
        return comunidadRepository.save(comunidad);
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
        return comunidadRepository.findByEstado(estado);
    }
}
