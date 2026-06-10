/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mockspfg;

import java.util.Date;

/**
 *
 * @author alumno
 */
public class UsuarioEvento {

    private int usuarioEventoId;
    private int usuarioId;
    private int eventoId;
    private Date fechaRegistro;
    private boolean asistencia;

    public UsuarioEvento(int usuarioEventoId, int usuarioId, int eventoId, Date fechaRegistro, boolean asistencia) {
        this.usuarioEventoId = usuarioEventoId;
        this.usuarioId = usuarioId;
        this.eventoId = eventoId;
        this.fechaRegistro = fechaRegistro;
        this.asistencia = asistencia;
    }

    public int getUsuarioEventoId() {
        return usuarioEventoId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public int getEventoId() {
        return eventoId;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public boolean isAsistencia() {
        return asistencia;
    }

    public void setUsuarioEventoId(int usuarioEventoId) {
        this.usuarioEventoId = usuarioEventoId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }

}
