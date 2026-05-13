package com.duoc.ms_inventario.controller;

import com.duoc.ms_inventario.dto.ApiResponse;
import com.duoc.ms_inventario.dto.InventarioDTO;
import com.duoc.ms_inventario.model.InventarioItem;
import com.duoc.ms_inventario.service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
@Slf4j
public class InventarioController {

    private final InventarioService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<InventarioItem>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<InventarioItem>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InventarioItem>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<InventarioItem>builder()
                        .success(true)
                        .message("Registro obtenido")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InventarioItem>> crear(@Valid @RequestBody InventarioDTO dto) {
        return ResponseEntity.status(201).body(
                ApiResponse.<InventarioItem>builder()
                        .success(true)
                        .message("Registro creado")
                        .data(service.crear(dto))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InventarioItem>> actualizar(@PathVariable Long id,
                                                                  @Valid @RequestBody InventarioDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.<InventarioItem>builder()
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
