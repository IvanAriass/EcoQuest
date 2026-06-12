package com.proyecto.spring.modelos;

import java.time.LocalDate;
import java.time.Period;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreUsuario;
    private String contraseña;
    private String nombre;
    private String apellido;
    private String descripcion;
    private LocalDate fechaNacimiento;
    private String email;
    private String imagen;
    private boolean bloqueado;
    private String causaBloqueo;
    private LocalDateTime fechaBloqueo;
    private Integer puntos;

    @JsonManagedReference("usuario-comunidades")
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UsuarioComunidad> comunidades;

    @ManyToMany
    @JoinTable(name = "usuario_evento", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "evento_id"))
    private List<Evento> eventos;

    public Usuario() {
    }

    public Usuario(String nombreUsuario, String contraseña, String nombre, String apellido, String descripcion,
            LocalDate fechaNacimiento, String email, String imagen) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.apellido = apellido;
        this.descripcion = descripcion;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.imagen = imagen;
        this.bloqueado = false;
        this.puntos = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEdad() {
        return fechaNacimiento == null ? 0 : Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getCausaBloqueo() {
        return causaBloqueo;
    }

    public void setCausaBloqueo(String causaBloqueo) {
        this.causaBloqueo = causaBloqueo;
    }

    public LocalDateTime getFechaBloqueo() {
        return fechaBloqueo;
    }

    public void setFechaBloqueo(LocalDateTime fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    public int getPuntos() {
        return puntos != null ? puntos : 0;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public List<UsuarioComunidad> getComunidades() {
        return comunidades;
    }

    public void setComunidades(List<UsuarioComunidad> comunidades) {
        this.comunidades = comunidades;
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
        result = prime * result + ((nombreUsuario == null) ? 0 : nombreUsuario.hashCode());
        result = prime * result + ((contraseña == null) ? 0 : contraseña.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + Objects.hashCode(fechaNacimiento);
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((imagen == null) ? 0 : imagen.hashCode());
        result = prime * result + (bloqueado ? 1231 : 1237);
        result = prime * result + ((causaBloqueo == null) ? 0 : causaBloqueo.hashCode());
        result = prime * result + ((fechaBloqueo == null) ? 0 : fechaBloqueo.hashCode());
        result = prime * result + puntos;
        result = prime * result + ((comunidades == null) ? 0 : comunidades.hashCode());
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
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nombreUsuario == null) {
            if (other.nombreUsuario != null)
                return false;
        } else if (!nombreUsuario.equals(other.nombreUsuario))
            return false;
        if (contraseña == null) {
            if (other.contraseña != null)
                return false;
        } else if (!contraseña.equals(other.contraseña))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (apellido == null) {
            if (other.apellido != null)
                return false;
        } else if (!apellido.equals(other.apellido))
            return false;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (!Objects.equals(fechaNacimiento, other.fechaNacimiento))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (imagen == null) {
            if (other.imagen != null)
                return false;
        } else if (!imagen.equals(other.imagen))
            return false;
        if (bloqueado != other.bloqueado)
            return false;
        if (causaBloqueo == null) {
            if (other.causaBloqueo != null)
                return false;
        } else if (!causaBloqueo.equals(other.causaBloqueo))
            return false;
        if (fechaBloqueo == null) {
            if (other.fechaBloqueo != null)
                return false;
        } else if (!fechaBloqueo.equals(other.fechaBloqueo))
            return false;
        if (puntos != other.puntos)
            return false;
        if (comunidades == null) {
            if (other.comunidades != null)
                return false;
        } else if (!comunidades.equals(other.comunidades))
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
        return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + ", contraseña=" + contraseña + ", nombre="
                + nombre + ", apellido=" + apellido + ", descripcion=" + descripcion + ", fechaNacimiento=" + fechaNacimiento + ", email="
                + email + ", imagen=" + imagen + ", bloqueado=" + bloqueado + ", causaBloqueo=" + causaBloqueo
                + ", fechaBloqueo=" + fechaBloqueo + ", puntos=" + puntos + ", comunidades=" + comunidades + ", eventos="
                + eventos + "]";
    }

}