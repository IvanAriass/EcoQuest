/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mockspfg;

/**
 *
 * @author alumno
 */
public class Nivel {
    private int nivelId;
    private String nombre;
    private String descripcion;
    private int puntos;
    private String icono;

    public Nivel(int nivelId, String nombre, String descripcion, int puntos, String icono) {
        this.nivelId = nivelId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntos = puntos;
        this.icono = icono;
    }

    public int getNivelId() {
        return nivelId;
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

    public String getIcono() {
        return icono;
    }

    public void setNivelId(int nivelId) {
        this.nivelId = nivelId;
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

    public void setIcono(String icono) {
        this.icono = icono;
    }
}
