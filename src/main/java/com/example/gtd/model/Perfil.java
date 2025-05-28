package com.example.gtd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Perfil {
    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
