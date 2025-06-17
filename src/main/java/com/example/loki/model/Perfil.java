package com.example.loki.model;

import com.example.loki.annotations.NullOrNotBlank;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Perfil {
  @Id
  @GeneratedValue
  private Long id;

  @NullOrNotBlank(message = "Este campo no puede estar vacio")
  private String nombre;
  @NullOrNotBlank(message = "Este campo no puede estar vacio")
  private String apellido;
  @Email(message = "El correo debe ser valido") @NullOrNotBlank(message = "Este campo no puede estar vacio")
  private String correo;
  @NullOrNotBlank(message = "Este campo no puede estar vacio")
  private LocalDate fecha_alta;

  public Perfil(String nombre, String apellido, String correo, LocalDate fecha_alta) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.correo = correo;
    this.fecha_alta = fecha_alta;
  }
}
