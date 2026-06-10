package com.proyecto.apirest.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "comunidades")
public class Comunidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comunidadId;
    
    @ManyToMany(mappedBy = "comunidades")
    private List<Usuario> usuarios;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    @OneToMany(mappedBy = "comunidad")
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private List<Evento> eventos;
    
    private String imagen;
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    protected Comunidad() {
    }

    public Integer getComunidadId() {
        return comunidadId;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public String getImagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Rol getRol() {
        return rol;
    }

    public Comunidad(Integer comunidadId, List<Usuario> usuarios, String nombre, List<Evento> eventos, String imagen, String descripcion, Rol rol) {
        this.comunidadId = comunidadId;
        this.usuarios = usuarios;
        this.nombre = nombre;
        this.eventos = eventos;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.rol = rol;
    }

    public void setComunidadId(Integer comunidadId) {
        this.comunidadId = comunidadId;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
