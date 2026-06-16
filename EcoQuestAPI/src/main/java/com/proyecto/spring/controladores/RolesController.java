package com.proyecto.spring.controladores;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.spring.servicios.PermisosService;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    private final PermisosService permisosService;

    public RolesController(PermisosService permisosService) {
        this.permisosService = permisosService;
    }

    @GetMapping
    public List<Map<String, Object>> obtenerRoles() {
        return permisosService.obtenerDefinicionesRoles();
    }

    @GetMapping("/{usuarioId}/{comunidadId}")
    public ResponseEntity<Map<String, Object>> obtenerRolUsuario(
            @PathVariable Long usuarioId,
            @PathVariable Long comunidadId) {
        String rol = permisosService.obtenerRolUsuario(usuarioId, comunidadId);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("rol", rol));
    }

    @GetMapping("/{usuarioId}/{comunidadId}/permiso/{permiso}")
    public ResponseEntity<Map<String, Object>> verificarPermiso(
            @PathVariable Long usuarioId,
            @PathVariable Long comunidadId,
            @PathVariable String permiso) {
        boolean tiene = permisosService.tienePermiso(usuarioId, comunidadId, permiso);
        return ResponseEntity.ok(Map.of("tienePermiso", tiene));
    }
}
