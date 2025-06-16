package com.example.loki.model;

import com.example.loki.model.entities.Oferta;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data @EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Cliente extends Perfil {

    @OneToMany(mappedBy = "cliente")
    private List<Oferta> ofertas;

    public Cliente(String nombre, String apellido, String email, LocalDate fecha_alta) {
        super(nombre, apellido, email, fecha_alta);
    }

    public Cliente() {

    }
}
