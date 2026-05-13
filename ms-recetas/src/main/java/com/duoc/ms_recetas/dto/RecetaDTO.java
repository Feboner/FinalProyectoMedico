package com.duoc.ms_recetas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class RecetaDTO {
    @NotBlank(message = "El folio es obligatorio")
    private String folio;

    @NotBlank(message = "El RUT del paciente es obligatorio")
    private String pacienteRut;

    @NotBlank(message = "El medicamento es obligatorio")
    private String medicamento;

    @NotBlank(message = "La dosis es obligatoria")
    private String dosis;

    @NotNull(message = "La fecha de emision es obligatoria")
    private LocalDate fechaEmision;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}
