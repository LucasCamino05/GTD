package com.example.loki.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClienteRequestDTO {
    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio.")
    private String apellido;
    @Email
    @NotBlank (message = "El correo es obligatorio.")
    private String email;
}
