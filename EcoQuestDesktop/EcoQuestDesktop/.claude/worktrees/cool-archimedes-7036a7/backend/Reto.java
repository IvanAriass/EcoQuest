/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mockspfg;

/**
 *
 * @author alumno
 */
public class Reto {
    private int retoId;
    private String nombre;
    private String descripcion;
    private int puntos;

    public Reto(int retoId, String nombre, String descripcion, int puntos) {
        this.retoId = retoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntos = puntos;
    }

    public int getRetoId() {
        return retoId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setRetoId(int retoId) {
        this.retoId = retoId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

}
