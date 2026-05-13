package com.duoc.ms_pacientes.repository;

import com.duoc.ms_pacientes.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    List<Direccion> findByPacienteId(Long pacienteId);
}
