package com.proyecto.spring.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proyecto.spring.modelos.TransaccionPuntos;

@Repository
public interface TransaccionPuntosRepository extends JpaRepository<TransaccionPuntos, Long> {
    List<TransaccionPuntos> findByUsuarioIdOrderByFechaDesc(Long usuarioId);

    @Query("SELECT COALESCE(SUM(t.puntos), 0) FROM TransaccionPuntos t WHERE t.usuario.id = :usuarioId AND t.tipo = 'GANADO'")
    int totalGanadosByUsuarioId(Long usuarioId);

    @Query("SELECT COALESCE(SUM(t.puntos), 0) FROM TransaccionPuntos t WHERE t.usuario.id = :usuarioId AND t.tipo = 'CANJEADO'")
    int totalCanjeadosByUsuarioId(Long usuarioId);

    List<TransaccionPuntos> findByUsuarioIdAndFechaAfterOrderByFechaDesc(Long usuarioId, LocalDateTime fecha);

    boolean existsByUsuarioIdAndTipoAndReferenciaId(Long usuarioId, String tipo, Long referenciaId);
}
