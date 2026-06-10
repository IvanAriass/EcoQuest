package com.proyecto.apirest.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "eventos")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventoId;
    
    @Column(nullable = false)
    private String nombre;
    
    private String ubicacion;
    private String descripcion;
    private String imagen;
    
    private LocalDateTime fecha;
    
    @ManyToOne
    @JoinColumn(name = "comunidad_id")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Comunidad comunidad;
    
    @ManyToMany
    @JoinTable(name = "usuario_evento",
        joinColumns = @JoinColumn(name = "evento_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private java.util.List<Usuario> usuarios;

    protected Evento() {
    }

    public void setEventoId(Integer eventoId) {
        this.eventoId = eventoId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Integer getEventoId() {
        return eventoId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Evento(Integer eventoId, String nombre, String ubicacion, String descripcion, String imagen, LocalDateTime fecha) {
        this.eventoId = eventoId;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.fecha = fecha;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public java.util.List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(java.util.List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
