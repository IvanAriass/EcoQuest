package com.proyecto.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.spring.modelos.Reto;

@Repository
public interface RetoRepository extends JpaRepository<Reto, Long> {
    List<Reto> findByFrecuencia(String frecuencia);
    List<Reto> findByTipo(String tipo);
}
