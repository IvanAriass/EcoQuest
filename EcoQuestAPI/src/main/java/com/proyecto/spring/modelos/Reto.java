package com.proyecto.spring.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Reto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private int puntos;
    private String tipo;
    private int requisitoCantidad;
    private String frecuencia;

    public Reto() {
        this.requisitoCantidad = 1;
        this.frecuencia = "ILIMITADA";
    }

    public Reto(String nombre, String descripcion, int puntos, String tipo, int requisitoCantidad, String frecuencia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntos = puntos;
        this.tipo = tipo;
        this.requisitoCantidad = requisitoCantidad;
        this.frecuencia = frecuencia;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getRequisitoCantidad() { return requisitoCantidad; }
    public void setRequisitoCantidad(int requisitoCantidad) { this.requisitoCantidad = requisitoCantidad; }

    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Reto other = (Reto) obj;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Reto [id=" + id + ", nombre=" + nombre + ", puntos=" + puntos + ", tipo=" + tipo + "]";
    }
}
