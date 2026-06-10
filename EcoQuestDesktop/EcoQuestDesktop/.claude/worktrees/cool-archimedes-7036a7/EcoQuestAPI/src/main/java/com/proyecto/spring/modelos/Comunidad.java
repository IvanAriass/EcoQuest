package com.proyecto.spring.modelos;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Comunidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagen;

    @OneToMany(mappedBy = "comunidad", cascade = CascadeType.ALL)
    private List<Evento> eventos;

    @JsonManagedReference("comunidad-usuarios")
    @OneToMany(mappedBy = "comunidad", cascade = CascadeType.ALL)
    private List<UsuarioComunidad> usuarios;

    public Comunidad() {
    }

    public Comunidad(String nombre, String descripcion, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<UsuarioComunidad> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioComunidad> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + ((imagen == null) ? 0 : imagen.hashCode());
        result = prime * result + ((usuarios == null) ? 0 : usuarios.hashCode());
        result = prime * result + ((eventos == null) ? 0 : eventos.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Comunidad other = (Comunidad) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (imagen == null) {
            if (other.imagen != null)
                return false;
        } else if (!imagen.equals(other.imagen))
            return false;
        if (usuarios == null) {
            if (other.usuarios != null)
                return false;
        } else if (!usuarios.equals(other.usuarios))
            return false;
        if (eventos == null) {
            if (other.eventos != null)
                return false;
        } else if (!eventos.equals(other.eventos))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Comunidad [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", imagen=" + imagen
                + ", usuarios=" + usuarios + ", eventos=" + eventos +"]";
    }

}