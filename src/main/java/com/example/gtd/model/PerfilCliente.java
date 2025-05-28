package com.example.gtd.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data @EqualsAndHashCode(callSuper = true)
public class PerfilCliente extends Perfil {
  // atributos adicionales
}
