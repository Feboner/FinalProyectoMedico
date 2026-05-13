package com.example.ms_libro.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LibroDTO {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotNull(message = "El año es obligatorio")
    private Integer anio;

    @NotNull(message = "El autorId es obligatorio")
    private Long autorId;
}