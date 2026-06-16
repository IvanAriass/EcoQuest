package com.proyecto.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.spring.modelos.UsuarioCosmetico;

@Repository
public interface UsuarioCosmeticoRepository extends JpaRepository<UsuarioCosmetico, Long> {
    List<UsuarioCosmetico> findByUsuarioIdOrderByFechaCanjeDesc(Long usuarioId);
    List<UsuarioCosmetico> findByUsuarioIdAndAplicadoTrue(Long usuarioId);
    Optional<UsuarioCosmetico> findByUsuarioIdAndProductoId(Long usuarioId, Long productoId);
    boolean existsByUsuarioIdAndProductoId(Long usuarioId, Long productoId);
}
