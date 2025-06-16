package com.example.loki.model.dto;

import com.example.loki.model.entities.Oferta;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClienteResponseDTO {
    private String nombre;
    private String apellido;
    private String email;
    private List<Oferta> ofertas;
}
