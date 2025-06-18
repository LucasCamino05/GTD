package com.example.loki.model.dto;

import com.example.loki.model.enums.EstadoOferta;
import com.example.loki.model.enums.Rol;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class OfertaResponseDTO {
    private Long id;
    private ProductoResponseDTO producto;
    private Map<Rol, Double> ofertas;
    private EstadoOferta estado;
}
