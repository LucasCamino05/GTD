package com.example.loki.controller;

import com.example.loki.model.dto.*;
import com.example.loki.model.entities.Cliente;
import com.example.loki.model.entities.Perfil;
import com.example.loki.model.entities.Vendedor;
import com.example.loki.security.JwtService;
import com.example.loki.security.UserDetailsImpl;
import com.example.loki.service.ClienteService;
import com.example.loki.service.VendedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class PerfilController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ClienteService clienteService;
    private final VendedorService vendedorService;

    @PostMapping("/login")
    public ResponseEntity<PerfilResponseDTO> login(@RequestBody PerfilRequestDTO perfilRequestDTO){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(perfilRequestDTO.getEmail(), perfilRequestDTO.getPassword())
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Perfil perfil = userDetails.getPerfil();
//        UserDetails userDetails = new UserDetailsImpl(perfil);
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new PerfilResponseDTO(token, perfil.getRol().name()));
    }

    @PostMapping("/registrar/cliente")
    public ResponseEntity<?> registrarCliente(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO,
                                              BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errores);
        }

        clienteRequestDTO.setPassword(passwordEncoder.encode(clienteRequestDTO.getPassword()));

        ClienteResponseDTO guardado = clienteService.saveCliente(clienteRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PostMapping("/registrar/vendedor")
    public ResponseEntity<?> registrarVendedor(@Valid @RequestBody VendedorRequestDTO vendedorRequestDTO,
                                               BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage())
            );

            return ResponseEntity.badRequest().body(errores);
        }

        vendedorRequestDTO.setPassword(passwordEncoder.encode(vendedorRequestDTO.getPassword()));

        VendedorResponseDTO guardado = vendedorService.saveVendedor(vendedorRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getPerfilAutenticado(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Perfil perfil = userDetails.getPerfil();

        if(perfil instanceof Cliente){
            ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(
                    perfil.getNombre(),
                    perfil.getApellido(),
                    perfil.getEmail()
            );

            return ResponseEntity.ok().body(clienteResponseDTO);
        }

        if(perfil instanceof Vendedor){
            VendedorResponseDTO vendedorResponseDTO = new VendedorResponseDTO(
                    perfil.getNombre(),
                    perfil.getApellido(),
                    perfil.getEmail(),
                    perfil.getFecha_alta());

            return ResponseEntity.ok().body(vendedorResponseDTO);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Tipo de perfil desconocido.");
    }
}
