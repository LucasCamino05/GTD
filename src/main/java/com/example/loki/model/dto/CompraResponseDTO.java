package com.example.loki.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
public class CompraResponseDTO {
    private ClienteResponseDTO clienteResponseDTO;
    private List<ProductoResponseDTO> productos;
    private Double precioFinal;
}
