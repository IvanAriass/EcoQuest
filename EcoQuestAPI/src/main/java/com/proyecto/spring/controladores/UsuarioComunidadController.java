package com.proyecto.spring.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import com.proyecto.spring.modelos.UsuarioComunidad;
import com.proyecto.spring.modelos.UsuarioComunidadDTO;
import com.proyecto.spring.servicios.PermisosService;
import com.proyecto.spring.servicios.UsuarioComunidadService;

@RestController
@RequestMapping("/api/usuario-comunidad")
public class UsuarioComunidadController {

    @Autowired
    private UsuarioComunidadService usuarioComunidadService;

    @Autowired
    private PermisosService permisosService;

    @PostMapping("/unirse")
    public ResponseEntity<UsuarioComunidad> unirse(@RequestParam Long usuarioId,
                                                    @RequestParam Long comunidadId,
                                                    @RequestParam String rol) {
        return usuarioComunidadService.unirse(usuarioId, comunidadId, rol)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<UsuarioComunidad> comunidadesDeUsuario(@PathVariable Long usuarioId) {
        return usuarioComunidadService.comunidadesDeUsuario(usuarioId);
    }

    @GetMapping("/comunidad/{comunidadId}")
    public List<UsuarioComunidadDTO> usuariosDeComunidad(@PathVariable Long comunidadId) {
        return usuarioComunidadService.usuariosDeComunidad(comunidadId).stream()
                .map(UsuarioComunidadDTO::new)
                .toList();
    }

    @DeleteMapping("/abandonar")
    public ResponseEntity<Void> abandonar(@RequestParam Long usuarioId, @RequestParam Long comunidadId) {
        return usuarioComunidadService.abandonar(usuarioId, comunidadId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("/rol")
    public ResponseEntity<UsuarioComunidad> cambiarRol(@RequestParam Long usuarioId,
                                                         @RequestParam Long comunidadId,
                                                         @RequestParam String nuevoRol) {
        return usuarioComunidadService.cambiarRol(usuarioId, comunidadId, nuevoRol)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/expulsar")
    public ResponseEntity<?> expulsar(@RequestParam Long usuarioId,
                                       @RequestParam Long comunidadId,
                                       @RequestParam Long solicitanteId) {
        if (!permisosService.tienePermiso(solicitanteId, comunidadId, "GESTIONAR_MIEMBROS")) {
            return ResponseEntity.status(403).body(Map.of("error", "No tienes permiso para expulsar miembros"));
        }
        if (usuarioId.equals(solicitanteId)) {
            return ResponseEntity.badRequest().body(Map.of("error", "No puedes expulsarte a ti mismo, usa abandonar"));
        }
        return usuarioComunidadService.abandonar(usuarioId, comunidadId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}