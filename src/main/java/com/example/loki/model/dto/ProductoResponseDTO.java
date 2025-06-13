package com.example.loki.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {
    private String nombre;
    private String marca;
    private Double precio;
}
