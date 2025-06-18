package com.example.loki.model.mappers;

import com.example.loki.model.dto.OfertaRequestDTO;
import com.example.loki.model.dto.OfertaResponseDTO;
import com.example.loki.model.entities.Oferta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfertaMapper {
    Oferta DTOtoOferta(OfertaRequestDTO ofertaRequestDTO);
    OfertaResponseDTO ofertaToDTO(Oferta oferta);
}
