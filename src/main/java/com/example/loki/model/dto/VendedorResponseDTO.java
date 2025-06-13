package com.example.loki.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VendedorResponseDTO {
    private String nombre;
    private String apellido;
    private String correo;
    private String cvu;
    private String alias;
}
