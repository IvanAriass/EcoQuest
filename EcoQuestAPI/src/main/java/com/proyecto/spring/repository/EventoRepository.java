package com.proyecto.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.spring.modelos.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByNombreContainingIgnoreCase(String nombre);

    List<Evento> findByEstado(String estado);
}
