package com.proyecto.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.spring.modelos.Comunidad;

@Repository
public interface ComunidadRepository extends JpaRepository<Comunidad, Long> {
    java.util.List<Comunidad> findByEstado(String estado);
}
