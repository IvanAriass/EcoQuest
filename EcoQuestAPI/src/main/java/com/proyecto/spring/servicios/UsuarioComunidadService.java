package com.proyecto.spring.servicios;

import com.proyecto.spring.modelos.*;
import com.proyecto.spring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioComunidadService {

    @Autowired
    private UsuarioComunidadRepository usuarioComunidadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComunidadRepository comunidadRepository;

    public Optional<UsuarioComunidad> unirse(Long usuarioId, Long comunidadId, String rol) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        Optional<Comunidad> comunidad = comunidadRepository.findById(comunidadId);

        if (usuario.isEmpty() || comunidad.isEmpty()) return Optional.empty();

        UsuarioComunidad relacion = new UsuarioComunidad(usuario.get(), comunidad.get(), rol);
        return Optional.of(usuarioComunidadRepository.save(relacion));
    }

    public List<UsuarioComunidad> comunidadesDeUsuario(Long usuarioId) {
        return usuarioComunidadRepository.findByUsuarioId(usuarioId);
    }

    public List<UsuarioComunidad> usuariosDeComunidad(Long comunidadId) {
        return usuarioComunidadRepository.findByComunidadId(comunidadId);
    }

    public boolean abandonar(Long usuarioId, Long comunidadId) {
        return usuarioComunidadRepository.findByUsuarioIdAndComunidadId(usuarioId, comunidadId)
                .map(relacion -> {
                    usuarioComunidadRepository.delete(relacion);
                    return true;
                }).orElse(false);
    }

    public Optional<UsuarioComunidad> cambiarRol(Long usuarioId, Long comunidadId, String nuevoRol) {
        return usuarioComunidadRepository.findByUsuarioIdAndComunidadId(usuarioId, comunidadId)
                .map(relacion -> {
                    relacion.setRol(nuevoRol);
                    return usuarioComunidadRepository.save(relacion);
                });
    }
}