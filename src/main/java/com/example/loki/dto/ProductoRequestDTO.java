package com.example.loki.dto;

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
    @Positive
    private Double precio;


}
