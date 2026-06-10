/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.proyecto.apirest.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usuarioId;
    
    @Column(nullable = false)
    private String contraseña;
    
    @Column(nullable = false)
    private Boolean admin;
    
    @Column(nullable = false, unique = true)
    private String usuario;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String apellidos;
    
    @Column(nullable = false, unique = true)
    private String correo;
    
    private String imagen;
    private String descripcion;
    
    private LocalDate edad;
    
    @ManyToMany
    @JoinTable(name = "usuario_comunidad",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "comunidad_id"))
    private List<Comunidad> comunidades;
    
    @Column(nullable = false)
    private Integer puntos = 0;
    
    @Column(nullable = false)
    private Integer puntosExperiencia = 0;
    
    @ManyToOne
    @JoinColumn(name = "nivel_id")
    private Nivel nivel;
    
    @ManyToMany
    @JoinTable(name = "usuario_producto",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productos;

    protected Usuario() {
    }

    public Usuario(Integer usuarioId, String contraseña, Boolean admin, String usuario, String nombre, String apellidos, String correo, String imagen, String descripcion, LocalDate edad, List<Comunidad> comunidades, Integer puntos, Integer puntosExperiencia, Nivel nivel, List<Producto> productos) {
        this.usuarioId = usuarioId;
        this.contraseña = contraseña;
        this.admin = admin;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.edad = edad;
        this.comunidades = comunidades;
        this.puntos = puntos;
        this.puntosExperiencia = puntosExperiencia;
        this.nivel = nivel;
        this.productos = productos;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public String getContraseña() {
        return contraseña;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public String getImagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getEdad() {
        return edad;
    }

    public List<Comunidad> getComunidades() {
        return comunidades;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public Integer getPuntosExperiencia() {
        return puntosExperiencia;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEdad(LocalDate edad) {
        this.edad = edad;
    }

    public void setComunidades(List<Comunidad> comunidades) {
        this.comunidades = comunidades;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public void setPuntosExperiencia(Integer puntosExperiencia) {
        this.puntosExperiencia = puntosExperiencia;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    
}
