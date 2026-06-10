/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mockspfg;

import java.util.List;

/**
 *
 * @author alumno
 */

public class Comunidad {
    private int comunidadId;
    private List<Usuario> usuarios;
    private String nombre;
    private List<Evento> eventos;
    private String imagen;
    private String descripcion;
    private Rol rol;

    public int getComunidadId() {
        return comunidadId;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public String getImagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Rol getRol() {
        return rol;
    }

    public Comunidad(int comunidadId, List<Usuario> usuarios, String nombre, List<Evento> eventos, String imagen, String descripcion, Rol rol) {
        this.comunidadId = comunidadId;
        this.usuarios = usuarios;
        this.nombre = nombre;
        this.eventos = eventos;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.rol = rol;
    }

    public void setComunidadId(int comunidadId) {
        this.comunidadId = comunidadId;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
