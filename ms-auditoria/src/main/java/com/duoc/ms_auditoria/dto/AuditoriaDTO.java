package com.duoc.ms_auditoria.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuditoriaDTO {
    @NotBlank(message = "El RUT de usuario es obligatorio")
    private String usuarioRut;

    @NotBlank(message = "La accion es obligatoria")
    private String accion;

    @NotBlank(message = "El detalle es obligatorio")
    private String detalle;

    @NotBlank(message = "El modulo es obligatorio")
    private String modulo;
}
