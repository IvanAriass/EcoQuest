package com.proyecto.spring.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario crear(Usuario usuario) {
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> actualizar(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
            usuario.setContraseña(usuarioActualizado.getContraseña());
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellido(usuarioActualizado.getApellido());
            usuario.setDescripcion(usuarioActualizado.getDescripcion());
            usuario.setEdad(usuarioActualizado.getEdad());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setImagen(usuarioActualizado.getImagen());
            return usuarioRepository.save(usuario);
        });
    }

    public boolean eliminar(Long id) {
        if (!usuarioRepository.existsById(id))
            return false;
        usuarioRepository.deleteById(id);
        return true;
    }
}