package com.duoc.ms_auditoria.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "auditorias")
public class Auditoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String usuarioRut;

    @Column(nullable = false)
    private String accion; // COMPRA, LOGIN, MODIFICACION

    @Column(nullable = false)
    private String detalle;

    @Column(nullable = false)
    private String modulo; // ms-pedidos, ms-pagos, etc.

    @Column(nullable = false)
    private LocalDateTime fecha;
}
