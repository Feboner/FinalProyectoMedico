package com.duoc.ms_pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PacienteResumenDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String rut;
    private String email;
    private String telefono;
}