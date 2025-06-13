package com.example.loki.model.mappers;

import com.example.loki.model.Vendedor;
import com.example.loki.model.dto.VendedorRequestDTO;
import com.example.loki.model.dto.VendedorResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendedorMapper {
    Vendedor toEntity(VendedorRequestDTO vendedorRequestDTO);
    VendedorResponseDTO toDTO(Vendedor vendedor);
}
