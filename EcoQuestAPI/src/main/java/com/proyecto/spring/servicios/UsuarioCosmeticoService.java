package com.proyecto.spring.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.spring.modelos.UsuarioCosmetico;
import com.proyecto.spring.repository.UsuarioCosmeticoRepository;

@Service
public class UsuarioCosmeticoService {

    @Autowired
    private UsuarioCosmeticoRepository usuarioCosmeticoRepository;

    public List<UsuarioCosmetico> obtenerCosmeticos(Long usuarioId) {
        return usuarioCosmeticoRepository.findByUsuarioIdOrderByFechaCanjeDesc(usuarioId);
    }

    public List<UsuarioCosmetico> obtenerCosmeticosAplicados(Long usuarioId) {
        return usuarioCosmeticoRepository.findByUsuarioIdAndAplicadoTrue(usuarioId);
    }

    public boolean existeCosmetico(Long usuarioId, Long productoId) {
        return usuarioCosmeticoRepository.existsByUsuarioIdAndProductoId(usuarioId, productoId);
    }

    @Transactional
    public boolean aplicarCosmetico(Long usuarioId, Long productoId) {
        Optional<UsuarioCosmetico> opt = usuarioCosmeticoRepository.findByUsuarioIdAndProductoId(usuarioId, productoId);
        if (opt.isEmpty()) return false;
        UsuarioCosmetico uc = opt.get();
        if (uc.isAplicado()) return false;

        desactivarCosmeticosDelTipo(usuarioId, uc.getProducto().getTipo());
        uc.setAplicado(true);
        usuarioCosmeticoRepository.save(uc);
        return true;
    }

    @Transactional
    public boolean desaplicarCosmetico(Long usuarioId, Long productoId) {
        Optional<UsuarioCosmetico> opt = usuarioCosmeticoRepository.findByUsuarioIdAndProductoId(usuarioId, productoId);
        if (opt.isEmpty()) return false;
        UsuarioCosmetico uc = opt.get();
        if (!uc.isAplicado()) return false;
        uc.setAplicado(false);
        usuarioCosmeticoRepository.save(uc);
        return true;
    }

    private void desactivarCosmeticosDelTipo(Long usuarioId, String tipo) {
        List<UsuarioCosmetico> aplicados = usuarioCosmeticoRepository.findByUsuarioIdAndAplicadoTrue(usuarioId);
        for (UsuarioCosmetico uc : aplicados) {
            if (uc.getProducto().getTipo() != null && uc.getProducto().getTipo().equals(tipo)) {
                uc.setAplicado(false);
                usuarioCosmeticoRepository.save(uc);
            }
        }
    }
}
