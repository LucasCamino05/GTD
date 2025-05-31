package com.example.loki.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Perfil {
    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String apellido;
    private String correo;
}
