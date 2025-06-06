package com.example.loki.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String marca;
    @Column(nullable = false)
    private String origen;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private String categoria;
    @Column(nullable = false)
    private Double precio;
    @Column(nullable = false)
    private LocalDate fechaAlta;

    public Producto(String nombre, String marca, String origen, String descripcion, String categoria, Double precio) {
        this.nombre = nombre;
        this.marca = marca;
        this.origen = origen;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        fechaAlta = LocalDate.now();
    }
}
