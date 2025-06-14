package com.example.loki.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

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
    private String correo;
    @NotBlank(message = "El cvu es obligatorio.")
    private String cvu;
    @NotBlank(message = "El alias es obligatorio.")
    private String alias;
}
