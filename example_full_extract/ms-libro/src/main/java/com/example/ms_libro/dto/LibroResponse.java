package com.example.ms_libro.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibroResponse {

    private Long id;
    private String titulo;
    private Integer anio;
    private AutorResponse autor;
}