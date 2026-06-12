package com.proyecto.spring.modelos;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProgresoReto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "reto_id")
    private Reto reto;

    private int progreso;
    private boolean completado;
    private LocalDateTime fechaUltimaActualizacion;
    private LocalDateTime fechaLimite;

    public ProgresoReto() {}

    public ProgresoReto(Usuario usuario, Reto reto, LocalDateTime fechaLimite) {
        this.usuario = usuario;
        this.reto = reto;
        this.progreso = 0;
        this.completado = false;
        this.fechaUltimaActualizacion = LocalDateTime.now();
        this.fechaLimite = fechaLimite;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Reto getReto() { return reto; }
    public void setReto(Reto reto) { this.reto = reto; }

    public int getProgreso() { return progreso; }
    public void setProgreso(int progreso) { this.progreso = progreso; }

    public boolean isCompletado() { return completado; }
    public void setCompletado(boolean completado) { this.completado = completado; }

    public LocalDateTime getFechaUltimaActualizacion() { return fechaUltimaActualizacion; }
    public void setFechaUltimaActualizacion(LocalDateTime fechaUltimaActualizacion) { this.fechaUltimaActualizacion = fechaUltimaActualizacion; }

    public LocalDateTime getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDateTime fechaLimite) { this.fechaLimite = fechaLimite; }
}
