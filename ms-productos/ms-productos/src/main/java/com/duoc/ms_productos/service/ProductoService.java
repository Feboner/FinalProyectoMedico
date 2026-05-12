package com.duoc.msproductos.service;

import com.duoc.msproductos.model.Producto;
import com.duoc.msproductos.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
   
    private static final Logger log = LoggerFactory.getLogger(ProductoService.class);

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodos() {
    
        log.info("Iniciando consulta de todos los productos médicos en la capa de servicio"); [cite: 92]
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        log.info("Buscando producto médico con ID: {}", id); [cite: 92]
        return productoRepository.findById(id);
    }

    public Producto guardar(Producto producto) {
        try {
            
            log.info("Intentando guardar nuevo producto médico: {}", producto.getNombre()); [cite: 93]
            return productoRepository.save(producto);
        } catch (Exception e) {
            
            log.error("Error al persistir el producto en la base de datos: {}", e.getMessage()); [cite: 93]
            throw e;
        }
    }

    public void eliminar(Long id) {

        log.warn("Eliminando registro de producto con ID: {}", id); [cite: 93]
        productoRepository.deleteById(id);
    }
}