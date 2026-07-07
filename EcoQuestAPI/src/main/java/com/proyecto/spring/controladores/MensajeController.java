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

import com.proyecto.spring.dto.MensajeDTO;
import com.proyecto.spring.servicios.MensajeService;
import com.proyecto.spring.servicios.PermisosService;

@RestController
@RequestMapping("/api/comunidades/{comunidadId}/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private PermisosService permisosService;

    @GetMapping
    public List<MensajeDTO> obtenerMensajes(@PathVariable Long comunidadId) {
        return mensajeService.obtenerPorComunidad(comunidadId);
    }

    @PostMapping
    public ResponseEntity<?> crearMensaje(
            @PathVariable Long comunidadId,
            @RequestBody Map<String, Object> body) {
        Long usuarioId = body.get("usuarioId") != null
                ? ((Number) body.get("usuarioId")).longValue()
                : null;
        String texto = (String) body.get("texto");

        if (usuarioId == null || texto == null || texto.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "usuarioId y texto son requeridos"));
        }

        if (!permisosService.tienePermiso(usuarioId, comunidadId, "ESCRIBIR_MENSAJES")) {
            return ResponseEntity.status(403).body(Map.of("error", "No tienes permiso para escribir mensajes"));
        }

        return mensajeService.crear(comunidadId, usuarioId, texto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{mensajeId}")
    public ResponseEntity<Void> eliminarMensaje(
            @PathVariable Long comunidadId,
            @PathVariable Long mensajeId,
            @RequestBody(required = false) Map<String, Object> body) {
        Long usuarioId = body != null && body.get("usuarioId") != null
                ? ((Number) body.get("usuarioId")).longValue()
                : null;

        if (usuarioId != null && !permisosService.tienePermiso(usuarioId, comunidadId, "MODERAR_CHAT")) {
            return ResponseEntity.status(403).build();
        }

        return mensajeService.eliminar(mensajeId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
