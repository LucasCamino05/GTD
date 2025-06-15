package com.example.loki.model.entities;

import com.example.loki.model.Cliente;
import com.example.loki.model.Vendedor;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ofertas")
@Getter
@Setter
@EqualsAndHashCode
public class Oferta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_oferta;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_vendedor")
    private Vendedor vendedor;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;
}
