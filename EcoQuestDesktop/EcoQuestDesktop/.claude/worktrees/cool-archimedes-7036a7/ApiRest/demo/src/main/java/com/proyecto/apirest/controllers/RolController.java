package com.proyecto.apirest.controllers;

import com.proyecto.apirest.entity.Rol;
import com.proyecto.apirest.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    private RolRepository rolRepository;

    @GetMapping
    public ResponseEntity<List<Rol>> obtenerTodos() {
        List<Rol> roles = rolRepository.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerPorId(@PathVariable int id) {
        Optional<Rol> rol = rolRepository.findById(id);
        return rol.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Rol> crear(@RequestBody Rol rol) {
        Rol rolGuardado = rolRepository.save(rol);
        return new ResponseEntity<>(rolGuardado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizar(@PathVariable int id, @RequestBody Rol rolActualizado) {
        Optional<Rol> rolExistente = rolRepository.findById(id);
        if (rolExistente.isPresent()) {
            Rol rol = rolExistente.get();
            rol.setNombreRol(rolActualizado.getNombreRol());
            Rol rolGuardado = rolRepository.save(rol);
            return new ResponseEntity<>(rolGuardado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (rolRepository.existsById(id)) {
            rolRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
