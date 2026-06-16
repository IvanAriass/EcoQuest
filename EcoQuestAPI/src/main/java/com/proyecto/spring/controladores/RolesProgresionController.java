package com.proyecto.spring.controladores;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.spring.servicios.PermisosService;

@RestController
@RequestMapping("/api/usuario-comunidad")
public class RolesProgresionController {

    @Autowired
    private PermisosService permisosService;

    @PatchMapping("/rol/cambiar")
    public ResponseEntity<Map<String, Object>> cambiarRol(
            @RequestBody Map<String, Object> body) {
        Long usuarioId = body.get("usuarioId") != null
                ? ((Number) body.get("usuarioId")).longValue() : null;
        Long comunidadId = body.get("comunidadId") != null
                ? ((Number) body.get("comunidadId")).longValue() : null;
        Long solicitanteId = body.get("solicitanteId") != null
                ? ((Number) body.get("solicitanteId")).longValue() : null;
        String nuevoRol = (String) body.get("nuevoRol");

        if (usuarioId == null || comunidadId == null || solicitanteId == null || nuevoRol == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Faltan par\u00e1metros"));
        }

        boolean exito = permisosService.cambiarRol(usuarioId, comunidadId, nuevoRol, solicitanteId);
        if (exito) {
            return ResponseEntity.ok(Map.of("mensaje", "Rol actualizado a " + nuevoRol));
        }
        return ResponseEntity.status(403).body(Map.of("error", "No tienes permiso para cambiar este rol"));
    }
}
