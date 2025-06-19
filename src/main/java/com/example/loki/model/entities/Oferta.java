package com.example.loki.model.entities;

import com.example.loki.model.enums.EstadoOferta;
import com.example.loki.model.enums.Rol;
import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.Pair;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name = "ofertas")
@Data
public class Oferta {
    @Id
    @SequenceGenerator(
            name = "oferta_seq_gen",
            sequenceName = "oferta_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oferta_seq_gen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_vendedor")
    private Vendedor vendedor;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    private LocalDate fecha;

    @ElementCollection
    @CollectionTable(name = "negociacion", joinColumns = @JoinColumn(name = "negociacion_id"))
    @MapKeyColumn(name = "rol")
    @Column(name = "monto")
    private Map<Rol, Double> ofertas = new LinkedHashMap<>();

//    @ElementCollection
//    @CollectionTable(name = "contraoferta_vendedor", joinColumns = @JoinColumn(name = "oferta_id"))
//    @MapKeyColumn(name = "nro_contraoferta")
//    @Column(name = "monto")
//    private HashMap<Integer, Double> ofertaVendedor;

    @Enumerated(EnumType.STRING)
    private EstadoOferta estado;


    public void agregarPrecio(Rol rol, Double precio) {
        ofertas.put(rol, precio);
    }
}
