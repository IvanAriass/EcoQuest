package com.proyecto.spring.controladores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.spring.modelos.Producto;
import com.proyecto.spring.servicios.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public List<Producto> buscarPorNombre(@RequestParam String nombre) {
        return productoService.buscarPorNombre(nombre);
    }

    @GetMapping("/imagen/{nombreImagen}")
    public ResponseEntity<byte[]> getImage(@PathVariable String nombreImagen) {
        try {
            Path imagePath = Paths.get("src/main/resources/static/imagenes/productos", nombreImagen);

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
    public Producto crear(@RequestBody Producto producto) {
        return productoService.crear(producto);
    }

    @PostMapping(value = "/con-imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> crearConImagen(
            @RequestParam("producto") String productoJson, // <-- cambia @RequestPart por @RequestParam
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Producto producto = mapper.readValue(productoJson, Producto.class);

            if (imagen != null && !imagen.isEmpty()) {
                String nombreArchivo = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
                Path ruta = Paths.get("src/main/resources/static/imagenes/productos/" + nombreArchivo);
                Files.createDirectories(ruta.getParent());
                Files.write(ruta, imagen.getBytes());
                producto.setImagen(nombreArchivo);
            }

            Producto guardado = productoService.crear(producto);
            return ResponseEntity.ok(guardado);

        } catch (Exception e) {
            e.printStackTrace(); // verás el error exacto en la consola de Spring
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{id}/con-imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> actualizarConImagen(
            @PathVariable Long id,
            @RequestParam("producto") String productoJson,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Producto datosActualizados = mapper.readValue(productoJson, Producto.class);

            if (imagen != null && !imagen.isEmpty()) {
                // Borrar imagen anterior si existe
                productoService.obtenerPorId(id).ifPresent(productoViejo -> {
                    String viejaImagen = productoViejo.getImagen();
                    if (viejaImagen != null && !viejaImagen.isBlank()) {
                        try {
                            Files.deleteIfExists(Paths.get("src/main/resources/static/imagenes/productos", viejaImagen));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                String nombreArchivo = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
                Path ruta = Paths.get("src/main/resources/static/imagenes/productos/" + nombreArchivo);
                Files.createDirectories(ruta.getParent());
                Files.write(ruta, imagen.getBytes());
                datosActualizados.setImagen(nombreArchivo);
            }

            return productoService.actualizar(id, datosActualizados)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizar(id, producto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return productoService.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}