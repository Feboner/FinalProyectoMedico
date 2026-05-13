package com.duoc.ms_pedidos.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numeroPedido;

    @Column(nullable = false)
    private String pacienteRut;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private String estado;
}
