package com.example.loki.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.Vendedor;
import com.example.loki.service.VendedorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {

  @Autowired
  private VendedorService vendedorService;
  @Autowired
  private ObjectMapper objectMapper;

  @GetMapping
  public List<String> getVendedor() {
    return vendedorService.getVendedor()
        .stream().map(x -> x.toString())
        .toList();
  }

  @GetMapping("/{id}")
  public String getVendedorById(@PathVariable Long id) throws PerfilNotFound {
    return vendedorService.getVendedorById(id)
        .orElseThrow(() -> new PerfilNotFound("Usuario no encontrado"))
        .toString();
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Map<String, Object> modificacion)
      throws PerfilNotFound, ConstraintViolationException {
    try {
      vendedorService.modificarVendedor(id, modificacion);
      return ResponseEntity.ok("Vendedor modificado");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    // otras excepeciones son manejadas por GlobalExceptionHandler y PerfilExceptionHandler
  }

  @PostMapping
  public ResponseEntity<?> crear(@RequestBody @Valid Vendedor vendedor) {
    vendedorService.saveVendedor(vendedor);
    return ResponseEntity.ok("Vendedor creado");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarVendedor(@PathVariable Long id) throws PerfilNotFound {
    vendedorService.deleteVendedor(id);
    return ResponseEntity.ok("Vendedor eliminado");
  }
}
