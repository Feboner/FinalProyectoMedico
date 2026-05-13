package com.example.ms_autor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_autor.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long>{

}
