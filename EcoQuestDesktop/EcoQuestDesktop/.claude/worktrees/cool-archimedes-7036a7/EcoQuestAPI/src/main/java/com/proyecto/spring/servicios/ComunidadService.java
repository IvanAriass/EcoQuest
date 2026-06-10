package com.proyecto.spring.servicios;

import com.proyecto.spring.modelos.Comunidad;
import com.proyecto.spring.repository.ComunidadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            return comunidadRepository.save(comunidad);
        });
    }

    public boolean eliminar(Long id) {
        if (!comunidadRepository.existsById(id)) return false;
        comunidadRepository.deleteById(id);
        return true;
    }
}