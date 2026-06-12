package com.proyecto.spring.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.spring.modelos.CanjeProducto;
import com.proyecto.spring.modelos.Producto;
import com.proyecto.spring.modelos.ProgresoReto;
import com.proyecto.spring.modelos.Reto;
import com.proyecto.spring.modelos.TransaccionPuntos;
import com.proyecto.spring.modelos.Usuario;
import com.proyecto.spring.repository.CanjeProductoRepository;
import com.proyecto.spring.repository.ProductoRepository;
import com.proyecto.spring.repository.ProgresoRetoRepository;
import com.proyecto.spring.repository.RetoRepository;
import com.proyecto.spring.repository.TransaccionPuntosRepository;
import com.proyecto.spring.repository.UsuarioRepository;

@Service
public class PuntosService {

    @Autowired
    private RetoRepository retoRepository;

    @Autowired
    private ProgresoRetoRepository progresoRetoRepository;

    @Autowired
    private TransaccionPuntosRepository transaccionPuntosRepository;

    @Autowired
    private CanjeProductoRepository canjeProductoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // --- Retos ---

    public List<Reto> obtenerRetos() {
        return retoRepository.findAll();
    }

    public Optional<Reto> obtenerRetoPorId(Long id) {
        return retoRepository.findById(id);
    }

    // --- Progreso ---

    public List<ProgresoReto> obtenerProgresoUsuario(Long usuarioId) {
        return progresoRetoRepository.findByUsuarioId(usuarioId);
    }

    public Optional<ProgresoReto> obtenerProgresoReto(Long usuarioId, Long retoId) {
        return progresoRetoRepository.findByUsuarioIdAndRetoId(usuarioId, retoId);
    }

    // --- Transacciones ---

    public List<TransaccionPuntos> obtenerHistorial(Long usuarioId) {
        return transaccionPuntosRepository.findByUsuarioIdOrderByFechaDesc(usuarioId);
    }

    public int calcularSaldo(Long usuarioId) {
        int ganados = transaccionPuntosRepository.totalGanadosByUsuarioId(usuarioId);
        int canjeados = transaccionPuntosRepository.totalCanjeadosByUsuarioId(usuarioId);
        return ganados - canjeados;
    }

    // --- Canjes ---

    public List<CanjeProducto> obtenerCanjes(Long usuarioId) {
        return canjeProductoRepository.findByUsuarioIdOrderByFechaDesc(usuarioId);
    }

    // --- Logica de otorgar puntos ---

    @Transactional
    public TransaccionPuntos otorgarPuntos(Usuario usuario, int puntos, String concepto, String tipo, Long referenciaId) {
        TransaccionPuntos transaccion = new TransaccionPuntos(usuario, puntos, tipo, concepto, referenciaId);
        usuario.setPuntos(calcularSaldo(usuario.getId()) + puntos);
        usuarioRepository.save(usuario);
        return transaccionPuntosRepository.save(transaccion);
    }

    @Transactional
    public boolean verificarYOtorgarReto(Usuario usuario, String tipoReto, Long referenciaId) {
        List<Reto> retos = retoRepository.findByTipo(tipoReto);
        if (retos.isEmpty()) return false;

        boolean otorgado = false;

        for (Reto reto : retos) {
            if ("DIARIA".equals(reto.getFrecuencia())) {
                boolean yaCompletadoHoy = transaccionPuntosRepository
                        .existsByUsuarioIdAndTipoAndReferenciaId(usuario.getId(), "RETO_" + reto.getTipo(), referenciaId);

                if (!yaCompletadoHoy) {
                    String concepto = reto.getNombre();
                    otorgarPuntos(usuario, reto.getPuntos(), concepto, "GANADO", referenciaId);
                    otorgado = true;
                }
            } else {
                boolean yaCanjeado = transaccionPuntosRepository
                        .existsByUsuarioIdAndTipoAndReferenciaId(usuario.getId(), "RETO_" + reto.getTipo(), referenciaId);

                if (!yaCanjeado) {
                    String concepto = reto.getNombre();
                    otorgarPuntos(usuario, reto.getPuntos(), concepto, "GANADO", referenciaId);
                    otorgado = true;
                }
            }
        }

        return otorgado;
    }

    @Transactional
    public boolean verificarYOtorgarRetoLogin(Usuario usuario) {
        String tipoReto = "LOGIN";
        List<Reto> retos = retoRepository.findByTipo(tipoReto);
        if (retos.isEmpty()) return false;

        boolean otorgado = false;

        for (Reto reto : retos) {
            LocalDate hoy = LocalDate.now();
            String fechaKey = hoy.toString();

            boolean yaRecibidoHoy = transaccionPuntosRepository
                    .findByUsuarioIdAndFechaAfterOrderByFechaDesc(usuario.getId(), hoy.atStartOfDay())
                    .stream()
                    .anyMatch(t -> t.getConcepto() != null && t.getConcepto().contains(reto.getNombre()));

            if (!yaRecibidoHoy) {
                otorgarPuntos(usuario, reto.getPuntos(), reto.getNombre() + " (" + fechaKey + ")", "GANADO", null);
                otorgado = true;
            }
        }

        return otorgado;
    }

    @Transactional
    public boolean canjearProducto(Long usuarioId, Long productoId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<Producto> productoOpt = productoRepository.findById(productoId);

        if (usuarioOpt.isEmpty() || productoOpt.isEmpty()) return false;

        Usuario usuario = usuarioOpt.get();
        Producto producto = productoOpt.get();
        int saldo = calcularSaldo(usuarioId);

        if (saldo < producto.getPrecio()) return false;

        otorgarPuntos(usuario, -producto.getPrecio(), "Canje: " + producto.getNombre(), "CANJEADO", productoId);

        CanjeProducto canje = new CanjeProducto(usuario, producto, producto.getPrecio());
        canjeProductoRepository.save(canje);

        return true;
    }
}
