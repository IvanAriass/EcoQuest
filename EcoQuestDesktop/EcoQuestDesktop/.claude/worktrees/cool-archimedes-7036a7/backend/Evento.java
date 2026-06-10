/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mockspfg;

import java.util.Date;

/**
 *
 * @author alumno
 */
public class Evento {
    private int eventoId;
    private String nombre;
    private String ubicacion;
    private String descripcion;
    private String imagen;
    private Date fecha;

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getEventoId() {
        return eventoId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public Date getFecha() {
        return fecha;
    }

    public Evento(int eventoId, String nombre, String ubicacion, String descripcion, String imagen, Date fecha) {
        this.eventoId = eventoId;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.fecha = fecha;
    }
}
