package com.example.loki.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "notificaciones")
@Data
public class Notificacion {
    @Id
    @SequenceGenerator(
            name = "notificacion_seq_gen",
            sequenceName = "notificacion_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificacion_seq_gen")

    private Long id;

    private String mensaje;
    private Boolean leido = false;
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;
}
