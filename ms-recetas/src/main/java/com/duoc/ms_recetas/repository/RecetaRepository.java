package com.duoc.ms_recetas.repository;

import com.duoc.ms_recetas.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
}
