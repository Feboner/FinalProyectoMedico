package com.example.ms_libro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_libro.model.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long>{

}
