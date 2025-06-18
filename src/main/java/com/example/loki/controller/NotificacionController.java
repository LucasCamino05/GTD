package com.example.loki.controller;

import com.example.loki.exceptions.NotificacionNoEncontradaException;
import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.dto.NotificacionResponseDTO;
import com.example.loki.model.entities.Notificacion;
import com.example.loki.model.entities.Perfil;
import com.example.loki.security.UserDetailsImpl;
import com.example.loki.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notificaciones")
public class NotificacionController {
    private final NotificacionService service;

    @Autowired
    public NotificacionController(NotificacionService service) {
        this.service = service;
    }

    @GetMapping("/mis-notificaciones")
    public ResponseEntity<?> getNotificaciones(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Perfil perfil = userDetails.getPerfil();

        try{
            List<NotificacionResponseDTO> notificaciones = service.getNotificaciones(perfil);
            return ResponseEntity.ok().body(notificaciones);
        }catch (PerfilNotFound e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/leido")
    public ResponseEntity<?> leerNotificacion(@PathVariable Long id, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Perfil perfil = userDetails.getPerfil();

        try{
            service.leerNotificacion(id, perfil);
            return ResponseEntity.ok().build();
        } catch (NotificacionNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<?> eliminarNotificacion(@PathVariable Long id, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Perfil perfil = userDetails.getPerfil();

        try{
            service.eliminarNotificacion(id, perfil);
            return ResponseEntity.noContent().build();
        }catch (NotificacionNoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
