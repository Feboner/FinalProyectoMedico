package com.duoc.ms_productos.controller;

import com.duoc.ms_productos.dto.ApiResponse;
import com.duoc.ms_productos.dto.ProductoDTO;
import com.duoc.ms_productos.model.Producto;
import com.duoc.ms_productos.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Slf4j
public class ProductoController {

    private final ProductoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Producto>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Producto>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Producto>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<Producto>builder()
                        .success(true)
                        .message("Producto obtenido")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Producto>> crear(@Valid @RequestBody ProductoDTO dto) {
        Producto p = service.crear(dto);
        return ResponseEntity.status(201).body(
                ApiResponse.<Producto>builder()
                        .success(true)
                        .message("Producto creado")
                        .data(p)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Producto>> actualizar(@PathVariable Long id,
                                                            @Valid @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.<Producto>builder()
                        .success(true)
                        .message("Producto actualizado")
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
                        .message("Producto eliminado")
                        .build()
        );
    }
}
