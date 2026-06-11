package com.proyecto.spring.dto.auth;

import com.proyecto.spring.dto.UsuarioDTO;

public class AuthResponse {
    public String token;
    public UsuarioDTO usuario;

    public AuthResponse(String token, UsuarioDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }
}
