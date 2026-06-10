package com.proyecto.spring.modelos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
@JsonPropertyOrder({ "id", "nombre", "descripcion", "fechaHora", "ubicacion", "imagen", "estado", "usuarios", "comunidad" })
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaHora;
    private String ubicacion;
    private String imagen;
    private String estado;

    @JsonIgnore
    @ManyToMany(mappedBy = "eventos")
    private List<Usuario> usuarios;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "comunidad_id")
    private Comunidad comunidad;

    public Evento() {
    }

    public Evento(String nombre, String descripcion, LocalDateTime fechaHora, String ubicacion, String imagen,
            String estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
        this.estado = estado;
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

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + ((fechaHora == null) ? 0 : fechaHora.hashCode());
        result = prime * result + ((ubicacion == null) ? 0 : ubicacion.hashCode());
        result = prime * result + ((imagen == null) ? 0 : imagen.hashCode());
        result = prime * result + ((usuarios == null) ? 0 : usuarios.hashCode());
        result = prime * result + ((comunidad == null) ? 0 : comunidad.hashCode());
        result = prime * result + ((estado == null) ? 0 : estado.hashCode());
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
        Evento other = (Evento) obj;
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
        if (fechaHora == null) {
            if (other.fechaHora != null)
                return false;
        } else if (!fechaHora.equals(other.fechaHora))
            return false;
        if (ubicacion == null) {
            if (other.ubicacion != null)
                return false;
        } else if (!ubicacion.equals(other.ubicacion))
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
        if (comunidad == null) {
            if (other.comunidad != null)
                return false;
        } else if (!comunidad.equals(other.comunidad))
            return false;
        if (estado == null) {
            if (other.estado != null)
                return false;
        } else if (!estado.equals(other.estado))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Evento [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fechaHora=" + fechaHora
                + ", ubicacion=" + ubicacion + ", imagen=" + imagen + ", usuarios=" + usuarios + ", comunidad="
                + comunidad + ", estado=" + estado + "]";
    }

}