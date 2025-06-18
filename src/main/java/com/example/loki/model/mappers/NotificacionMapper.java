package com.example.loki.model.mappers;

import com.example.loki.model.dto.NotificacionRequestDTO;
import com.example.loki.model.dto.NotificacionResponseDTO;
import com.example.loki.model.entities.Notificacion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificacionMapper {
    NotificacionResponseDTO notificacionToDTO(Notificacion notificacion);

    Notificacion DTOtoNotificacion(NotificacionRequestDTO notificacionRequestDTO);
}
