package com.proyecto.spring.servicios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.spring.modelos.Usuario;
import com.proyecto.spring.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${app.base.url}")
    private String baseUrl;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findByBloqueadoFalse().stream()
                .map(u -> {
                    if (u.getImagen() != null && !u.getImagen().isBlank()) {
                        u.setImagen(baseUrl + "/api/usuarios/imagen/" + u.getImagen());
                    }
                    return u;
                })
                .toList();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id).map(u -> {
            if (u.getImagen() != null && !u.getImagen().isBlank()) {
                u.setImagen(baseUrl + "/api/usuarios/imagen/" + u.getImagen());
            }
            return u;
        });
    }

    public Usuario crear(Usuario usuario) {
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> actualizar(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
            if (usuarioActualizado.getContraseña() != null && !usuarioActualizado.getContraseña().isBlank()) {
                usuario.setContraseña(passwordEncoder.encode(usuarioActualizado.getContraseña()));
            }
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellido(usuarioActualizado.getApellido());
            usuario.setDescripcion(usuarioActualizado.getDescripcion());
            usuario.setEdad(usuarioActualizado.getEdad());
            usuario.setEmail(usuarioActualizado.getEmail());
            if (usuarioActualizado.getImagen() != null && !usuarioActualizado.getImagen().isBlank()) {
                usuario.setImagen(usuarioActualizado.getImagen());
            }
            return usuarioRepository.save(usuario);
        });
    }

    public boolean eliminar(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty())
            return false;

        String nombreImagen = usuarioOpt.get().getImagen();
        if (nombreImagen != null && !nombreImagen.isBlank()) {
            Path rutaImagen = Paths.get("src/main/resources/static/imagenes/usuarios", nombreImagen);
            try {
                Files.deleteIfExists(rutaImagen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        usuarioRepository.deleteById(id);
        return true;
    }

    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombreUsuarioContainingIgnoreCaseAndBloqueadoFalse(nombre).stream()
                .map(u -> {
                    if (u.getImagen() != null && !u.getImagen().isBlank()) {
                        u.setImagen(baseUrl + "/api/usuarios/imagen/" + u.getImagen());
                    }
                    return u;
                })
                .toList();
    }

    public List<Usuario> obtenerTodosSinFiltro() {
        return usuarioRepository.findAll().stream()
                .map(u -> {
                    if (u.getImagen() != null && !u.getImagen().isBlank()) {
                        u.setImagen(baseUrl + "/api/usuarios/imagen/" + u.getImagen());
                    }
                    return u;
                })
                .toList();
    }

    public List<Usuario> obtenerBloqueados() {
        return usuarioRepository.findByBloqueadoTrue().stream()
                .map(u -> {
                    if (u.getImagen() != null && !u.getImagen().isBlank()) {
                        u.setImagen(baseUrl + "/api/usuarios/imagen/" + u.getImagen());
                    }
                    return u;
                })
                .toList();
    }

    public Optional<Usuario> bloquear(Long id, String causa) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setBloqueado(true);
            usuario.setCausaBloqueo(causa);
            usuario.setFechaBloqueo(LocalDateTime.now());
            return usuarioRepository.save(usuario);
        });
    }

    public Optional<Usuario> desbloquear(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setBloqueado(false);
            usuario.setCausaBloqueo(null);
            usuario.setFechaBloqueo(null);
            return usuarioRepository.save(usuario);
        });
    }
}