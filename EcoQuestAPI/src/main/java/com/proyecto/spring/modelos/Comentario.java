package com.proyecto.spring.modelos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String texto;
    private LocalDateTime fechaHora;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "comentario_padre_id")
    private Comentario comentarioPadre;

    public Comentario() {
    }

    public Comentario(String texto, LocalDateTime fechaHora, Usuario usuario, Evento evento) {
        this.texto = texto;
        this.fechaHora = fechaHora;
        this.usuario = usuario;
        this.evento = evento;
    }

    public Comentario(String texto, LocalDateTime fechaHora, Usuario usuario, Evento evento, Comentario comentarioPadre) {
        this.texto = texto;
        this.fechaHora = fechaHora;
        this.usuario = usuario;
        this.evento = evento;
        this.comentarioPadre = comentarioPadre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Comentario getComentarioPadre() {
        return comentarioPadre;
    }

    public void setComentarioPadre(Comentario comentarioPadre) {
        this.comentarioPadre = comentarioPadre;
    }

    public Long getComentarioPadreId() {
        return comentarioPadre != null ? comentarioPadre.getId() : null;
    }
}
