package com.example.loki.model.entities;

import com.example.loki.model.enums.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Perfil {
    @Id
    @SequenceGenerator(
            name = "perfil_seq_gen",
            sequenceName = "perfil_seq_gen",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_seq_gen")

    private Long id;

    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String email;
    private String password;

    private LocalDate fecha_alta;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToMany(mappedBy = "perfil")
    private List<Notificacion> notificaciones = new ArrayList<>();

    public Perfil(String nombre, String apellido, String email, LocalDate fecha_alta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fecha_alta = fecha_alta;
    }
}
