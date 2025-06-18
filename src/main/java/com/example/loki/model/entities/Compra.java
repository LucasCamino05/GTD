package com.example.loki.model.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compras")
@Data
@NoArgsConstructor

public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compra_seq_gen")
    @SequenceGenerator(
            name = "compra_seq_gen",
            sequenceName = "compra_seq",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
            name = "compra_productos",
            joinColumns = @JoinColumn(name = "compra_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos;

    private Double precioTotal;
    private LocalDate fechaCompra;

}
