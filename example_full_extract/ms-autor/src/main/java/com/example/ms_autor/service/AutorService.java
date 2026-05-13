package com.example.ms_autor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_autor.dto.AutorDTO;
import com.example.ms_autor.model.Autor;
import com.example.ms_autor.repository.AutorRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutorService {

    private final AutorRepository repo;

    public Autor crear(AutorDTO dto) {
        log.info("Crear autor", keyValue("nombre", dto.getNombre()));

        Autor a = new Autor(null, dto.getNombre(), dto.getAnio());
        return repo.save(a);
    }

    public List<Autor> listar() {
        log.info("Listar autores");
        return repo.findAll();
    }

    public Autor obtener(Long id) {
        log.info("Obtener autor", keyValue("id", id));

        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor no encontrado"));
    }

    public Autor actualizar(Long id, AutorDTO dto) {
        log.info("Actualizar autor", keyValue("id", id));

        Autor a = obtener(id);
        a.setNombre(dto.getNombre());
        a.setAnio(dto.getAnio());

        return repo.save(a);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar autor", keyValue("id", id));
        repo.deleteById(id);
    }
}