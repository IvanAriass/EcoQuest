package com.proyecto.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.spring.modelos.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
}
