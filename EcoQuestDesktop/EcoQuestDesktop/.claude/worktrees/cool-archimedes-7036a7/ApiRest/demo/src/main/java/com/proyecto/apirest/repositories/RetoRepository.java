package com.proyecto.apirest.repositories;

import com.proyecto.apirest.entity.Reto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetoRepository extends JpaRepository<Reto, Integer> {
    List<Reto> findByNombreContainingIgnoreCase(String nombre);
}
