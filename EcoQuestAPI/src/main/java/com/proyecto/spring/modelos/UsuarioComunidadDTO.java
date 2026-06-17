package com.proyecto.spring.modelos;

import com.proyecto.spring.dto.ComunidadDTO;
import com.proyecto.spring.dto.UsuarioDTO;

public class UsuarioComunidadDTO {

    private Long id;
    private UsuarioDTO usuario;
    private ComunidadDTO comunidad;
    private String rol;
    private String nombreUsuario;
    private String nombreComunidad;

    public UsuarioComunidadDTO() {}

    public UsuarioComunidadDTO(UsuarioComunidad uc) {
        this.id = uc.getId();
        this.usuario = uc.getUsuario() != null ? new UsuarioDTO(uc.getUsuario()) : null;
        this.comunidad = uc.getComunidad() != null ? new ComunidadDTO(uc.getComunidad()) : null;
        this.rol = uc.getRol();
        this.nombreUsuario = uc.getNombreUsuario();
        this.nombreComunidad = uc.getNombreComunidad();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UsuarioDTO getUsuario() { return usuario; }
    public void setUsuario(UsuarioDTO usuario) { this.usuario = usuario; }

    public ComunidadDTO getComunidad() { return comunidad; }
    public void setComunidad(ComunidadDTO comunidad) { this.comunidad = comunidad; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getNombreComunidad() { return nombreComunidad; }
    public void setNombreComunidad(String nombreComunidad) { this.nombreComunidad = nombreComunidad; }
}
