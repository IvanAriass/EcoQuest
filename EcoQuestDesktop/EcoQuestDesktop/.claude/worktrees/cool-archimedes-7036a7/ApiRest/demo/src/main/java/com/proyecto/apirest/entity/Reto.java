package com.proyecto.apirest.entity;

import jakarta.persistence.*;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "retos")
public class Reto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer retoId;
    
    @Column(nullable = false)
    private String nombre;
    
    private String descripcion;
    
    @Column(nullable = false)
    private Integer puntos;
    
    @ManyToMany
    @JoinTable(name = "usuario_reto",
        joinColumns = @JoinColumn(name = "reto_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private java.util.List<Usuario> usuarios;

    protected Reto() {
    }

    public Reto(Integer retoId, String nombre, String descripcion, Integer puntos) {
        this.retoId = retoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntos = puntos;
    }

    public Integer getRetoId() {
        return retoId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setRetoId(Integer retoId) {
        this.retoId = retoId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

}
