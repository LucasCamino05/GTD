package com.example.loki.model.mappers;

import com.example.loki.model.dto.CompraResponseDTO;
import com.example.loki.model.dto.ClienteResponseDTO;
import com.example.loki.model.dto.ProductoResponseDTO;
import com.example.loki.model.entities.Compra;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompraMapper {
    Compra DTOtoCarrito(CompraResponseDTO compraResponseDTO);
    CompraResponseDTO CarritotoDTO(ClienteResponseDTO cliente, List<ProductoResponseDTO> productos, Double precioFinal);
}
