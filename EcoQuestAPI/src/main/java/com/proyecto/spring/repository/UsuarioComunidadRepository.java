package com.proyecto.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.spring.modelos.UsuarioComunidad;

@Repository
public interface UsuarioComunidadRepository extends JpaRepository<UsuarioComunidad, Long> {
    List<UsuarioComunidad> findByUsuarioId(Long usuarioId);

    List<UsuarioComunidad> findByComunidadId(Long comunidadId);

    Optional<UsuarioComunidad> findByUsuarioIdAndComunidadId(Long usuarioId, Long comunidadId);
}
