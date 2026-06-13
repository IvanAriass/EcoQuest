package com.proyecto.spring.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.spring.modelos.ProgresoReto;

@Repository
public interface ProgresoRetoRepository extends JpaRepository<ProgresoReto, Long> {
    List<ProgresoReto> findByUsuarioId(Long usuarioId);
    Optional<ProgresoReto> findByUsuarioIdAndRetoId(Long usuarioId, Long retoId);
    List<ProgresoReto> findByUsuarioIdAndCompletadoTrue(Long usuarioId);
    List<ProgresoReto> findByRetoIdAndFechaLimiteAfter(Long retoId, LocalDateTime fecha);
    long countByUsuarioIdAndRetoIdAndCompletadoTrue(Long usuarioId, Long retoId);
}
