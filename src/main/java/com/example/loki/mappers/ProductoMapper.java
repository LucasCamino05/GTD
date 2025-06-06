package com.example.loki.mappers;


import com.example.loki.dto.ProductoRequestDTO;
import com.example.loki.dto.ProductoResponseDTO;
import com.example.loki.model.Producto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    Producto toEntity(ProductoRequestDTO productoRequestDTO);
    ProductoResponseDTO toDTOResponse(Producto producto);

    Producto toEntity(ProductoResponseDTO productoResponseDTO);
    ProductoRequestDTO toDTORequest(Producto producto);

}
