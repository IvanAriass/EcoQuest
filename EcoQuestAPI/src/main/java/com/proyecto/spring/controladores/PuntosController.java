package com.proyecto.spring.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.spring.modelos.CanjeProducto;
import com.proyecto.spring.modelos.ProgresoReto;
import com.proyecto.spring.modelos.Reto;
import com.proyecto.spring.modelos.TransaccionPuntos;
import com.proyecto.spring.servicios.PuntosService;
import com.proyecto.spring.servicios.ResultadoCanje;

@RestController
@RequestMapping("/api")
public class PuntosController {

    @Autowired
    private PuntosService puntosService;

    // --- Retos ---

    @GetMapping("/retos")
    public List<Reto> obtenerRetos() {
        return puntosService.obtenerRetos();
    }

    @GetMapping("/retos/{id}")
    public ResponseEntity<Reto> obtenerReto(@PathVariable Long id) {
        return puntosService.obtenerRetoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- Progreso del usuario ---

    @GetMapping("/usuarios/{usuarioId}/progreso-retos")
    public List<ProgresoReto> obtenerProgresoRetos(@PathVariable Long usuarioId) {
        return puntosService.obtenerProgresoUsuario(usuarioId);
    }

    // --- Historial de puntos ---

    @GetMapping("/usuarios/{usuarioId}/puntos/historial")
    public List<TransaccionPuntos> obtenerHistorial(@PathVariable Long usuarioId) {
        return puntosService.obtenerHistorial(usuarioId);
    }

    @GetMapping("/usuarios/{usuarioId}/puntos/saldo")
    public ResponseEntity<Integer> obtenerSaldo(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(puntosService.calcularSaldo(usuarioId));
    }

    // --- Canje de productos ---

    @PostMapping("/usuarios/{usuarioId}/canjear/{productoId}")
    public ResponseEntity<String> canjearProducto(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId) {
        ResultadoCanje resultado = puntosService.canjearProducto(usuarioId, productoId);
        return switch (resultado) {
            case EXITO -> ResponseEntity.ok("Producto canjeado correctamente");
            case SALDO_INSUFICIENTE -> ResponseEntity.badRequest().body("Saldo insuficiente");
            case PRODUCTO_YA_CANJEADO -> ResponseEntity.badRequest().body("Ya tienes este producto");
            case DATOS_INVALIDOS -> ResponseEntity.badRequest().body("Datos invalidos");
        };
    }

    @GetMapping("/usuarios/{usuarioId}/canjes")
    public List<CanjeProducto> obtenerCanjes(@PathVariable Long usuarioId) {
        return puntosService.obtenerCanjes(usuarioId);
    }
}
