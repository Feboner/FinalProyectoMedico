package com.duoc.msauth.controller;

import com.duoc.msauth.dto.ApiResponse;
import com.duoc.msauth.dto.LoginRequest;
import com.duoc.msauth.model.Usuario;
import com.duoc.msauth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Usuario>> registrar(@Valid @RequestBody Usuario usuario) {
        log.info("Solicitud de registro: {}", usuario.getUsername());

        Usuario nuevo = service.registrar(usuario);

        return ResponseEntity.status(201).body(
                ApiResponse.<Usuario>builder()
                        .success(true)
                        .message("Usuario registrado")
                        .data(nuevo)
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Solicitud de login: {}", loginRequest.getUsername());

        String token = service.login(loginRequest.getUsername(), loginRequest.getPassword());

        if (token != null) {
            return ResponseEntity.ok(
                    ApiResponse.<String>builder()
                            .success(true)
                            .message("Login exitoso")
                            .data(token)
                            .build()
            );
        }

        return ResponseEntity.status(401).body(
                ApiResponse.<String>builder()
                        .success(false)
                        .message("Credenciales invalidas")
                        .build()
        );
    }
}
