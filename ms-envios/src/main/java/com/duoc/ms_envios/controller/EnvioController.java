package com.duoc.ms_envios.controller;

import com.duoc.ms_envios.dto.ApiResponse;
import com.duoc.ms_envios.dto.EnvioDTO;
import com.duoc.ms_envios.model.Envio;
import com.duoc.ms_envios.service.EnvioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/envios")
@RequiredArgsConstructor
@Slf4j
public class EnvioController {

    private final EnvioService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Envio>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Envio>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Envio>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<Envio>builder()
                        .success(true)
                        .message("Registro obtenido")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Envio>> crear(@Valid @RequestBody EnvioDTO dto) {
        return ResponseEntity.status(201).body(
                ApiResponse.<Envio>builder()
                        .success(true)
                        .message("Registro creado")
                        .data(service.crear(dto))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Envio>> actualizar(@PathVariable Long id,
                                                                  @Valid @RequestBody EnvioDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.<Envio>builder()
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
