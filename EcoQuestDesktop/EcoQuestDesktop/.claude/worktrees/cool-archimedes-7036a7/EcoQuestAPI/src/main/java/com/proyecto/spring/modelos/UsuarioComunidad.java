package com.proyecto.spring.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario_comunidad")
public class UsuarioComunidad {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference("usuario-comunidades")
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @JsonBackReference("comunidad-usuarios")
    @ManyToOne
    @JoinColumn(name = "comunidad_id")
    private Comunidad comunidad;

    private String rol;

    public UsuarioComunidad() {
    }

    public UsuarioComunidad(Usuario usuario, Comunidad comunidad, String rol) {
        this.usuario = usuario;
        this.comunidad = comunidad;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @JsonProperty("nombreUsuario")
    public String getNombreUsuario() {
        return usuario != null ? usuario.getNombreUsuario() : null;
    }

    @JsonProperty("nombreComunidad")
    public String getNombreComunidad() {
        return comunidad != null ? comunidad.getNombre() : null;
    }
}