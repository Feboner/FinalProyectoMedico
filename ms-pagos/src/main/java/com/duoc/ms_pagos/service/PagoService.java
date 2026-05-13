package com.duoc.ms_pagos.service;

import com.duoc.ms_pagos.dto.PagoDTO;
import com.duoc.ms_pagos.model.Pago;
import com.duoc.ms_pagos.repository.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class PagoService {
    private final PagoRepository repo;

    public List<Pago> listar() {
        log.info("Listar pagos");
        return repo.findAll();
    }

    public Pago obtener(Long id) {
        log.info("Obtener pago id: {}", id);
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado"));
    }

    public Pago crear(PagoDTO dto) {
        log.info("Crear pago: {}", dto.getNumeroPago());
        Pago p = new Pago();
        p.setNumeroPago(dto.getNumeroPago());
        p.setPacienteRut(dto.getPacienteRut());
        p.setMonto(dto.getMonto());
        p.setMetodoPago(dto.getMetodoPago());
        p.setEstado(dto.getEstado());
        p.setFechaPago(LocalDateTime.now());
        return repo.save(p);
    }

    public Pago actualizar(Long id, PagoDTO dto) {
        log.info("Actualizar pago id: {}", id);
        Pago p = obtener(id);
        p.setEstado(dto.getEstado());
        p.setMetodoPago(dto.getMetodoPago());
        return repo.save(p);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar pago id: {}", id);
        repo.delete(obtener(id));
    }
}
