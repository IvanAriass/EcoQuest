package com.proyecto.apirest.controllers;

import com.proyecto.apirest.entity.Noticia;
import com.proyecto.apirest.repositories.NoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/noticias")
@CrossOrigin(origins = "*")
public class NoticiaController {

    @Autowired
    private NoticiaRepository noticiaRepository;

    @GetMapping
    public ResponseEntity<List<Noticia>> obtenerTodas() {
        List<Noticia> noticias = noticiaRepository.findAll();
        return new ResponseEntity<>(noticias, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Noticia> obtenerPorId(@PathVariable int id) {
        Optional<Noticia> noticia = noticiaRepository.findById(id);
        return noticia.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Noticia> crear(@RequestBody Noticia noticia) {
        Noticia noticiaGuardada = noticiaRepository.save(noticia);
        return new ResponseEntity<>(noticiaGuardada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Noticia> actualizar(@PathVariable int id, @RequestBody Noticia noticiaActualizada) {
        Optional<Noticia> noticiaExistente = noticiaRepository.findById(id);
        if (noticiaExistente.isPresent()) {
            Noticia noticia = noticiaExistente.get();
            noticia.setAutor(noticiaActualizada.getAutor());
            noticia.setNombre(noticiaActualizada.getNombre());
            noticia.setDescripcion(noticiaActualizada.getDescripcion());
            noticia.setImagen(noticiaActualizada.getImagen());
            Noticia noticiaGuardada = noticiaRepository.save(noticia);
            return new ResponseEntity<>(noticiaGuardada, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (noticiaRepository.existsById(id)) {
            noticiaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
