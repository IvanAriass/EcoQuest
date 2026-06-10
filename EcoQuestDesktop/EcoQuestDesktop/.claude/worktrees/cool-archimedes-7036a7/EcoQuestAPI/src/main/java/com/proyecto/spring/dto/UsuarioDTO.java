package com.proyecto.spring.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.proyecto.spring.modelos.Evento;
import java.util.List;
import java.util.stream.Collectors;
import com.proyecto.spring.modelos.Usuario;

@JsonPropertyOrder({"id", "nombreUsuario", "nombre", "apellido", "descripcion", "edad", "email", "imagen", "comunidades", "eventos"})
public class UsuarioDTO {
    public Long id;
    public String nombreUsuario;
    public String nombre;
    public String apellido;
    public String descripcion;
    public int edad;
    public String email;
    public String imagen;
    public List<ComunidadResumenDTO> comunidades;
    public List<Evento> eventos;

    public UsuarioDTO(Usuario u) {
        this.id = u.getId();
        this.nombreUsuario = u.getNombreUsuario();
        this.nombre = u.getNombre();
        this.apellido = u.getApellido();
        this.descripcion = u.getDescripcion();
        this.edad = u.getEdad();
        this.email = u.getEmail();
        this.imagen = u.getImagen();
        this.eventos = u.getEventos();
        this.comunidades = u.getComunidades() == null ? List.of() :
            u.getComunidades().stream()
                .map(uc -> new ComunidadResumenDTO(uc.getComunidad().getNombre(), uc.getRol()))
                .collect(Collectors.toList());
    }
}