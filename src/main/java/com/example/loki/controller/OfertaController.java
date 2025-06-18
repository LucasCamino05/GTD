package com.example.loki.controller;

import com.example.loki.exceptions.OfertaNoEncontradaException;
import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.exceptions.ProductoNoEncontradoException;
import com.example.loki.model.dto.OfertaRequestDTO;
import com.example.loki.model.dto.OfertaResponseDTO;
import com.example.loki.model.entities.Cliente;
import com.example.loki.model.entities.Perfil;
import com.example.loki.model.entities.Vendedor;
import com.example.loki.security.UserDetailsImpl;
import com.example.loki.service.OfertaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/ofertas")
public class OfertaController{
    private final OfertaServiceImpl ofertaService;

    @Autowired
    public OfertaController(OfertaServiceImpl ofertaService){
        this.ofertaService = ofertaService;
    }

    @PostMapping("/crear")
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
            // CREAR NOTIFICACION
            return ResponseEntity.status(HttpStatus.CREATED).body(ofertaResponseDTO);
        }catch (ProductoNoEncontradoException p){
            return ResponseEntity.badRequest().body(p.getMessage());
        }
    }

    @GetMapping("/mis-ofertas")
    public ResponseEntity<?> getOfertas(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Perfil perfil = userDetails.getPerfil();

        try{
            List<OfertaResponseDTO> ofertas = ofertaService.getAllOfertas(perfil);
            return ResponseEntity.ok(ofertas);
        }catch (PerfilNotFound e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PatchMapping("/actualizar/{id}")
    public ResponseEntity updateOferta(@PathVariable Long id,
                                       @RequestBody Double precio,
                                       Authentication authentication){

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Perfil perfil = userDetails.getPerfil();

        try{
            ofertaService.updateOferta(id, precio, perfil);
            return ResponseEntity.ok("Oferta modificada.");
        }catch (OfertaNoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
