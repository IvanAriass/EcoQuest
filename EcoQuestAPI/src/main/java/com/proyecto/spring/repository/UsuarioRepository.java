package com.proyecto.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.spring.modelos.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNombreUsuarioContainingIgnoreCase(String nombreUsuario);
    List<Usuario> findByBloqueadoTrue();
    List<Usuario> findByBloqueadoFalse();
    List<Usuario> findByNombreUsuarioContainingIgnoreCaseAndBloqueadoFalse(String nombreUsuario);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
