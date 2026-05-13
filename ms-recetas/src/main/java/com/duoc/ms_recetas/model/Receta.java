package com.duoc.ms_recetas.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "recetas")
public class Receta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String folio;

    @Column(nullable = false)
    private String pacienteRut;

    @Column(nullable = false)
    private String medicamento;

    @Column(nullable = false)
    private String dosis;

    @Column(nullable = false)
    private LocalDate fechaEmision;

    @Column(nullable = false)
    private String estado; // VIGENTE, USADA, VENCIDA
}
