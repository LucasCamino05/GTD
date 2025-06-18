package com.example.loki.service;

import com.example.loki.exceptions.NotificacionNoEncontradaException;
import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.dto.NotificacionResponseDTO;
import com.example.loki.model.entities.Perfil;

import java.util.List;

public interface NotificacionService {
    List<NotificacionResponseDTO> getNotificaciones(Perfil perfil) throws PerfilNotFound;
    void notificar(Perfil perfil, String mensaje);
    void leerNotificacion(Long id, Perfil perfil) throws NotificacionNoEncontradaException;
    void eliminarNotificacion(Long id, Perfil perfil) throws NotificacionNoEncontradaException;
}
