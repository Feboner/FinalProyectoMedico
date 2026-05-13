package com.duoc.ms_proveedores.controller;

import com.duoc.ms_proveedores.dto.ApiResponse;
import com.duoc.ms_proveedores.dto.ProveedorDTO;
import com.duoc.ms_proveedores.model.Proveedor;
import com.duoc.ms_proveedores.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
@Slf4j
public class ProveedorController {

    private final ProveedorService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Proveedor>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Proveedor>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Proveedor>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<Proveedor>builder()
                        .success(true)
                        .message("Registro obtenido")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Proveedor>> crear(@Valid @RequestBody ProveedorDTO dto) {
        return ResponseEntity.status(201).body(
                ApiResponse.<Proveedor>builder()
                        .success(true)
                        .message("Registro creado")
                        .data(service.crear(dto))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Proveedor>> actualizar(@PathVariable Long id,
                                                                  @Valid @RequestBody ProveedorDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.<Proveedor>builder()
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
