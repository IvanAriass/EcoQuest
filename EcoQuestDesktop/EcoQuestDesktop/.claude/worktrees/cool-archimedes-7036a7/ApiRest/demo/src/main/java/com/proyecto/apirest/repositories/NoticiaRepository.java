package com.proyecto.apirest.repositories;

import com.proyecto.apirest.entity.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Integer> {
    List<Noticia> findByNombreContainingIgnoreCase(String nombre);
}
