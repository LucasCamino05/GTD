package com.example.loki.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductoRequestDTO {
    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;
    @NotBlank(message = "La marca es obligatorio.")
    private String marca;
    @NotBlank(message = "El origen es obligatorio.")
    private String origen;
    @NotBlank(message = "La descripcion es obligatorio.")
    private String descripcion;
    @NotBlank(message = "La categoria es obligatorio.")
    private String categoria;
    @NotNull(message = "El precio es obligatorio.")
    @Min(value = 0, message = "El precio no puede ser negativo.")
    private Double precio;
    @NotNull(message = "El stock es obligatorio.")
    @Min(value = 0, message = "El stock no puede ser negativo.")
    private Integer stock;
}
