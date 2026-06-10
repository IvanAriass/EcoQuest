package com.proyecto.spring.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.proyecto.spring.modelos.Evento;

@JsonPropertyOrder({ "id", "nombre", "descripcion", "fechaHora", "ubicacion", "imagen", "estado", "motivoCancelacion", "nombreComunidad", "imagenComunidad" })
public class EventoDTO {

    public Long id;
    public String nombre;
    public String descripcion;
    public String fechaHora;
    public String ubicacion;
    public String imagen;
    public String estado;
    public String motivoCancelacion;
    public String nombreComunidad;
    public String imagenComunidad;

    public EventoDTO() {}

    public EventoDTO(Evento e) {
        this.id = e.getId();
        this.nombre = e.getNombre();
        this.descripcion = e.getDescripcion();
        this.fechaHora = e.getFechaHora() != null ? e.getFechaHora().toString() : null;
        this.ubicacion = e.getUbicacion();
        this.imagen = e.getImagen();
        this.estado = e.getEstado();
        this.motivoCancelacion = e.getMotivoCancelacion();
        this.nombreComunidad = e.getComunidad() != null ? e.getComunidad().getNombre() : null;
        this.imagenComunidad = e.getComunidad() != null ? e.getComunidad().getImagen() : null;
    }
}
