package com.duoc.ms_envios.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EnvioDTO {
    @NotBlank(message = "El codigo de seguimiento es obligatorio")
    private String codigoSeguimiento;

    @NotBlank(message = "El RUT del paciente es obligatorio")
    private String pacienteRut;

    @NotBlank(message = "La direccion de destino es obligatoria")
    private String direccionDestino;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotBlank(message = "El transportista es obligatorio")
    private String transportista;
}
