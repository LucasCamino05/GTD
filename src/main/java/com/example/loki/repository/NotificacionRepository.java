package com.example.loki.repository;

import com.example.loki.model.entities.Notificacion;
import com.example.loki.model.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findNotificacionByPerfil(Perfil perfil);
    Optional<Notificacion> findByIdAndPerfil(Long id, Perfil perfil);
}
