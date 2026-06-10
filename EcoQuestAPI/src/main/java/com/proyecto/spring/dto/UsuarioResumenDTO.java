package com.proyecto.spring.dto;

public class UsuarioResumenDTO {
    public String nombreUsuario;
    public String rol;

    public UsuarioResumenDTO(String nombreUsuario, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }
}