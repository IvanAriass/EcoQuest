package com.proyecto.spring.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "roles_permisos")
public class RolPermiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference("rol-permisos")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @Column(nullable = false)
    private String permiso;

    public RolPermiso() {}

    public RolPermiso(Rol rol, String permiso) {
        this.rol = rol;
        this.permiso = permiso;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    public String getPermiso() { return permiso; }
    public void setPermiso(String permiso) { this.permiso = permiso; }
}
