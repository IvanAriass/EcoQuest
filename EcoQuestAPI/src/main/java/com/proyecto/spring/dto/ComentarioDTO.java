package com.proyecto.spring.dto;

import com.proyecto.spring.modelos.Comentario;

public class ComentarioDTO {

    public Long id;
    public String texto;
    public String fechaHora;
    public Long usuarioId;
    public String usuarioNombre;
    public String usuarioNombreUsuario;
    public String usuarioImagen;
    public Long eventoId;
    public Long comentarioPadreId;
    public int cantidadRespuestas;

    public ComentarioDTO() {}

    public ComentarioDTO(Comentario c) {
        this.id = c.getId();
        this.texto = c.getTexto();
        this.fechaHora = c.getFechaHora() != null ? c.getFechaHora().toString() : null;
        this.usuarioId = c.getUsuario() != null ? c.getUsuario().getId() : null;
        this.usuarioNombre = c.getUsuario() != null ? c.getUsuario().getNombre() + " " + c.getUsuario().getApellido() : null;
        this.usuarioNombreUsuario = c.getUsuario() != null ? c.getUsuario().getNombreUsuario() : "Usuario";
        this.usuarioImagen = c.getUsuario() != null ? c.getUsuario().getImagen() : null;
        this.eventoId = c.getEvento() != null ? c.getEvento().getId() : null;
        this.comentarioPadreId = c.getComentarioPadreId();
    }
}
