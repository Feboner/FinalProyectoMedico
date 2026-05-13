package com.duoc.msauth.service;

import com.duoc.msauth.model.Usuario;
import com.duoc.msauth.repository.UsuarioRepository;
import com.duoc.msauth.security.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Usuario registrar(Usuario usuario) {
        log.info("Registrando usuario: {}", usuario.getUsername());

        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            log.warn("Usuario ya existe: {}", usuario.getUsername());
            throw new RuntimeException("El usuario ya existe");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario guardado = usuarioRepository.save(usuario);

        log.info("Usuario creado con id: {}", guardado.getId());
        return guardado;
    }

    public String login(String username, String password) {
        log.info("Intento de login: {}", username);

        Optional<Usuario> user = usuarioRepository.findByUsername(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            log.info("Login exitoso: {}", username);
            return jwtUtil.generateToken(username);
        }

        log.warn("Login fallido: {}", username);
        return null;
    }
}
