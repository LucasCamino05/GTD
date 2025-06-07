package com.example.loki.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductoRequestDTO {
    @NotBlank
    private String nombre;
    @NotBlank
    private String marca;
    @NotBlank
    private String origen;
    @NotBlank
    private String descripcion;
    @NotBlank
    private String categoria;
    @NotBlank @Positive
    private Double precio;
    @NotBlank @Positive
    private Integer stock;
}
