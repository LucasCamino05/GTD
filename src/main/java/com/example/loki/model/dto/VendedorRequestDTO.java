package com.example.loki.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VendedorRequestDTO {
    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio.")
    private String apellido;
    @Email
    @NotBlank (message = "El correo es obligatorio.")
    private String email;
    @NotBlank(message = "El cvu es obligatorio.")
    private String cvu;
    @NotBlank(message = "El alias es obligatorio.")
    private String alias;
    @NotBlank
    private String pass;
}
