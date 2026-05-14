package com.duoc.ms_productos.service;

import com.duoc.ms_productos.dto.ProductoDTO;
import com.duoc.ms_productos.model.Producto;
import com.duoc.ms_productos.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoService {

    private final ProductoRepository repo;

    public List<Producto> listar() {
        log.info("Listar productos");
        return repo.findAll();
    }

    public Producto obtener(Long id) {
        log.info("Obtener producto id: {}", id);
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }

    public Producto crear(ProductoDTO dto) {
        log.info("Crear producto: {}", dto.getNombre());

        Producto p = new Producto();
        p.setNombre(dto.getNombre());
        p.setPrecio(dto.getPrecio());
        p.setStock(dto.getStock());

        return repo.save(p);
    }

    public Producto actualizar(Long id, ProductoDTO dto) {
        log.info("Actualizar producto id: {}", id);

        Producto p = obtener(id);
        p.setNombre(dto.getNombre());
        p.setPrecio(dto.getPrecio());
        p.setStock(dto.getStock());

        return repo.save(p);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar producto id: {}", id);
        repo.deleteById(id);
    }
}
