package com.example.loki.model.dto;

import com.example.loki.model.entities.Oferta;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VendedorResponseDTO {
    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fecha_alta;
//    private String cvu;
//    private String alias;
//    private List<Oferta> ofertas;
}
