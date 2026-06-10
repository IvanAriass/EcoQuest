package com.proyecto.apirest.repositories;

import com.proyecto.apirest.entity.Comunidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComunidadRepository extends JpaRepository<Comunidad, Integer> {
    Optional<Comunidad> findByNombre(String nombre);
}
