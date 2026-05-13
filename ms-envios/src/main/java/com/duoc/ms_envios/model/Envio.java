package com.duoc.ms_envios.model;

import jakarta.persistence.*;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "envios")
public class Envio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigoSeguimiento;

    @Column(nullable = false)
    private String pacienteRut;

    @Column(nullable = false)
    private String direccionDestino;

    @Column(nullable = false)
    private String estado; // PREPARANDO, EN_CAMINO, ENTREGADO

    @Column(nullable = false)
    private String transportista;
}
