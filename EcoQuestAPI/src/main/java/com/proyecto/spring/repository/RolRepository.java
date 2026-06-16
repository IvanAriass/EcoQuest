package com.proyecto.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.spring.modelos.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(String nombre);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Rol r " +
           "JOIN r.permisos p WHERE r.nombre = :rolNombre AND p.permiso = :permiso")
    boolean tienePermiso(@Param("rolNombre") String rolNombre, @Param("permiso") String permiso);

    boolean existsByNombre(String nombre);
}
