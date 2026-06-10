package com.proyecto.spring.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.spring.modelos.Evento;
import com.proyecto.spring.modelos.Usuario;
import com.proyecto.spring.repository.ComunidadRepository;
import com.proyecto.spring.repository.EventoRepository;
import com.proyecto.spring.repository.UsuarioRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ComunidadRepository comunidadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Evento> obtenerTodos() {
        return eventoRepository.findAll();
    }

    public Optional<Evento> obtenerPorId(Long id) {
        return eventoRepository.findById(id);
    }

    public Optional<Evento> crearEnComunidad(Long comunidadId, Evento evento) {
        return comunidadRepository.findById(comunidadId).map(comunidad -> {
            evento.setComunidad(comunidad);
            return eventoRepository.save(evento);
        });
    }

    public boolean apuntarUsuario(Long eventoId, Long usuarioId) {
        Optional<Evento> eventoOpt = eventoRepository.findById(eventoId);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (eventoOpt.isEmpty() || usuarioOpt.isEmpty())
            return false;

        Usuario usuario = usuarioOpt.get();
        Evento evento = eventoOpt.get();

        // Evitar duplicados
        if (!usuario.getEventos().contains(evento)) {
            usuario.getEventos().add(evento);
            usuarioRepository.save(usuario);
        }

        return true;
    }

    public boolean desapuntarUsuario(Long eventoId, Long usuarioId) {
        Optional<Evento> eventoOpt = eventoRepository.findById(eventoId);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (eventoOpt.isEmpty() || usuarioOpt.isEmpty())
            return false;

        Usuario usuario = usuarioOpt.get();
        usuario.getEventos().remove(eventoOpt.get());
        usuarioRepository.save(usuario);

        return true;
    }

    public Optional<Evento> actualizar(Long id, Evento eventoActualizado) {
        return eventoRepository.findById(id).map(evento -> {
            evento.setNombre(eventoActualizado.getNombre());
            evento.setDescripcion(eventoActualizado.getDescripcion());
            evento.setFechaHora(eventoActualizado.getFechaHora());
            evento.setUbicacion(eventoActualizado.getUbicacion());
            evento.setImagen(eventoActualizado.getImagen());
            return eventoRepository.save(evento);
        });
    }

    public Optional<Evento> cambiarEstado(Long id, String nuevoEstado) {
        return eventoRepository.findById(id).map(evento -> {
            evento.setEstado(nuevoEstado);
            return eventoRepository.save(evento);
        });
    }

    public boolean eliminar(Long id) {
        if (!eventoRepository.existsById(id))
            return false;
        eventoRepository.deleteById(id);
        return true;
    }
}