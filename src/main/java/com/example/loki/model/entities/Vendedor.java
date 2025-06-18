package com.example.loki.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table( name = "sellers")

public class Vendedor extends Perfil {
  private String cvu;
  private String alias;

  // OPCIONAL PERO SIRVE PARA CREAR UN METODO PARA QUE EL VENDEDOR VEA TODOS SUS PRODUCTOS
  // http://localhost:8080/api/vendedor/{id}/mis-productos
  @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL)
  private List<Producto> productos = new ArrayList<>();

  // http://localhost:8080/api/vendedor/{id}/mis-ofertas
  @OneToMany(mappedBy = "vendedor")
  private List<Oferta> ofertas = new ArrayList<>();

  public Vendedor(String nombre, String apellido, String email, LocalDate fecha_alta, String cvu, String alias) {
    super(nombre, apellido, email, fecha_alta);
    this.cvu = cvu;
    this.alias = alias;
  }
}
