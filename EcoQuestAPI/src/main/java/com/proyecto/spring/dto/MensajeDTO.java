package com.proyecto.spring.dto;

import com.proyecto.spring.modelos.Mensaje;

public class MensajeDTO {

    public Long id;
    public String texto;
    public String fechaHora;
    public Long usuarioId;
    public String usuarioNombre;
    public String usuarioNombreUsuario;
    public String usuarioImagen;
    public Long comunidadId;

    public MensajeDTO() {}

    public MensajeDTO(Mensaje m) {
        this.id = m.getId();
        this.texto = m.getTexto();
        this.fechaHora = m.getFechaHora() != null ? m.getFechaHora().toString() : null;
        this.usuarioId = m.getUsuario() != null ? m.getUsuario().getId() : null;
        this.usuarioNombre = m.getUsuario() != null ? m.getUsuario().getNombre() + " " + m.getUsuario().getApellido() : null;
        this.usuarioNombreUsuario = m.getUsuario() != null ? m.getUsuario().getNombreUsuario() : "Usuario";
        this.usuarioImagen = m.getUsuario() != null ? m.getUsuario().getImagen() : null;
        this.comunidadId = m.getComunidad() != null ? m.getComunidad().getId() : null;
    }
}
