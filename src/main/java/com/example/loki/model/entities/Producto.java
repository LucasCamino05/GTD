package com.example.loki.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@ToString
@NoArgsConstructor
public class Producto {
    @Id
    @SequenceGenerator(
            name = "producto_seq_gen",
            sequenceName = "producto_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_seq_gen")
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
    private Integer stock;
    private LocalDate fechaAlta;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;

//    @OneToMany(mappedBy = "producto")
//    private List<Oferta> ofertas;
}