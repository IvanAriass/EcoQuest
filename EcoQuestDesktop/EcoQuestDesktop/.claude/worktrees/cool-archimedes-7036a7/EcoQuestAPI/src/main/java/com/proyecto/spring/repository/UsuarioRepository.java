package com.proyecto.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.spring.modelos.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
