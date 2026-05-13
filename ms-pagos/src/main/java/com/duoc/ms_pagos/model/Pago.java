package com.duoc.ms_pagos.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "pagos")
public class Pago {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroPago;

    @Column(nullable = false)
    private String pacienteRut;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(nullable = false)
    private String metodoPago; // TARJETA, EFECTIVO, TRANSFERENCIA

    @Column(nullable = false)
    private String estado; // PENDIENTE, APROBADO, RECHAZADO

    @Column(nullable = false)
    private LocalDateTime fechaPago;
}
