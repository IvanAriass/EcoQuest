package com.proyecto.apirest.controllers;

import com.proyecto.apirest.entity.Nivel;
import com.proyecto.apirest.repositories.NivelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/niveles")
@CrossOrigin(origins = "*")
public class NivelController {

    @Autowired
    private NivelRepository nivelRepository;

    @GetMapping
    public ResponseEntity<List<Nivel>> obtenerTodos() {
        List<Nivel> niveles = nivelRepository.findAll();
        return new ResponseEntity<>(niveles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nivel> obtenerPorId(@PathVariable int id) {
        Optional<Nivel> nivel = nivelRepository.findById(id);
        return nivel.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Nivel> crear(@RequestBody Nivel nivel) {
        Nivel nivelGuardado = nivelRepository.save(nivel);
        return new ResponseEntity<>(nivelGuardado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nivel> actualizar(@PathVariable int id, @RequestBody Nivel nivelActualizado) {
        Optional<Nivel> nivelExistente = nivelRepository.findById(id);
        if (nivelExistente.isPresent()) {
            Nivel nivel = nivelExistente.get();
            nivel.setNombre(nivelActualizado.getNombre());
            nivel.setDescripcion(nivelActualizado.getDescripcion());
            nivel.setPuntos(nivelActualizado.getPuntos());
            nivel.setIcono(nivelActualizado.getIcono());
            Nivel nivelGuardado = nivelRepository.save(nivel);
            return new ResponseEntity<>(nivelGuardado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (nivelRepository.existsById(id)) {
            nivelRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
