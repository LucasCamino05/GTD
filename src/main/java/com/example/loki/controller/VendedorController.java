package com.example.loki.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.example.loki.model.dto.VendedorRequestDTO;
import com.example.loki.model.dto.VendedorResponseDTO;
import com.example.loki.service.VendedorService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/loki/v1/vendedores")
public class VendedorController {

  @Autowired
  private VendedorService vendedorService;

  @GetMapping
  public List<String> getVendedor() {
    return vendedorService.getVendedores()
        .stream().map(x -> x.toString())
        .toList();
  }

  @GetMapping("/{id}")
  public String getVendedorById(@PathVariable Long id) throws PerfilNotFound {
    return vendedorService.getVendedor(id)
        .toString();
  }

  @PostMapping()
  public ResponseEntity<?> crear(@Valid @RequestBody VendedorRequestDTO vendedorRequestDTO) {
    VendedorResponseDTO vendedorResponseDTO = vendedorService.saveVendedor(vendedorRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(vendedorResponseDTO);
    // MethodArgumentNotValidException es manejada por GlobalExceptionHandler
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

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarVendedor(@PathVariable Long id) throws PerfilNotFound {
    vendedorService.deleteVendedor(id);
    return ResponseEntity.ok("Vendedor eliminado");
  }
}
