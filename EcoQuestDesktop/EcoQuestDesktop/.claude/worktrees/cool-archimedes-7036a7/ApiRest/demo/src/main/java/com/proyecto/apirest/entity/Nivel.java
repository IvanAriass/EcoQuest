/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.proyecto.apirest.entity;

import jakarta.persistence.*;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "niveles")
public class Nivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nivelId;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    private String descripcion;
    
    @Column(nullable = false)
    private Integer puntos;
    
    private String icono;

    protected Nivel() {
    }

    public Nivel(Integer nivelId, String nombre, String descripcion, Integer puntos, String icono) {
        this.nivelId = nivelId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntos = puntos;
        this.icono = icono;
    }

    public Integer getNivelId() {
        return nivelId;
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

    public String getIcono() {
        return icono;
    }

    public void setNivelId(Integer nivelId) {
        this.nivelId = nivelId;
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

    public void setIcono(String icono) {
        this.icono = icono;
    }
}
