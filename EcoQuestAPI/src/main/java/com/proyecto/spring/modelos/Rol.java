package com.proyecto.spring.modelos;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int nivel;

    private String emoji;
    private String descripcion;

    @JsonManagedReference("rol-permisos")
    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RolPermiso> permisos;

    public Rol() {}

    public Rol(String nombre, int nivel, String emoji, String descripcion) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.emoji = emoji;
        this.descripcion = descripcion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }
    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public List<RolPermiso> getPermisos() { return permisos; }
    public void setPermisos(List<RolPermiso> permisos) { this.permisos = permisos; }
}
