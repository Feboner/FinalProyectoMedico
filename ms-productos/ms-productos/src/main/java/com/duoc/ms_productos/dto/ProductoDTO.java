package com.duoc.msproductos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductoDTO {
    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @Min(value = 0, message = "El precio debe ser positivo")
    private Double precio;

    @NotNull(message = "Indique si requiere receta")
    private Boolean requiereReceta;

    private String compuestoActivo;
}