package com.example.loki.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data @EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Cliente extends Perfil {
}
