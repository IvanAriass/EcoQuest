/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mockspfg;

import java.util.Date;
import java.util.List;

/**
 *
 * @author alumno
 */
public class Usuario {
    private int usuarioId;
    private String contraseña;
    private boolean admin;
    private String usuario;
    private String nombre;
    private String apellidos;
    private String correo;
    private String imagen;
    private String descripcion;
    private Date edad; // 
    private List<Comunidad> comunidades;
    private int puntos;
    private int puntosExperiencia;
    private Nivel nivel;
    private List<Producto> productos;

    public Usuario(int usuarioId, String contraseña, boolean admin, String usuario, String nombre, String apellidos, String correo, String imagen, String descripcion, Date edad, List<Comunidad> comunidades, int puntos, int puntosExperiencia, Nivel nivel, List<Producto> productos) {
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

    public int getUsuarioId() {
        return usuarioId;
    }

    public String getContraseña() {
        return contraseña;
    }

    public boolean isAdmin() {
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

    public Date getEdad() {
        return edad;
    }

    public List<Comunidad> getComunidades() {
        return comunidades;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getPuntosExperiencia() {
        return puntosExperiencia;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setAdmin(boolean admin) {
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

    public void setEdad(Date edad) {
        this.edad = edad;
    }

    public void setComunidades(List<Comunidad> comunidades) {
        this.comunidades = comunidades;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void setPuntosExperiencia(int puntosExperiencia) {
        this.puntosExperiencia = puntosExperiencia;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    
}
