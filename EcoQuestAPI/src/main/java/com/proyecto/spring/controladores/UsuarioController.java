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
import com.proyecto.spring.dto.UsuarioDTO;
import com.proyecto.spring.modelos.Usuario;
import com.proyecto.spring.repository.UsuarioRepository;
import com.proyecto.spring.servicios.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<UsuarioDTO> obtenerTodos() {
        return usuarioService.obtenerTodos().stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(u -> ResponseEntity.ok(new UsuarioDTO(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public List<UsuarioDTO> buscarPorNombre(@RequestParam String nombre) {
        return usuarioService.buscarPorNombre(nombre).stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/imagen/{nombreImagen}")
    public ResponseEntity<byte[]> getImage(@PathVariable String nombreImagen) {
        try {
            Path imagePath = Paths.get("src/main/resources/static/imagenes/usuarios", nombreImagen);

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

    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioService.crear(usuario);
    }

    @PostMapping(value = "/con-imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Usuario> crearConImagen(
            @RequestParam("usuario") String usuarioJson,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Usuario usuario = mapper.readValue(usuarioJson, Usuario.class);

            if (imagen != null && !imagen.isEmpty()) {
                String nombreArchivo = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
                Path ruta = Paths.get("src/main/resources/static/imagenes/usuarios/" + nombreArchivo);
                Files.createDirectories(ruta.getParent());
                Files.write(ruta, imagen.getBytes());
                usuario.setImagen(nombreArchivo);
            }

            Usuario guardado = usuarioService.crear(usuario);
            return ResponseEntity.ok(guardado);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizar(id, usuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}/con-imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Usuario> actualizarConImagen(
            @PathVariable Long id,
            @RequestParam("usuario") String usuarioJson,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Usuario datosActualizados = mapper.readValue(usuarioJson, Usuario.class);

            if (imagen != null && !imagen.isEmpty()) {
                usuarioRepository.findById(id).ifPresent(usuarioViejo -> {
                    String viejaImagen = usuarioViejo.getImagen();
                    if (viejaImagen != null && !viejaImagen.isBlank()) {
                        try {
                            Files.deleteIfExists(Paths.get("src/main/resources/static/imagenes/usuarios", viejaImagen));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                String nombreArchivo = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
                Path ruta = Paths.get("src/main/resources/static/imagenes/usuarios/" + nombreArchivo);
                Files.createDirectories(ruta.getParent());
                Files.write(ruta, imagen.getBytes());
                datosActualizados.setImagen(nombreArchivo);
            }

            return usuarioService.actualizar(id, datosActualizados)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/todos")
    public List<UsuarioDTO> obtenerTodosSinFiltro() {
        return usuarioService.obtenerTodosSinFiltro().stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/bloqueados")
    public List<UsuarioDTO> obtenerBloqueados() {
        return usuarioService.obtenerBloqueados().stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}/bloquear")
    public ResponseEntity<UsuarioDTO> bloquear(@PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String causa = body.getOrDefault("causa", "Sin especificar");
        return usuarioService.bloquear(id, causa)
                .map(u -> ResponseEntity.ok(new UsuarioDTO(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/desbloquear")
    public ResponseEntity<UsuarioDTO> desbloquear(@PathVariable Long id) {
        return usuarioService.desbloquear(id)
                .map(u -> ResponseEntity.ok(new UsuarioDTO(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return usuarioService.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}