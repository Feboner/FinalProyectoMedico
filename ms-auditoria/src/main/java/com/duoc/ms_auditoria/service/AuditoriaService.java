package com.duoc.ms_auditoria.service;

import com.duoc.ms_auditoria.dto.AuditoriaDTO;
import com.duoc.ms_auditoria.model.Auditoria;
import com.duoc.ms_auditoria.repository.AuditoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class AuditoriaService {
    private final AuditoriaRepository repo;

    public List<Auditoria> listar() {
        log.info("Listar auditorias");
        return repo.findAll();
    }

    public Auditoria obtener(Long id) {
        log.info("Obtener auditoria id: {}", id);
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Auditoria no encontrada"));
    }

    public Auditoria crear(AuditoriaDTO dto) {
        log.info("Registrar auditoria accion: {} modulo: {}", dto.getAccion(), dto.getModulo());
        Auditoria a = new Auditoria();
        a.setUsuarioRut(dto.getUsuarioRut());
        a.setAccion(dto.getAccion());
        a.setDetalle(dto.getDetalle());
        a.setModulo(dto.getModulo());
        a.setFecha(LocalDateTime.now());
        return repo.save(a);
    }

    public Auditoria actualizar(Long id, AuditoriaDTO dto) {
        log.info("Actualizar auditoria id: {}", id);
        Auditoria a = obtener(id);
        a.setDetalle(dto.getDetalle());
        return repo.save(a);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar auditoria id: {}", id);
        repo.delete(obtener(id));
    }
}
