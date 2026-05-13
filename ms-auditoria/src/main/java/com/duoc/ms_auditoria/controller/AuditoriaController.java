package com.duoc.ms_auditoria.controller;

import com.duoc.ms_auditoria.dto.ApiResponse;
import com.duoc.ms_auditoria.dto.AuditoriaDTO;
import com.duoc.ms_auditoria.model.Auditoria;
import com.duoc.ms_auditoria.service.AuditoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
@RequiredArgsConstructor
@Slf4j
public class AuditoriaController {

    private final AuditoriaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Auditoria>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Auditoria>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Auditoria>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<Auditoria>builder()
                        .success(true)
                        .message("Registro obtenido")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Auditoria>> crear(@Valid @RequestBody AuditoriaDTO dto) {
        return ResponseEntity.status(201).body(
                ApiResponse.<Auditoria>builder()
                        .success(true)
                        .message("Registro creado")
                        .data(service.crear(dto))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Auditoria>> actualizar(@PathVariable Long id,
                                                                  @Valid @RequestBody AuditoriaDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.<Auditoria>builder()
                        .success(true)
                        .message("Registro actualizado")
                        .data(service.actualizar(id, dto))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Registro eliminado")
                        .build()
        );
    }
}
