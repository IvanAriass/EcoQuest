package com.proyecto.spring.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.spring.modelos.Rol;
import com.proyecto.spring.servicios.PermisosService;

@RestController
@RequestMapping("/api/comunidades/{comunidadId}/roles")
public class RolesGestionController {

    private final PermisosService permisosService;

    public RolesGestionController(PermisosService permisosService) {
        this.permisosService = permisosService;
    }

    @GetMapping
    public List<Map<String, Object>> listarRoles() {
        return permisosService.obtenerDefinicionesRoles();
    }

    @PostMapping
    public ResponseEntity<?> crearRol(@PathVariable Long comunidadId,
                                       @RequestParam Long solicitanteId,
                                       @RequestBody Map<String, Object> body) {
        if (!permisosService.tienePermiso(solicitanteId, comunidadId, "ADMINISTRAR")) {
            return ResponseEntity.status(403).body(Map.of("error", "No tienes permiso para administrar roles"));
        }
        String nombre = (String) body.get("nombre");
        int nivel = (int) body.getOrDefault("nivel", 0);
        String emoji = (String) body.getOrDefault("emoji", "");
        String descripcion = (String) body.getOrDefault("descripcion", "");
        @SuppressWarnings("unchecked")
        List<String> permisos = (List<String>) body.getOrDefault("permisos", List.of());

        Rol rol = permisosService.crearRol(nombre, nivel, emoji, descripcion, permisos);
        return ResponseEntity.ok(rolAMapa(rol));
    }

    @PutMapping("/{rolId}")
    public ResponseEntity<?> actualizarRol(@PathVariable Long comunidadId,
                                            @PathVariable Long rolId,
                                            @RequestParam Long solicitanteId,
                                            @RequestBody Map<String, Object> body) {
        if (!permisosService.tienePermiso(solicitanteId, comunidadId, "ADMINISTRAR")) {
            return ResponseEntity.status(403).body(Map.of("error", "No tienes permiso para administrar roles"));
        }
        String nombre = (String) body.get("nombre");
        Integer nivel = body.containsKey("nivel") ? (Integer) body.get("nivel") : null;
        String emoji = (String) body.get("emoji");
        String descripcion = (String) body.get("descripcion");

        return permisosService.actualizarRol(rolId, nombre, nivel, emoji, descripcion)
                .map(rol -> ResponseEntity.ok((Object) rolAMapa(rol)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{rolId}/permisos")
    public ResponseEntity<?> actualizarPermisos(@PathVariable Long comunidadId,
                                                 @PathVariable Long rolId,
                                                 @RequestParam Long solicitanteId,
                                                 @RequestBody Map<String, Object> body) {
        if (!permisosService.tienePermiso(solicitanteId, comunidadId, "ADMINISTRAR")) {
            return ResponseEntity.status(403).body(Map.of("error", "No tienes permiso para administrar roles"));
        }
        @SuppressWarnings("unchecked")
        List<String> permisos = (List<String>) body.get("permisos");
        return permisosService.reemplazarPermisos(rolId, permisos)
                .map(rol -> ResponseEntity.ok((Object) rolAMapa(rol)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{rolId}")
    public ResponseEntity<?> eliminarRol(@PathVariable Long comunidadId,
                                          @PathVariable Long rolId,
                                          @RequestParam Long solicitanteId) {
        if (!permisosService.tienePermiso(solicitanteId, comunidadId, "ADMINISTRAR")) {
            return ResponseEntity.status(403).body(Map.of("error", "No tienes permiso para administrar roles"));
        }
        if (permisosService.eliminarRol(rolId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private Map<String, Object> rolAMapa(Rol rol) {
        Map<String, Object> mapa = new HashMap<>();
        mapa.put("id", rol.getNombre());
        mapa.put("nombre", rol.getNombre());
        mapa.put("emoji", rol.getEmoji());
        mapa.put("nivel", rol.getNivel());
        mapa.put("descripcion", rol.getDescripcion());
        mapa.put("permisos", rol.getPermisos().stream()
                .map(rp -> rp.getPermiso())
                .collect(Collectors.toList()));
        return mapa;
    }
}
