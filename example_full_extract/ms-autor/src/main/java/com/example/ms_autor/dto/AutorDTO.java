package com.example.ms_autor.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AutorDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 0, message = "El año debe ser positivo")
    private Integer anio;
}