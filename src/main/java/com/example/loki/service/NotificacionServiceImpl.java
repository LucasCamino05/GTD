package com.example.loki.service;

import com.example.loki.exceptions.NotificacionNoEncontradaException;
import com.example.loki.model.dto.NotificacionResponseDTO;
import com.example.loki.model.entities.Notificacion;
import com.example.loki.model.entities.Perfil;
import com.example.loki.model.mappers.NotificacionMapper;
import com.example.loki.repository.NotificacionRepository;
import com.example.loki.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificacionServiceImpl implements NotificacionService{
    private final NotificacionRepository repository;
    private final NotificacionMapper mapper;
    private final PerfilRepository perfilRepository;

    @Autowired
    public NotificacionServiceImpl(NotificacionRepository repository, NotificacionMapper mapper, PerfilRepository perfilRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.perfilRepository = perfilRepository;
    }

    @Override
    public List<NotificacionResponseDTO> getNotificaciones(Perfil perfil){
        List<Notificacion> notificaciones =  repository.findNotificacionByPerfil(perfil);

        return notificaciones
                .stream()
                .map(mapper::notificacionToDTO)
                .toList();
    }

    @Override
    public void notificar(Perfil perfil, String mensaje) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(mensaje);
        notificacion.setPerfil(perfil);
        notificacion.setFecha(LocalDate.now());
        notificacion.setLeido(false);

        repository.save(notificacion);
    }

    @Override
    public void leerNotificacion(Long id, Perfil perfil) throws NotificacionNoEncontradaException {
        Notificacion notificacion = buscarNotificacion(id, perfil);

        notificacion.setLeido(true);
        repository.save(notificacion);
    }

    @Override
    public void eliminarNotificacion(Long id, Perfil perfil) throws NotificacionNoEncontradaException {
        Notificacion notificacion = buscarNotificacion(id, perfil);

        repository.delete(notificacion);
    }

    private Notificacion buscarNotificacion(Long id, Perfil perfil) throws NotificacionNoEncontradaException {
        return repository.findByIdAndPerfil(id, perfil)
                .orElseThrow(() -> new NotificacionNoEncontradaException("No existe la notificacion."));
    }
}
