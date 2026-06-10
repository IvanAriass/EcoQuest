package com.proyecto.apirest.controllers;

import com.proyecto.apirest.entity.Evento;
import com.proyecto.apirest.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping
    public ResponseEntity<List<Evento>> obtenerTodos() {
        List<Evento> eventos = eventoRepository.findAll();
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerPorId(@PathVariable int id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        return evento.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Evento> crear(@RequestBody Evento evento) {
        Evento eventoGuardado = eventoRepository.save(evento);
        return new ResponseEntity<>(eventoGuardado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> actualizar(@PathVariable int id, @RequestBody Evento eventoActualizado) {
        Optional<Evento> eventoExistente = eventoRepository.findById(id);
        if (eventoExistente.isPresent()) {
            Evento evento = eventoExistente.get();
            evento.setNombre(eventoActualizado.getNombre());
            evento.setUbicacion(eventoActualizado.getUbicacion());
            evento.setDescripcion(eventoActualizado.getDescripcion());
            evento.setImagen(eventoActualizado.getImagen());
            evento.setFecha(eventoActualizado.getFecha());
            Evento eventoGuardado = eventoRepository.save(evento);
            return new ResponseEntity<>(eventoGuardado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
