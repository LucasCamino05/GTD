package com.example.loki.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VendedorRequestDTO {
    @NotBlank @NotEmpty @NotNull
    private String nombre;
    @NotBlank @NotEmpty @NotNull
    private String apellido;
    @NotBlank @NotEmpty @NotNull
    private String correo;
    @NotBlank @NotEmpty @NotNull
    private String cvu;
    @NotBlank @NotEmpty @NotNull
    private String alias;
}
