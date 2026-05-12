package com.duoc.msproductos.controller;

import com.duoc.msproductos.model.Producto;
import com.duoc.msproductos.service.ProductoService;
import jakarta.validation.Valid; // Para activar las validaciones JSR 380 [cite: 65, 66]
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.obtenerTodos()); [cite: 83]
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.guardar(producto)); [cite: 88, 90]
    }
}