package com.proyecto.spring.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.spring.modelos.UsuarioCosmetico;
import com.proyecto.spring.servicios.UsuarioCosmeticoService;

@RestController
@RequestMapping("/api/usuarios/{usuarioId}/cosmeticos")
public class UsuarioCosmeticoController {

    @Autowired
    private UsuarioCosmeticoService usuarioCosmeticoService;

    @GetMapping
    public List<UsuarioCosmetico> obtenerCosmeticos(@PathVariable Long usuarioId) {
        return usuarioCosmeticoService.obtenerCosmeticos(usuarioId);
    }

    @GetMapping("/aplicados")
    public List<UsuarioCosmetico> obtenerCosmeticosAplicados(@PathVariable Long usuarioId) {
        return usuarioCosmeticoService.obtenerCosmeticosAplicados(usuarioId);
    }

    @PostMapping("/{productoId}/aplicar")
    public ResponseEntity<String> aplicarCosmetico(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId) {
        boolean resultado = usuarioCosmeticoService.aplicarCosmetico(usuarioId, productoId);
        if (resultado) {
            return ResponseEntity.ok("Cosmetico aplicado correctamente");
        }
        return ResponseEntity.badRequest().body("No se pudo aplicar el cosmetico");
    }

    @PostMapping("/{productoId}/desaplicar")
    public ResponseEntity<String> desaplicarCosmetico(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId) {
        boolean resultado = usuarioCosmeticoService.desaplicarCosmetico(usuarioId, productoId);
        if (resultado) {
            return ResponseEntity.ok("Cosmetico desaplicado correctamente");
        }
        return ResponseEntity.badRequest().body("No se pudo desaplicar el cosmetico");
    }
}
