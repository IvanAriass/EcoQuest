package com.proyecto.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.spring.modelos.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByEventoIdAndComentarioPadreIsNullOrderByFechaHoraAsc(Long eventoId);

    @Query("SELECT c FROM Comentario c WHERE c.comentarioPadre.id = :comentarioPadreId ORDER BY c.fechaHora ASC")
    List<Comentario> findByComentarioPadreId(@Param("comentarioPadreId") Long comentarioPadreId);

    @Query("SELECT COUNT(c) FROM Comentario c WHERE c.comentarioPadre.id = :comentarioPadreId")
    long countByComentarioPadreId(@Param("comentarioPadreId") Long comentarioPadreId);

    void deleteByEventoId(Long eventoId);
}
