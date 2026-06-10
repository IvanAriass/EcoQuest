package com.proyecto.apirest.entity;

import jakarta.persistence.*;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, unique = true)
    private String nombreRol;
    
    @OneToMany(mappedBy = "rol")
    private java.util.List<Comunidad> comunidades;

    protected Rol() {
    }

    public Rol(Integer id, String nombreRol) {
        this.id = id;
        this.nombreRol = nombreRol;
    }

    public Integer getId() {
        return id;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
