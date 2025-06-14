package com.example.loki.model.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VendedorResponseDTO {
    private String nombre;
    private String apellido;
    private String correo;
    private LocalDate fecha_alta;
    private String cvu;
    private String alias;

}
