package com.proyecto.apirest.controllers;

import com.proyecto.apirest.entity.Reto;
import com.proyecto.apirest.repositories.RetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/retos")
@CrossOrigin(origins = "*")
public class RetoController {

    @Autowired
    private RetoRepository retoRepository;

    @GetMapping
    public ResponseEntity<List<Reto>> obtenerTodos() {
        List<Reto> retos = retoRepository.findAll();
        return new ResponseEntity<>(retos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reto> obtenerPorId(@PathVariable int id) {
        Optional<Reto> reto = retoRepository.findById(id);
        return reto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Reto> crear(@RequestBody Reto reto) {
        Reto retoGuardado = retoRepository.save(reto);
        return new ResponseEntity<>(retoGuardado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reto> actualizar(@PathVariable int id, @RequestBody Reto retoActualizado) {
        Optional<Reto> retoExistente = retoRepository.findById(id);
        if (retoExistente.isPresent()) {
            Reto reto = retoExistente.get();
            reto.setNombre(retoActualizado.getNombre());
            reto.setDescripcion(retoActualizado.getDescripcion());
            reto.setPuntos(retoActualizado.getPuntos());
            Reto retoGuardado = retoRepository.save(reto);
            return new ResponseEntity<>(retoGuardado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (retoRepository.existsById(id)) {
            retoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
