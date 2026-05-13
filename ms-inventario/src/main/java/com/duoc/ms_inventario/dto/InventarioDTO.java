package com.duoc.ms_inventario.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class InventarioDTO {
    @NotBlank(message = "El codigo del producto es obligatorio")
    private String codigoProducto;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El stock actual es obligatorio")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stockActual;

    @NotNull(message = "El stock minimo es obligatorio")
    @PositiveOrZero(message = "El stock minimo no puede ser negativo")
    private Integer stockMinimo;

    @NotBlank(message = "La ubicacion en bodega es obligatoria")
    private String ubicacionBodega;
}
