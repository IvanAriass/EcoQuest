package com.proyecto.spring.servicios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.proyecto.spring.dto.EventoDTO;
import com.proyecto.spring.modelos.Evento;
import com.proyecto.spring.modelos.Usuario;
import com.proyecto.spring.repository.ComunidadRepository;
import com.proyecto.spring.repository.EventoRepository;
import com.proyecto.spring.repository.UsuarioRepository;

import com.proyecto.spring.servicios.PuntosService;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ComunidadRepository comunidadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PuntosService puntosService;

    @Value("${app.base.url}")
    private String baseUrl;

    private void prefijarImagenes(EventoDTO dto) {
        if (dto.imagen != null && !dto.imagen.isBlank()) {
            dto.imagen = baseUrl + "/api/eventos/imagen/" + dto.imagen;
        }
        if (dto.imagenComunidad != null && !dto.imagenComunidad.isBlank()) {
            dto.imagenComunidad = baseUrl + "/api/comunidades/imagen/" + dto.imagenComunidad;
        }
    }

    public List<EventoDTO> obtenerTodosDTO() {
        return eventoRepository.findAll().stream()
                .map(EventoDTO::new)
                .peek(this::prefijarImagenes)
                .collect(Collectors.toList());
    }

    public Optional<EventoDTO> obtenerPorIdDTO(Long id) {
        return eventoRepository.findById(id).map(EventoDTO::new)
                .map(dto -> { prefijarImagenes(dto); return dto; });
    }

    public List<EventoDTO> buscarPorNombre(String nombre) {
        return eventoRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(EventoDTO::new)
                .peek(this::prefijarImagenes)
                .collect(Collectors.toList());
    }

    public List<EventoDTO> buscarPorEstado(String estado) {
        return eventoRepository.findByEstado(estado).stream()
                .map(EventoDTO::new)
                .peek(this::prefijarImagenes)
                .collect(Collectors.toList());
    }

    public Evento crear(Evento evento) {
        return eventoRepository.save(evento);
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

        if (!usuario.getEventos().contains(evento)) {
            usuario.getEventos().add(evento);
            usuarioRepository.save(usuario);
            puntosService.verificarYOtorgarReto(usuario, "EVENTO", eventoId);
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

    public Optional<Evento> cancelarConMotivo(Long id, String motivo) {
        return eventoRepository.findById(id).map(evento -> {
            evento.setEstado("CANCELADO");
            evento.setMotivoCancelacion(motivo);
            return eventoRepository.save(evento);
        });
    }

    public Optional<Evento> enviarARevision(Long id, String motivo) {
        return eventoRepository.findById(id).map(evento -> {
            evento.setEstado("REVISION");
            evento.setMotivoCancelacion(motivo);
            return eventoRepository.save(evento);
        });
    }

    public boolean eliminar(Long id) {
        Optional<Evento> eventoOpt = eventoRepository.findById(id);
        if (eventoOpt.isEmpty())
            return false;

        String nombreImagen = eventoOpt.get().getImagen();
        if (nombreImagen != null && !nombreImagen.isBlank()) {
            Path rutaImagen = Paths.get("src/main/resources/static/imagenes/eventos", nombreImagen);
            try {
                Files.deleteIfExists(rutaImagen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        eventoRepository.deleteById(id);
        return true;
    }
}