package com.proyecto.spring.controladores;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.spring.modelos.Evento;
import com.proyecto.spring.servicios.EventoService;
import com.proyecto.spring.servicios.PermisosService;

@RestController
@RequestMapping("/api/eventos")
public class EventoPermisosController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private PermisosService permisosService;

    @PostMapping("/comunidad/{comunidadId}/con-permiso")
    public ResponseEntity<?> crearEventoConPermiso(
            @PathVariable Long comunidadId,
            @RequestBody Map<String, Object> body) {
        Long usuarioId = body.get("creadorId") != null
                ? ((Number) body.get("creadorId")).longValue()
                : null;

        if (usuarioId == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "creadorId es requerido"));
        }

        if (!permisosService.tienePermiso(usuarioId, comunidadId, "CREAR_EVENTOS")) {
            return ResponseEntity.status(403).body(Map.of("error",
                "No tienes permiso para crear eventos en esta comunidad. Necesitas al menos rol PLANTA."));
        }

        Evento evento = new Evento();
        evento.setNombre((String) body.get("nombre"));
        evento.setDescripcion((String) body.get("descripcion"));
        String fechaStr = (String) body.get("fechaHora");
        if (fechaStr != null) {
            evento.setFechaHora(LocalDateTime.parse(fechaStr));
        }
        evento.setUbicacion((String) body.get("ubicacion"));
        evento.setImagen((String) body.getOrDefault("imagen", ""));
        evento.setEstado("ACTIVO");

        return eventoService.crearEnComunidad(comunidadId, evento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
