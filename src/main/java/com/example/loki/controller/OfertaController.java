package com.example.loki.controller;

import com.example.loki.model.entities.Oferta;
import com.example.loki.model.entities.Perfil;
import com.example.loki.exceptions.NotFoundException;
import com.example.loki.security.UserDetailsImpl;
import com.example.loki.service.OfertaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("loki/v1/oferta")
public class OfertaController{
    private final OfertaServiceImpl ofertaService;

    @Autowired
    public OfertaController(OfertaServiceImpl ofertaService){
        this.ofertaService = ofertaService;
    }

// locah...../api/producto/[id]/oferta/nueva-oferta
    @PutMapping("/{id}/aceptar")
    public ResponseEntity<?> aceptarOferta(@PathVariable Long id, Authentication authentication) {
        Perfil perfil = ((UserDetailsImpl) authentication.getPrincipal()).getPerfil();
        Oferta oferta = ofertaService.aceptarOferta(id, perfil);
        return ResponseEntity.ok(oferta);
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazarOferta(@PathVariable Long id, Authentication authentication) {
        Perfil perfil = ((UserDetailsImpl) authentication.getPrincipal()).getPerfil();
        Oferta oferta = ofertaService.rechazarOferta(id, perfil);
        return ResponseEntity.ok(oferta);
    }
}
