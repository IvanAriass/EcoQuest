/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mockspfg;

/**
 *
 * @author alumno
 */
public class Noticia {
    private int noticiaId;
    private String autor;
    private String descripcion;
    private String imagen;
    private String nombre;

    public int getNoticiaId() {
        return noticiaId;
    }

    public void setNoticiaId(int noticiaId) {
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

    public Noticia(int noticiaId, String autor, String descripcion, String imagen, String nombre) {
        this.noticiaId = noticiaId;
        this.autor = autor;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.nombre = nombre;
    }
}
