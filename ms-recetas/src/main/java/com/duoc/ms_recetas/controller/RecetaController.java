package com.duoc.ms_recetas.controller;

import com.duoc.ms_recetas.dto.ApiResponse;
import com.duoc.ms_recetas.dto.RecetaDTO;
import com.duoc.ms_recetas.model.Receta;
import com.duoc.ms_recetas.service.RecetaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
@RequiredArgsConstructor
@Slf4j
public class RecetaController {

    private final RecetaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Receta>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Receta>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Receta>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<Receta>builder()
                        .success(true)
                        .message("Registro obtenido")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Receta>> crear(@Valid @RequestBody RecetaDTO dto) {
        return ResponseEntity.status(201).body(
                ApiResponse.<Receta>builder()
                        .success(true)
                        .message("Registro creado")
                        .data(service.crear(dto))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Receta>> actualizar(@PathVariable Long id,
                                                                  @Valid @RequestBody RecetaDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.<Receta>builder()
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
