package com.duoc.ms_proveedores.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProveedorDTO {
    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El contacto es obligatorio")
    private String contacto;

    @Email(message = "Email invalido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "El tipo de producto es obligatorio")
    private String tipoProducto;
}
