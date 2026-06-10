/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mockspfg;

/**
 *
 * @author alumno
 */
public class Producto {
    private int productoId;
    private String nombre;
    private String descripcion;
    private int puntos;

    public Producto(int productoId, String nombre, String descripcion, int puntos) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntos = puntos;
    }

    public int getProductoId() {
        return productoId;
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

    public void setProductoId(int productoId) {
        this.productoId = productoId;
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
