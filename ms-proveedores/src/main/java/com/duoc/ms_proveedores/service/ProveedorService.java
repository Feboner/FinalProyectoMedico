package com.duoc.ms_proveedores.service;

import com.duoc.ms_proveedores.dto.ProveedorDTO;
import com.duoc.ms_proveedores.model.Proveedor;
import com.duoc.ms_proveedores.repository.ProveedorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class ProveedorService {
    private final ProveedorRepository repo;

    public List<Proveedor> listar() {
        log.info("Listar proveedores");
        return repo.findAll();
    }

    public Proveedor obtener(Long id) {
        log.info("Obtener proveedor id: {}", id);
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
    }

    public Proveedor crear(ProveedorDTO dto) {
        log.info("Crear proveedor rut: {}", dto.getRut());
        if (repo.findByRut(dto.getRut()).isPresent()) {
            throw new RuntimeException("Ya existe un proveedor con ese RUT");
        }
        Proveedor p = new Proveedor();
        p.setRut(dto.getRut());
        p.setNombre(dto.getNombre());
        p.setContacto(dto.getContacto());
        p.setEmail(dto.getEmail());
        p.setTipoProducto(dto.getTipoProducto());
        return repo.save(p);
    }

    public Proveedor actualizar(Long id, ProveedorDTO dto) {
        log.info("Actualizar proveedor id: {}", id);
        Proveedor p = obtener(id);
        p.setNombre(dto.getNombre());
        p.setContacto(dto.getContacto());
        p.setEmail(dto.getEmail());
        p.setTipoProducto(dto.getTipoProducto());
        return repo.save(p);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar proveedor id: {}", id);
        repo.delete(obtener(id));
    }
}
