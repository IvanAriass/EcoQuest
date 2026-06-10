package com.proyecto.apirest.controllers;

import com.proyecto.apirest.entity.Comunidad;
import com.proyecto.apirest.repositories.ComunidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comunidades")
@CrossOrigin(origins = "*")
public class ComunidadController {

    @Autowired
    private ComunidadRepository comunidadRepository;

    @GetMapping
    public ResponseEntity<List<Comunidad>> obtenerTodas() {
        List<Comunidad> comunidades = comunidadRepository.findAll();
        return new ResponseEntity<>(comunidades, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comunidad> obtenerPorId(@PathVariable int id) {
        Optional<Comunidad> comunidad = comunidadRepository.findById(id);
        return comunidad.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Comunidad> crear(@RequestBody Comunidad comunidad) {
        Comunidad comunidadGuardada = comunidadRepository.save(comunidad);
        return new ResponseEntity<>(comunidadGuardada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comunidad> actualizar(@PathVariable int id, @RequestBody Comunidad comunidadActualizada) {
        Optional<Comunidad> comunidadExistente = comunidadRepository.findById(id);
        if (comunidadExistente.isPresent()) {
            Comunidad comunidad = comunidadExistente.get();
            comunidad.setNombre(comunidadActualizada.getNombre());
            comunidad.setImagen(comunidadActualizada.getImagen());
            comunidad.setDescripcion(comunidadActualizada.getDescripcion());
            Comunidad comunidadGuardada = comunidadRepository.save(comunidad);
            return new ResponseEntity<>(comunidadGuardada, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (comunidadRepository.existsById(id)) {
            comunidadRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
