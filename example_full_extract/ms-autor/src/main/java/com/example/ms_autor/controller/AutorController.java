package com.example.ms_autor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ms_autor.dto.ApiResponse;
import com.example.ms_autor.dto.AutorDTO;
import com.example.ms_autor.model.Autor;
import com.example.ms_autor.service.AutorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Autor>> crear(@Valid @RequestBody AutorDTO dto) {

        Autor autor = service.crear(dto);

        return ResponseEntity.status(201).body(
                ApiResponse.<Autor>builder()
                        .success(true)
                        .message("Autor creado")
                        .data(autor)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Autor>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Autor>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Autor>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Autor>builder()
                        .success(true)
                        .message("Autor obtenido")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Autor>> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody AutorDTO dto) {

        Autor autor = service.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Autor>builder()
                        .success(true)
                        .message("Autor actualizado")
                        .data(autor)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Autor eliminado")
                        .build()
        );
    }
}