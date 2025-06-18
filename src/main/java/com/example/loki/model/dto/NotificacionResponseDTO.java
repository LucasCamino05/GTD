package com.example.loki.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NotificacionResponseDTO {
    private String mensaje;
    private LocalDate fecha;
    private Boolean leido;
}
