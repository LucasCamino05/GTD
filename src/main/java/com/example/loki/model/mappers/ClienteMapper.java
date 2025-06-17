package com.example.loki.model.mappers;

import com.example.loki.model.entities.Cliente;
import com.example.loki.model.dto.ClienteRequestDTO;
import com.example.loki.model.dto.ClienteResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    Cliente ClienteDTOtoEntity(ClienteRequestDTO clienteRequestDTO);
    ClienteResponseDTO ClientetoDTO(Cliente cliente);
}
