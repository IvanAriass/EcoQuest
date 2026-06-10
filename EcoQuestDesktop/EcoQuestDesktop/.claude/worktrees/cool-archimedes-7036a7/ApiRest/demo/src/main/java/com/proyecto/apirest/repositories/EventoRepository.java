package com.proyecto.apirest.repositories;

import com.proyecto.apirest.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByNombreContainingIgnoreCase(String nombre);
}
