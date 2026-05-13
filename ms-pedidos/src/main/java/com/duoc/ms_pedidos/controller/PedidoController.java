package com.duoc.ms_pedidos.controller;

import com.duoc.ms_pedidos.dto.ApiResponse;
import com.duoc.ms_pedidos.dto.PedidoDTO;
import com.duoc.ms_pedidos.dto.PedidoResponse;
import com.duoc.ms_pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Slf4j
public class PedidoController {

    private final PedidoService service;

    @GetMapping
        public ResponseEntity<ApiResponse<List<PedidoResponse>>> listar() {
        return ResponseEntity.ok(
            ApiResponse.<List<PedidoResponse>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<PedidoResponse>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
            ApiResponse.<PedidoResponse>builder()
                        .success(true)
                        .message("Registro obtenido")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PostMapping
        public ResponseEntity<ApiResponse<PedidoResponse>> crear(@Valid @RequestBody PedidoDTO dto) {
        return ResponseEntity.status(201).body(
            ApiResponse.<PedidoResponse>builder()
                        .success(true)
                        .message("Registro creado")
                        .data(service.crear(dto))
                        .build()
        );
    }

    @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<PedidoResponse>> actualizar(@PathVariable Long id,
                                      @Valid @RequestBody PedidoDTO dto) {
        return ResponseEntity.ok(
            ApiResponse.<PedidoResponse>builder()
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
