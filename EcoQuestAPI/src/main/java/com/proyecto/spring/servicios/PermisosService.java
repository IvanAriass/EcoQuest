package com.proyecto.spring.servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.spring.modelos.Rol;
import com.proyecto.spring.modelos.RolPermiso;
import com.proyecto.spring.modelos.UsuarioComunidad;
import com.proyecto.spring.repository.RolPermisoRepository;
import com.proyecto.spring.repository.RolRepository;
import com.proyecto.spring.repository.UsuarioComunidadRepository;

@Service
public class PermisosService {

    @Autowired
    private UsuarioComunidadRepository usuarioComunidadRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private RolPermisoRepository rolPermisoRepository;

    public List<Rol> obtenerTodosLosRoles() {
        return rolRepository.findAll();
    }

    public Optional<Rol> obtenerRolPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre != null ? nombre.toUpperCase() : "");
    }

    public List<Map<String, Object>> obtenerDefinicionesRoles() {
        return rolRepository.findAll().stream().map(rol -> {
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("id", rol.getNombre());
            mapa.put("nombre", rol.getNombre());
            mapa.put("emoji", rol.getEmoji());
            mapa.put("nivel", rol.getNivel());
            mapa.put("descripcion", rol.getDescripcion());
            mapa.put("permisos", rol.getPermisos().stream()
                    .map(RolPermiso::getPermiso)
                    .collect(Collectors.toList()));
            return mapa;
        }).collect(Collectors.toList());
    }

    public String rolPorDefecto() {
        return rolRepository.findByNombre("SEMILLA")
                .map(Rol::getNombre)
                .orElse("SEMILLA");
    }

    public boolean tienePermiso(Long usuarioId, Long comunidadId, String permiso) {
        Optional<UsuarioComunidad> relacion = usuarioComunidadRepository
                .findByUsuarioIdAndComunidadId(usuarioId, comunidadId);
        if (relacion.isEmpty()) return false;

        String rol = relacion.get().getRol();
        if (rol == null || rol.isBlank()) return false;

        return rolRepository.tienePermiso(rol.toUpperCase(), permiso);
    }

    public boolean cambiarRol(Long usuarioId, Long comunidadId, String nuevoRol,
                               Long solicitanteId) {
        if (!tienePermiso(solicitanteId, comunidadId, "GESTIONAR_MIEMBROS")) {
            return false;
        }
        if (!rolRepository.existsByNombre(nuevoRol.toUpperCase())) {
            return false;
        }
        return usuarioComunidadRepository
                .findByUsuarioIdAndComunidadId(usuarioId, comunidadId)
                .map(relacion -> {
                    relacion.setRol(nuevoRol.toUpperCase());
                    usuarioComunidadRepository.save(relacion);
                    return true;
                }).orElse(false);
    }

    public String obtenerRolUsuario(Long usuarioId, Long comunidadId) {
        return usuarioComunidadRepository
                .findByUsuarioIdAndComunidadId(usuarioId, comunidadId)
                .map(UsuarioComunidad::getRol)
                .orElse(null);
    }

    public Rol crearRol(String nombre, int nivel, String emoji, String descripcion,
                         List<String> permisos) {
        Rol rol = new Rol(nombre.toUpperCase(), nivel, emoji, descripcion);
        rol.setPermisos(new ArrayList<>());
        for (String permiso : permisos) {
            rol.getPermisos().add(new RolPermiso(rol, permiso));
        }
        return rolRepository.save(rol);
    }

    public Optional<Rol> actualizarRol(Long id, String nombre, Integer nivel,
                                         String emoji, String descripcion) {
        return rolRepository.findById(id).map(rol -> {
            if (nombre != null) rol.setNombre(nombre.toUpperCase());
            if (nivel != null) rol.setNivel(nivel);
            if (emoji != null) rol.setEmoji(emoji);
            if (descripcion != null) rol.setDescripcion(descripcion);
            return rolRepository.save(rol);
        });
    }

    public Optional<Rol> reemplazarPermisos(Long rolId, List<String> nuevosPermisos) {
        return rolRepository.findById(rolId).map(rol -> {
            rol.getPermisos().clear();
            for (String permiso : nuevosPermisos) {
                rol.getPermisos().add(new RolPermiso(rol, permiso));
            }
            return rolRepository.save(rol);
        });
    }

    public boolean eliminarRol(Long id) {
        if (rolRepository.existsById(id)) {
            rolRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
