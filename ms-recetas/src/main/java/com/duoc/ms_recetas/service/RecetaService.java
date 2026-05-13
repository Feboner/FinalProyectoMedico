package com.duoc.ms_recetas.service;

import com.duoc.ms_recetas.dto.RecetaDTO;
import com.duoc.ms_recetas.model.Receta;
import com.duoc.ms_recetas.repository.RecetaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class RecetaService {
    private final RecetaRepository repo;

    public List<Receta> listar() {
        log.info("Listar recetas");
        return repo.findAll();
    }

    public Receta obtener(Long id) {
        log.info("Obtener receta id: {}", id);
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada"));
    }

    public Receta crear(RecetaDTO dto) {
        log.info("Crear receta folio: {}", dto.getFolio());
        Receta r = new Receta();
        r.setFolio(dto.getFolio());
        r.setPacienteRut(dto.getPacienteRut());
        r.setMedicamento(dto.getMedicamento());
        r.setDosis(dto.getDosis());
        r.setFechaEmision(dto.getFechaEmision());
        r.setEstado(dto.getEstado());
        return repo.save(r);
    }

    public Receta actualizar(Long id, RecetaDTO dto) {
        log.info("Actualizar receta id: {}", id);
        Receta r = obtener(id);
        r.setMedicamento(dto.getMedicamento());
        r.setDosis(dto.getDosis());
        r.setEstado(dto.getEstado());
        return repo.save(r);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar receta id: {}", id);
        repo.delete(obtener(id));
    }
}
