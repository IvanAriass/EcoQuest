package com.proyecto.apirest.entity;

import jakarta.persistence.*;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productoId;
    
    @Column(nullable = false)
    private String nombre;
    
    private String descripcion;
    
    @Column(nullable = false)
    private Integer puntos;

    protected Producto() {
    }

    public Producto(Integer productoId, String nombre, String descripcion, Integer puntos) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntos = puntos;
    }

    public Integer getProductoId() {
        return productoId;
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

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
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
