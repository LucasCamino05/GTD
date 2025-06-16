package com.example.loki.model;

import com.example.loki.model.entities.Oferta;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
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

  @OneToMany(mappedBy = "vendedor")
  private List<Oferta> ofertas;

  public Vendedor(String nombre, String apellido, String email, LocalDate fecha_alta, String cvu, String alias) {
    super(nombre, apellido, email, fecha_alta);
    this.cvu = cvu;
    this.alias = alias;
  }

}
