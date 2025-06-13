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
    @Id
    @GeneratedValue
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
