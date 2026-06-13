package com.proyecto.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.spring.modelos.CanjeProducto;

@Repository
public interface CanjeProductoRepository extends JpaRepository<CanjeProducto, Long> {
    List<CanjeProducto> findByUsuarioIdOrderByFechaDesc(Long usuarioId);
}
