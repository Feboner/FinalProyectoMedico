package com.duoc.ms_pedidos.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidoDTO {
    @NotBlank(message = "El numero de pedido es obligatorio")
    private String numeroPedido;

    @NotBlank(message = "El RUT del paciente es obligatorio")
    private String pacienteRut;

    @NotNull(message = "El total es obligatorio")
    private BigDecimal total;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}
