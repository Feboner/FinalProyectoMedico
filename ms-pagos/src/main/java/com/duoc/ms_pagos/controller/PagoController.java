package com.duoc.ms_pagos.controller;

import com.duoc.ms_pagos.dto.ApiResponse;
import com.duoc.ms_pagos.dto.PagoDTO;
import com.duoc.ms_pagos.model.Pago;
import com.duoc.ms_pagos.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Slf4j
public class PagoController {

    private final PagoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Pago>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Pago>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Pago>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<Pago>builder()
                        .success(true)
                        .message("Registro obtenido")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Pago>> crear(@Valid @RequestBody PagoDTO dto) {
        return ResponseEntity.status(201).body(
                ApiResponse.<Pago>builder()
                        .success(true)
                        .message("Registro creado")
                        .data(service.crear(dto))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Pago>> actualizar(@PathVariable Long id,
                                                                  @Valid @RequestBody PagoDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.<Pago>builder()
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
