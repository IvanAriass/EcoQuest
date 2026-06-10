package com.proyecto.apirest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> infoApi() {
        Map<String, Object> info = new HashMap<>();
        info.put("nombre", "API REST - Plataforma Gamificación");
        info.put("version", "1.0.0");
        info.put("estado", "Activa");
        info.put("timestamp", LocalDateTime.now());
        info.put("endpoints", new String[]{
            "/api/usuarios - Gestión de usuarios",
            "/api/comunidades - Gestión de comunidades",
            "/api/eventos - Gestión de eventos",
            "/api/productos - Gestión de productos",
            "/api/retos - Gestión de retos",
            "/api/niveles - Gestión de niveles",
            "/api/roles - Gestión de roles",
            "/api/noticias - Gestión de noticias"
        });
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(health, HttpStatus.OK);
    }
}
