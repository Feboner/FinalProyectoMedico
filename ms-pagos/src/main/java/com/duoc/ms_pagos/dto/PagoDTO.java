package com.duoc.ms_pagos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PagoDTO {
    @NotBlank(message = "El numero de pago es obligatorio")
    private String numeroPago;

    @NotBlank(message = "El RUT del paciente es obligatorio")
    private String pacienteRut;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotBlank(message = "El metodo de pago es obligatorio")
    private String metodoPago;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}
