package com.proyecto.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.spring.modelos.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    List<Mensaje> findByComunidadIdOrderByFechaHoraAsc(Long comunidadId);

    void deleteByComunidadId(Long comunidadId);
}
