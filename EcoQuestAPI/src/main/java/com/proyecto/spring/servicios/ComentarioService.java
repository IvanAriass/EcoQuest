package com.proyecto.spring.servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.spring.dto.ComentarioDTO;
import com.proyecto.spring.modelos.Comentario;
import com.proyecto.spring.modelos.Evento;
import com.proyecto.spring.modelos.Usuario;
import com.proyecto.spring.repository.ComentarioRepository;
import com.proyecto.spring.repository.EventoRepository;
import com.proyecto.spring.repository.UsuarioRepository;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PuntosService puntosService;

    public List<ComentarioDTO> obtenerPorEventoId(Long eventoId) {
        return comentarioRepository.findByEventoIdAndComentarioPadreIsNullOrderByFechaHoraAsc(eventoId).stream()
                .map(c -> {
                    ComentarioDTO dto = new ComentarioDTO(c);
                    dto.cantidadRespuestas = (int) comentarioRepository.countByComentarioPadreId(c.getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<ComentarioDTO> obtenerRespuestas(Long comentarioId) {
        return comentarioRepository.findByComentarioPadreId(comentarioId).stream()
                .map(c -> {
                    ComentarioDTO dto = new ComentarioDTO(c);
                    dto.cantidadRespuestas = (int) comentarioRepository.countByComentarioPadreId(c.getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<ComentarioDTO> crear(Long eventoId, Long usuarioId, String texto, Long comentarioPadreId) {
        Optional<Evento> eventoOpt = eventoRepository.findById(eventoId);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (eventoOpt.isEmpty() || usuarioOpt.isEmpty())
            return Optional.empty();

        Comentario comentario;
        if (comentarioPadreId != null) {
            Optional<Comentario> padreOpt = comentarioRepository.findById(comentarioPadreId);
            if (padreOpt.isEmpty())
                return Optional.empty();
            comentario = new Comentario(texto, LocalDateTime.now(), usuarioOpt.get(), eventoOpt.get(), padreOpt.get());
        } else {
            comentario = new Comentario(texto, LocalDateTime.now(), usuarioOpt.get(), eventoOpt.get());
        }

        Comentario guardado = comentarioRepository.save(comentario);

        puntosService.verificarYOtorgarReto(usuarioOpt.get(), "COMENTARIO", eventoId);

        return Optional.of(new ComentarioDTO(guardado));
    }

    public boolean eliminar(Long id) {
        if (comentarioRepository.existsById(id)) {
            comentarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void eliminarPorEventoId(Long eventoId) {
        comentarioRepository.deleteByEventoId(eventoId);
    }
}
