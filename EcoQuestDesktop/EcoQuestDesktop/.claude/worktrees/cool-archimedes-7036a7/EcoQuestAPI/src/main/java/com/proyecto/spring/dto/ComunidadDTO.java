package com.proyecto.spring.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.proyecto.spring.modelos.Comunidad;
import com.proyecto.spring.modelos.Evento;

@JsonPropertyOrder({ "id", "nombre", "descripcion", "imagen", "usuarios", "eventos" })
public class ComunidadDTO {
    public Long id;
    public String nombre;
    public String descripcion;
    public String imagen;
    public List<UsuarioResumenDTO> usuarios;
    public List<Evento> eventos;

    public ComunidadDTO(Comunidad c) {
        this.id = c.getId();
        this.nombre = c.getNombre();
        this.descripcion = c.getDescripcion();
        this.imagen = c.getImagen();
        this.usuarios = c.getUsuarios() == null ? List.of()
                : c.getUsuarios().stream()
                        .map(uc -> new UsuarioResumenDTO(uc.getUsuario().getNombreUsuario(), uc.getRol()))
                        .collect(Collectors.toList());
        this.eventos = c.getEventos();
    }
}