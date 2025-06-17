package com.example.loki.model;

import com.example.loki.annotations.NullOrNotBlank;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table( name = "sellers")

public class Vendedor extends Perfil {
  @NullOrNotBlank(message = "Este campo no puede estar vacio")
  private String cvu;
  @NullOrNotBlank(message = "Este campo no puede estar vacio")
  private String alias;

  public Vendedor(String nombre, String apellido, String correo, LocalDate fecha_alta, String cvu, String alias) {
    super(nombre, apellido, correo, fecha_alta);
    this.cvu = cvu;
    this.alias = alias;
  }

//  @Override
//  public String toString() {
//    return "Vendedor{" + super.toString()+
//            "cvu='" + cvu + '\'' +
//            ", alias='" + alias + '\'' +
//            '}';
//  }
}
