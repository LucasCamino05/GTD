package com.example.loki.controller;

import com.example.loki.exceptions.NotFoundException;
import com.example.loki.exceptions.ProductoNoEncontradoException;
import com.example.loki.model.dto.OfertaRequestDTO;
import com.example.loki.model.dto.OfertaResponseDTO;
import com.example.loki.model.entities.Cliente;
import com.example.loki.model.entities.Oferta;
import com.example.loki.model.entities.Perfil;
import com.example.loki.security.UserDetailsImpl;
import com.example.loki.service.OfertaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("loki/v1/oferta")
public class OfertaController{
    private final OfertaServiceImpl ofertaService;

    @Autowired
    public OfertaController(OfertaServiceImpl ofertaService){
        this.ofertaService = ofertaService;
    }
    @GetMapping("/mis-ofertas")
    public List<OfertaResponseDTO> mostrarOfertas(Authentication authentication){

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Perfil perfil = userDetails.getPerfil();

        return ofertaService.getAllOfertasByPerfilID(perfil.getId(), perfil.getRol());
    }

    @PostMapping("/ofertar")
    public ResponseEntity<?> crearOferta(@Valid @RequestBody OfertaRequestDTO ofertaRequestDTO,
                                         BindingResult result,
                                         Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Perfil perfil = userDetails.getPerfil();

        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(
                    error -> errores.put(error.getField(), error.getDefaultMessage())
            );

            return ResponseEntity.badRequest().body(errores);
        }

        try{
            OfertaResponseDTO ofertaResponseDTO = ofertaService.crearOferta(ofertaRequestDTO, (Cliente) perfil);
            return ResponseEntity.status(HttpStatus.CREATED).body(ofertaResponseDTO);
        }catch (ProductoNoEncontradoException p){
            return ResponseEntity.badRequest().body(p.getMessage());
        }
    }

    @PutMapping("/{id}/aceptar")
    public ResponseEntity<?> aceptarOferta(@PathVariable Long id, Authentication authentication) {
        try {
            Perfil perfil = ((UserDetailsImpl) authentication.getPrincipal()).getPerfil();
            OfertaResponseDTO oferta = ofertaService.aceptarOferta(id, perfil);
            return ResponseEntity.ok(oferta);
        }catch(NotFoundException | AuthorizationServiceException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazarOferta(@PathVariable Long id, Authentication authentication) {
        try{
            Perfil perfil = ((UserDetailsImpl) authentication.getPrincipal()).getPerfil();
            OfertaResponseDTO oferta = ofertaService.rechazarOferta(id, perfil);
            return ResponseEntity.ok(oferta);
        }catch(NotFoundException | AuthorizationServiceException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
