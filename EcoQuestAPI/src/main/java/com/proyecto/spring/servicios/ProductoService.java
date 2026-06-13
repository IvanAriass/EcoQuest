package com.proyecto.spring.servicios;

import com.proyecto.spring.modelos.Categoria;
import com.proyecto.spring.modelos.Producto;
import com.proyecto.spring.repository.CategoriaRepository;
import com.proyecto.spring.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Value("${app.base.url}")
    private String baseUrl;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll().stream()
                .map(p -> {
                    if (p.getImagen() != null && !p.getImagen().isBlank() && !p.getImagen().startsWith("http")) {
                        p.setImagen(baseUrl + "/api/productos/imagen/" + p.getImagen());
                    }
                    return p;
                })
                .toList();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id).map(p -> {
            if (p.getImagen() != null && !p.getImagen().isBlank() && !p.getImagen().startsWith("http")) {
                p.setImagen(baseUrl + "/api/productos/imagen/" + p.getImagen());
            }
            return p;
        });
    }

    public Producto crear(Producto producto) {
        resolverCategoria(producto);
        return productoRepository.save(producto);
    }

    public Optional<Producto> actualizar(Long id, Producto productoActualizado) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(productoActualizado.getNombre());
            producto.setPrecio(productoActualizado.getPrecio());

            if (productoActualizado.getImagen() != null && !productoActualizado.getImagen().isBlank()) {
                producto.setImagen(productoActualizado.getImagen());
            }

            resolverCategoria(producto, productoActualizado);

            return productoRepository.save(producto);
        });
    }

    private void resolverCategoria(Producto producto) {
        resolverCategoria(producto, null);
    }

    private void resolverCategoria(Producto producto, Producto productoActualizado) {
        Categoria categoria = productoActualizado != null
                ? productoActualizado.getCategoria()
                : producto.getCategoria();

        if (categoria != null) {
            if (categoria.getId() != null) {
                categoriaRepository.findById(categoria.getId())
                        .ifPresent(producto::setCategoria);
            } else if (categoria.getNombre() != null && !categoria.getNombre().isBlank()) {
                categoriaRepository.findByNombreIgnoreCase(categoria.getNombre())
                        .ifPresent(producto::setCategoria);
            }
        } else if (productoActualizado != null) {
            producto.setCategoria(null);
        }
    }

    public boolean eliminar(Long id) {
        Optional<Producto> productoOpt = productoRepository.findById(id);
        if (productoOpt.isEmpty())
            return false;

        String nombreImagen = productoOpt.get().getImagen();
        if (nombreImagen != null && !nombreImagen.isBlank()) {
            Path rutaImagen = Paths.get("src/main/resources/static/imagenes/productos", nombreImagen);
            try {
                Files.deleteIfExists(rutaImagen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productoRepository.deleteById(id);
        return true;
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(p -> {
                    if (p.getImagen() != null && !p.getImagen().isBlank() && !p.getImagen().startsWith("http")) {
                        p.setImagen(baseUrl + "/api/productos/imagen/" + p.getImagen());
                    }
                    return p;
                })
                .toList();
    }
}