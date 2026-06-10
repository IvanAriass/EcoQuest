/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.apirest.entity;
import java.util.Date;

/**
 *
 * @author alumno
 */


public class UsuarioReto {
    
    private int usuarioRetoId;
    private int usuarioId;
    private int retoId;
    private boolean completado;
    private Date fechaCompletado;

    protected UsuarioReto() {
    }

    public void setUsuarioRetoId(int usuarioRetoId) {
        this.usuarioRetoId = usuarioRetoId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setRetoId(int retoId) {
        this.retoId = retoId;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public void setFechaCompletado(Date fechaCompletado) {
        this.fechaCompletado = fechaCompletado;
    }

    public int getUsuarioRetoId() {
        return usuarioRetoId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public int getRetoId() {
        return retoId;
    }

    public boolean isCompletado() {
        return completado;
    }

    public Date getFechaCompletado() {
        return fechaCompletado;
    }

    public UsuarioReto(int usuarioRetoId, int usuarioId, int retoId, boolean completado, Date fechaCompletado) {
        this.usuarioRetoId = usuarioRetoId;
        this.usuarioId = usuarioId;
        this.retoId = retoId;
        this.completado = completado;
        this.fechaCompletado = fechaCompletado;
    }
}
