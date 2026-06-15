package com.proyecto.spring.controladores;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.spring.dto.ComentarioDTO;
import com.proyecto.spring.servicios.ComentarioService;

@RestController
@RequestMapping("/api/eventos/{eventoId}/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    public List<ComentarioDTO> obtenerComentarios(@PathVariable Long eventoId) {
        return comentarioService.obtenerPorEventoId(eventoId);
    }

    @PostMapping
    public ResponseEntity<ComentarioDTO> crearComentario(
            @PathVariable Long eventoId,
            @RequestBody Map<String, Object> body) {

        Long usuarioId = body.get("usuarioId") != null
                ? ((Number) body.get("usuarioId")).longValue()
                : null;
        String texto = body.get("texto") != null ? body.get("texto").toString() : null;
        Long comentarioPadreId = body.get("comentarioPadreId") != null
                ? ((Number) body.get("comentarioPadreId")).longValue()
                : null;

        if (usuarioId == null || texto == null || texto.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        return comentarioService.crear(eventoId, usuarioId, texto, comentarioPadreId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{comentarioId}/respuestas")
    public List<ComentarioDTO> obtenerRespuestas(@PathVariable Long comentarioId) {
        return comentarioService.obtenerRespuestas(comentarioId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Long eventoId, @PathVariable Long id) {
        return comentarioService.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
