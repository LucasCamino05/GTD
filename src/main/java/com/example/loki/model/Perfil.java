package com.example.loki.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Perfil {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_seq_gen")
    @SequenceGenerator(
            name = "perfil_seq_gen",
            sequenceName = "perfil_seq_gen",
            allocationSize = 1
    )
    private Long id;

    private String nombre;
    private String apellido;
    private String correo;
    private LocalDate fecha_alta;

    public Perfil(String nombre, String apellido, String correo, LocalDate fecha_alta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fecha_alta = fecha_alta;
    }
}
