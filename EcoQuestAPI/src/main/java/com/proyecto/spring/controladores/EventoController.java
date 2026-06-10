package com.proyecto.spring.controladores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

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
import com.proyecto.spring.dto.EventoDTO;
import com.proyecto.spring.modelos.Evento;
import com.proyecto.spring.servicios.EventoService;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public List<EventoDTO> obtenerTodos() {
        return eventoService.obtenerTodosDTO();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> obtenerPorId(@PathVariable Long id) {
        return eventoService.obtenerPorIdDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public List<EventoDTO> buscarPorNombre(@RequestParam String nombre) {
        return eventoService.buscarPorNombre(nombre);
    }

    @GetMapping("/estado")
    public List<EventoDTO> buscarPorEstado(@RequestParam String estado) {
        return eventoService.buscarPorEstado(estado);
    }

    @GetMapping("/imagen/{nombreImagen}")
    public ResponseEntity<byte[]> getImage(@PathVariable String nombreImagen) {
        try {
            Path imagePath = Paths.get("src/main/resources/static/imagenes/eventos", nombreImagen);

            if (!Files.exists(imagePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] imageBytes = Files.readAllBytes(imagePath);
            String contentType = detectarContentType(nombreImagen);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(imageBytes);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private String detectarContentType(String nombreImagen) {
        String nombre = nombreImagen.toLowerCase();
        if (nombre.endsWith(".jpg") || nombre.endsWith(".jpeg"))
            return "image/jpeg";
        if (nombre.endsWith(".png"))
            return "image/png";
        if (nombre.endsWith(".gif"))
            return "image/gif";
        if (nombre.endsWith(".webp"))
            return "image/webp";
        if (nombre.endsWith(".bmp"))
            return "image/bmp";
        return "application/octet-stream";
    }

    @PostMapping("/comunidad/{comunidadId}")
    public ResponseEntity<Evento> crearEnComunidad(@PathVariable Long comunidadId, @RequestBody Evento evento) {
        return eventoService.crearEnComunidad(comunidadId, evento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/con-imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Evento> crearConImagen(
            @RequestParam("evento") String eventoJson,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

        try {
            Evento evento = objectMapper.readValue(eventoJson, Evento.class);

            if (imagen != null && !imagen.isEmpty()) {
                String nombreArchivo = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
                Path ruta = Paths.get("src/main/resources/static/imagenes/eventos/" + nombreArchivo);
                Files.createDirectories(ruta.getParent());
                Files.write(ruta, imagen.getBytes());
                evento.setImagen(nombreArchivo);
            }

            Evento guardado = eventoService.crear(evento);
            return ResponseEntity.ok(guardado);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
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

    @PatchMapping("/{id}/en-revision")
    public ResponseEntity<Evento> enviarARevision(@PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String motivo = body.getOrDefault("motivo", "Sin especificar");
        return eventoService.enviarARevision(id, motivo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/cancelar-con-motivo")
    public ResponseEntity<Evento> cancelarConMotivo(@PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String motivo = body.getOrDefault("motivo", "Sin especificar");
        return eventoService.cancelarConMotivo(id, motivo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Evento> cancelarEvento(@PathVariable Long id) {
        return eventoService.cambiarEstado(id, "CANCELADO")
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/aprobar")
    public ResponseEntity<Evento> aprobarEvento(@PathVariable Long id) {
        return eventoService.cambiarEstado(id, "ACTIVO")
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
