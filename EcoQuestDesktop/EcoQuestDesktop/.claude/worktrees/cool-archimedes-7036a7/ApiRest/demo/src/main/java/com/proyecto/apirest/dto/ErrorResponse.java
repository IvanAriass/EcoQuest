package com.proyecto.apirest.dto;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int status;
    private String mensaje;
    private LocalDateTime timestamp;
    private String path;

    public ErrorResponse(int status, String mensaje, String path) {
        this.status = status;
        this.mensaje = mensaje;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }
}
