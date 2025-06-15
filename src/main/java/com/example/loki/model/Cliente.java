package com.example.loki.model;

import com.example.loki.model.entities.Oferta;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Data @EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Cliente extends Perfil {

    @OneToMany(mappedBy = "cliente")
    private List<Oferta> ofertas;
}
