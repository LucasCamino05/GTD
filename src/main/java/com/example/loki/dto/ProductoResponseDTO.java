package com.example.loki.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductoResponseDTO {
    private String nombre;
    private String marca;
    private Double precio;
}
