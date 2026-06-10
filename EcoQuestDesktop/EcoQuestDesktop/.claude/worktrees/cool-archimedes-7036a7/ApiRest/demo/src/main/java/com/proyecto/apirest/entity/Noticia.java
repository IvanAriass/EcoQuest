package com.proyecto.apirest.entity;

import jakarta.persistence.*;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "noticias")
public class Noticia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noticiaId;
    
    @Column(nullable = false)
    private String autor;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    private String imagen;
    
    @Column(nullable = false)
    private String nombre;

    protected Noticia() {
    }

    public Integer getNoticiaId() {
        return noticiaId;
    }

    public void setNoticiaId(Integer noticiaId) {
        this.noticiaId = noticiaId;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public Noticia(Integer noticiaId, String autor, String descripcion, String imagen, String nombre) {
        this.noticiaId = noticiaId;
        this.autor = autor;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.nombre = nombre;
    }
}
