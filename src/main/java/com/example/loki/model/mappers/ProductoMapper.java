package com.example.loki.model.mappers;


import com.example.loki.model.dto.ProductoRequestDTO;
import com.example.loki.model.dto.ProductoResponseDTO;
import com.example.loki.model.entities.Producto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    Producto toEntity(ProductoRequestDTO productoRequestDTO);
    ProductoResponseDTO toDTOResponse(Producto producto);
}
