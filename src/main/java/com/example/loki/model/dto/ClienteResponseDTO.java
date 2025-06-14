package com.example.loki.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClienteResponseDTO {
    private String nombre;
    private String apellido;
    private String correo;
}
