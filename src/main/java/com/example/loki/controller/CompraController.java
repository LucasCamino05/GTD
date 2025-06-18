package com.example.loki.controller;

import com.example.loki.model.dto.CompraResponseDTO;
import com.example.loki.model.entities.Perfil;
import com.example.loki.security.UserDetailsImpl;
import com.example.loki.service.CompraServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraServiceImpl compraService;


    @GetMapping("/compra")
    public ResponseEntity<?> obtenerCompra() {
        CompraResponseDTO response = compraService.obtenerCompra();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/comprar")
    public ResponseEntity<?> crearCompra(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Perfil perfil = userDetails.getPerfil();
        try {
            compraService.crearCompra(perfil);
            return ResponseEntity.ok().body("Compra iniciada. Ya podes agregar oroductos.");
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/compra/agregar/{id}")
    public ResponseEntity<?> agregarProducto(@PathVariable Long id) {
        compraService.agregarProducto(id);
        return ResponseEntity.ok().body("Se agrego correctamente.");
    }

    @DeleteMapping("/compra/quitar/{id}")
    public ResponseEntity<?> quitarProducto(@PathVariable Long id) {
        compraService.quitarProducto(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/compra/limpiar")
    public ResponseEntity<?> limpiarCarrito() {
        compraService.limpiarCompra();
        return ResponseEntity.ok("Se vació el carrito");
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarCompra() {
        try {
            compraService.confirmarCompra();
            return ResponseEntity.ok("Compra realizada exitosamente");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}