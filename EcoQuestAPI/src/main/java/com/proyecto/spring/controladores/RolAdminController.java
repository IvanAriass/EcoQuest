package com.proyecto.spring.controladores;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.spring.modelos.Rol;
import com.proyecto.spring.servicios.PermisosService;

@RestController
@RequestMapping("/api/admin/roles")
public class RolAdminController {

    private final PermisosService permisosService;

    public RolAdminController(PermisosService permisosService) {
        this.permisosService = permisosService;
    }

    @PostMapping
    public ResponseEntity<Rol> crearRol(@RequestBody Map<String, Object> body) {
        String nombre = (String) body.get("nombre");
        int nivel = (int) body.getOrDefault("nivel", 0);
        String emoji = (String) body.getOrDefault("emoji", "");
        String descripcion = (String) body.getOrDefault("descripcion", "");
        @SuppressWarnings("unchecked")
        List<String> permisos = (List<String>) body.getOrDefault("permisos", List.of());

        Rol rol = permisosService.crearRol(nombre, nivel, emoji, descripcion, permisos);
        return ResponseEntity.ok(rol);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizarRol(@PathVariable Long id,
                                              @RequestBody Map<String, Object> body) {
        String nombre = (String) body.get("nombre");
        Integer nivel = body.containsKey("nivel") ? (Integer) body.get("nivel") : null;
        String emoji = (String) body.get("emoji");
        String descripcion = (String) body.get("descripcion");

        return permisosService.actualizarRol(id, nombre, nivel, emoji, descripcion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/permisos")
    public ResponseEntity<Rol> actualizarPermisos(@PathVariable Long id,
                                                   @RequestBody Map<String, Object> body) {
        @SuppressWarnings("unchecked")
        List<String> permisos = (List<String>) body.get("permisos");
        return permisosService.reemplazarPermisos(id, permisos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        if (permisosService.eliminarRol(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
