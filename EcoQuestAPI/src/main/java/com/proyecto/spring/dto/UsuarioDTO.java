package com.proyecto.spring.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.proyecto.spring.modelos.Evento;
import java.util.List;
import java.util.stream.Collectors;
import com.proyecto.spring.modelos.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonPropertyOrder({"id", "nombreUsuario", "nombre", "apellido", "descripcion", "fechaNacimiento", "email", "imagen",
    "bloqueado", "causaBloqueo", "fechaBloqueo", "puntos", "comunidades", "eventos"})
public class UsuarioDTO {
    public Long id;
    public String nombreUsuario;
    public String nombre;
    public String apellido;
    public String descripcion;
    public LocalDate fechaNacimiento;
    public String email;
    public String imagen;
    public boolean bloqueado;
    public String causaBloqueo;
    public LocalDateTime fechaBloqueo;
    public int puntos;
    public List<ComunidadResumenDTO> comunidades;
    public List<Evento> eventos;

    public int getEdad() {
        return fechaNacimiento == null ? 0 : java.time.Period.between(fechaNacimiento, java.time.LocalDate.now()).getYears();
    }

    public UsuarioDTO(Usuario u) {
        this.id = u.getId();
        this.nombreUsuario = u.getNombreUsuario();
        this.nombre = u.getNombre();
        this.apellido = u.getApellido();
        this.descripcion = u.getDescripcion();
        this.fechaNacimiento = u.getFechaNacimiento();
        this.email = u.getEmail();
        this.imagen = u.getImagen();
        this.bloqueado = u.isBloqueado();
        this.causaBloqueo = u.getCausaBloqueo();
        this.fechaBloqueo = u.getFechaBloqueo();
        this.puntos = u.getPuntos();
        this.eventos = u.getEventos();
        this.comunidades = u.getComunidades() == null ? List.of() :
            u.getComunidades().stream()
                .map(uc -> new ComunidadResumenDTO(uc.getComunidad().getNombre(), uc.getRol()))
                .collect(Collectors.toList());
    }
}