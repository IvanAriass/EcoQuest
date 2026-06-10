package com.proyecto.spring.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.spring.modelos.Evento;
import com.proyecto.spring.servicios.EventoService;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public List<Evento> obtenerTodos() {
        return eventoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerPorId(@PathVariable Long id) {
        return eventoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/comunidad/{comunidadId}")
    public ResponseEntity<Evento> crearEnComunidad(@PathVariable Long comunidadId, @RequestBody Evento evento) {
        return eventoService.crearEnComunidad(comunidadId, evento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{eventoId}/usuarios/{usuarioId}")
    public ResponseEntity<Void> apuntarseAEvento(
            @PathVariable Long eventoId,
            @PathVariable Long usuarioId) {
        return eventoService.apuntarUsuario(eventoId, usuarioId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{eventoId}/usuarios/{usuarioId}")
    public ResponseEntity<Void> desapuntarseDeEvento(
            @PathVariable Long eventoId,
            @PathVariable Long usuarioId) {
        return eventoService.desapuntarUsuario(eventoId, usuarioId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> actualizar(@PathVariable Long id, @RequestBody Evento evento) {
        return eventoService.actualizar(id, evento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Evento> cancelarEvento(@PathVariable Long id) {
        return eventoService.cambiarEstado(id, "CANCELADO")
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<Evento> finalizarEvento(@PathVariable Long id) {
        return eventoService.cambiarEstado(id, "FINALIZADO")
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return eventoService.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}