package com.proyecto.spring.servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.spring.dto.MensajeDTO;
import com.proyecto.spring.modelos.Comunidad;
import com.proyecto.spring.modelos.Mensaje;
import com.proyecto.spring.modelos.Usuario;
import com.proyecto.spring.repository.ComunidadRepository;
import com.proyecto.spring.repository.MensajeRepository;
import com.proyecto.spring.repository.UsuarioRepository;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComunidadRepository comunidadRepository;

    public List<MensajeDTO> obtenerPorComunidad(Long comunidadId) {
        return mensajeRepository.findByComunidadIdOrderByFechaHoraAsc(comunidadId)
                .stream()
                .map(MensajeDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<MensajeDTO> crear(Long comunidadId, Long usuarioId, String texto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<Comunidad> comunidadOpt = comunidadRepository.findById(comunidadId);

        if (usuarioOpt.isEmpty() || comunidadOpt.isEmpty())
            return Optional.empty();

        Mensaje mensaje = new Mensaje(texto, LocalDateTime.now(), usuarioOpt.get(), comunidadOpt.get());
        Mensaje guardado = mensajeRepository.save(mensaje);
        return Optional.of(new MensajeDTO(guardado));
    }

    public boolean eliminar(Long mensajeId) {
        if (mensajeRepository.existsById(mensajeId)) {
            mensajeRepository.deleteById(mensajeId);
            return true;
        }
        return false;
    }
}
