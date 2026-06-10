package com.proyecto.spring.controladores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.spring.dto.ComunidadDTO;
import com.proyecto.spring.modelos.Comunidad;
import com.proyecto.spring.servicios.ComunidadService;

@RestController
@RequestMapping("/api/comunidades")
public class ComunidadController {

    @Autowired
    private ComunidadService comunidadService;

    @Autowired
    private ObjectMapper objectMapper;

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

    @GetMapping("/estado")
    public List<ComunidadDTO> buscarPorEstado(@RequestParam String estado) {
        return comunidadService.buscarPorEstado(estado).stream()
                .map(ComunidadDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/imagen/{nombreImagen}")
    public ResponseEntity<byte[]> getImage(@PathVariable String nombreImagen) {
        try {
            Path imagePath = Paths.get("src/main/resources/static/imagenes/comunidades", nombreImagen);

            if (!Files.exists(imagePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] imageBytes = Files.readAllBytes(imagePath);
            String nombre = nombreImagen.toLowerCase();
            String contentType;
            if (nombre.endsWith(".jpg") || nombre.endsWith(".jpeg"))
                contentType = "image/jpeg";
            else if (nombre.endsWith(".png"))
                contentType = "image/png";
            else if (nombre.endsWith(".gif"))
                contentType = "image/gif";
            else if (nombre.endsWith(".webp"))
                contentType = "image/webp";
            else if (nombre.endsWith(".bmp"))
                contentType = "image/bmp";
            else
                contentType = "application/octet-stream";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(imageBytes);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public Comunidad crear(@RequestBody Comunidad comunidad) {
        if (comunidad.getEstado() == null) {
            comunidad.setEstado("ACTIVO");
        }
        return comunidadService.crear(comunidad);
    }

    @PostMapping(value = "/con-imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Comunidad> crearConImagen(
            @RequestParam("comunidad") String comunidadJson,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

        try {
            Comunidad comunidad = objectMapper.readValue(comunidadJson, Comunidad.class);

            if (comunidad.getEstado() == null) {
                comunidad.setEstado("ACTIVO");
            }

            if (imagen != null && !imagen.isEmpty()) {
                String nombreArchivo = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
                Path ruta = Paths.get("src/main/resources/static/imagenes/comunidades/" + nombreArchivo);
                Files.createDirectories(ruta.getParent());
                Files.write(ruta, imagen.getBytes());
                comunidad.setImagen(nombreArchivo);
            }

            Comunidad guardado = comunidadService.crear(comunidad);
            return ResponseEntity.ok(guardado);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comunidad> actualizar(@PathVariable Long id, @RequestBody Comunidad comunidad) {
        return comunidadService.actualizar(id, comunidad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/en-revision")
    public ResponseEntity<Comunidad> enviarARevision(@PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String motivo = body.getOrDefault("motivo", "Sin especificar");
        return comunidadService.enviarARevision(id, motivo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Comunidad> cancelar(@PathVariable Long id) {
        return comunidadService.cambiarEstado(id, "CANCELADO")
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/revisar")
    public ResponseEntity<Comunidad> revisar(@PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String motivo = body.getOrDefault("motivo", "Sin especificar");
        return comunidadService.revisar(id, motivo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/aprobar")
    public ResponseEntity<Comunidad> aprobar(@PathVariable Long id) {
        return comunidadService.cambiarEstado(id, "ACTIVO")
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
