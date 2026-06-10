/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alumno
 */
package com.proyecto.apirest.entity;

public class UsuarioComunidad {
    private int usuarioComunidadId;
    private int usuarioId;
    private int comunidadId;
    private Rol rol;
    public UsuarioComunidad(int usuarioComunidadId, int usuarioId, int comunidadId, Rol rol) {
        this.usuarioComunidadId = usuarioComunidadId;
        this.usuarioId = usuarioId;
        this.comunidadId = comunidadId;
        this.rol = rol;
    }

    public void setUsuarioComunidadId(int usuarioComunidadId) {
        this.usuarioComunidadId = usuarioComunidadId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setComunidadId(int comunidadId) {
        this.comunidadId = comunidadId;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public int getUsuarioComunidadId() {
        return usuarioComunidadId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public int getComunidadId() {
        return comunidadId;
    }

    public Rol getRol() {
        return rol;
    }
     
}
