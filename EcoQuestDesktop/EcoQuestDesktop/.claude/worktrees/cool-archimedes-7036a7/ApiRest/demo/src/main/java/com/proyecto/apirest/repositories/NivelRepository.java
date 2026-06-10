package com.proyecto.apirest.repositories;

import com.proyecto.apirest.entity.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Integer> {
    Optional<Nivel> findByNombre(String nombre);
}
