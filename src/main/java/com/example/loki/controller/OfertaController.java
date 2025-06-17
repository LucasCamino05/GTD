package com.example.loki.controller;

import com.example.loki.model.entities.Perfil;
import com.example.loki.exceptions.NotFoundException;
import com.example.loki.service.OfertaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("loki/v1/oferta")
public class OfertaController{
    private final OfertaServiceImpl ofertaService;

    @Autowired
    public OfertaController(OfertaServiceImpl ofertaService){
        this.ofertaService = ofertaService;
    }

    @PutMapping("/{id}/aceptar")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id) throws NotFoundException {
        try {
            Perfil perfil = (Perfil) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ofertaService.aceptarOferta(id, perfil);
        }catch (NotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Actualizado con exito!");
    }
}
