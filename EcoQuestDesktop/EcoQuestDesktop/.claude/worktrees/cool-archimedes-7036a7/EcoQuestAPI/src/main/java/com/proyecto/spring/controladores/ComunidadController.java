package com.proyecto.spring.controladores;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.spring.dto.ComunidadDTO;
import com.proyecto.spring.modelos.Comunidad;
import com.proyecto.spring.servicios.ComunidadService;

@RestController
@RequestMapping("/api/comunidades")
public class ComunidadController {

    @Autowired
    private ComunidadService comunidadService;

    @GetMapping
    public List<ComunidadDTO> obtenerTodos() {
        return comunidadService.obtenerTodos().stream()
                .map(ComunidadDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComunidadDTO> obtenerPorId(@PathVariable Long id) {
        return comunidadService.obtenerPorId(id)
                .map(c -> ResponseEntity.ok(new ComunidadDTO(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Comunidad crear(@RequestBody Comunidad comunidad) {
        return comunidadService.crear(comunidad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comunidad> actualizar(@PathVariable Long id, @RequestBody Comunidad comunidad) {
        return comunidadService.actualizar(id, comunidad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return comunidadService.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}