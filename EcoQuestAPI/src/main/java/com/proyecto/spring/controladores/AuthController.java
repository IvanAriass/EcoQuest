package com.proyecto.spring.controladores;

import com.proyecto.spring.dto.UsuarioDTO;
import com.proyecto.spring.dto.auth.AuthResponse;
import com.proyecto.spring.dto.auth.LoginRequest;
import com.proyecto.spring.dto.auth.RegisterRequest;
import com.proyecto.spring.modelos.Usuario;
import com.proyecto.spring.repository.UsuarioRepository;
import com.proyecto.spring.seguridad.JwtUtil;
import com.proyecto.spring.servicios.PuntosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PuntosService puntosService;

    @Value("${app.base.url}")
    private String baseUrl;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        var usuarioOpt = usuarioRepository.findByEmail(request.email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(request.password, usuario.getContraseña())) {
            return ResponseEntity.status(401).build();
        }

        if (usuario.isBloqueado()) {
            return ResponseEntity.status(403).build();
        }

        if (usuario.getImagen() != null && !usuario.getImagen().isBlank() && !usuario.getImagen().startsWith("http")) {
            usuario.setImagen(baseUrl + "/api/usuarios/imagen/" + usuario.getImagen());
        }

        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getId());
        puntosService.verificarYOtorgarRetoLogin(usuario);
        return ResponseEntity.ok(new AuthResponse(token, new UsuarioDTO(usuario)));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (usuarioRepository.findByEmail(request.email).isPresent()) {
            return ResponseEntity.status(409).build();
        }

        if (usuarioRepository.findByNombreUsuario(request.nombreUsuario).isPresent()) {
            return ResponseEntity.status(409).build();
        }

        Usuario usuario = new Usuario(
                request.nombreUsuario,
                request.password,
                request.nombre != null ? request.nombre : request.nombreUsuario,
                "", "", null, request.email, ""
        );
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        usuario = usuarioRepository.save(usuario);

        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getId());
        return ResponseEntity.ok(new AuthResponse(token, new UsuarioDTO(usuario)));
    }
}
