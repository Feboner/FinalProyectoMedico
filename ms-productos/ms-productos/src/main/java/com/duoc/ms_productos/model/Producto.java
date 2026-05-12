package com.duoc.msproductos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*; // JSR 380 
import lombok.Data;

@Entity
@Table(name = "productos")
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; [cite: 56, 63]

    @NotBlank(message = "El nombre del producto es obligatorio") [cite: 66, 161]
    private String nombre;

    @Min(value = 0, message = "El precio no puede ser negativo") [cite: 66, 161]
    private Double precio;

    @NotNull(message = "Debe indicar si requiere receta") [cite: 66, 161]
    private Boolean requiereReceta;

    @NotBlank(message = "El compuesto activo es obligatorio")
    private String compuestoActivo;
}