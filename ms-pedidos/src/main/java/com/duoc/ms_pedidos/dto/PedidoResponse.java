package com.duoc.ms_pedidos.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoResponse {
    private Long id;
    private String numeroPedido;
    private BigDecimal total;
    private String estado;
    private PacienteResumenDTO paciente;
}