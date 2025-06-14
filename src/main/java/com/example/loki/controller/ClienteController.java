package com.example.loki.controller;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.Cliente;
import com.example.loki.service.ClienteService;

import jakarta.validation.Valid;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

  @Autowired
  private ClienteService clienteService;

  @GetMapping
  public List<String> getCliente() {
    return clienteService.getCliente()
        .stream().map(x -> x.toString())
        .toList();
  }

  @GetMapping("/{id}")
  public Cliente getClienteById(@PathVariable Long id) throws PerfilNotFound {
    return clienteService.getClienteById(id).orElseThrow(() -> new PerfilNotFound("Usuario no encontrado"));
  }

  @PostMapping
  public ResponseEntity<?> crear(@RequestBody @Valid Cliente cliente) {
    clienteService.saveCliente(cliente);
    return ResponseEntity.ok("cliente creado");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarCliente(@PathVariable Long id) throws PerfilNotFound {
    clienteService.deleteCliente(id);
    return ResponseEntity.ok("Eliminado con exito!");
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Map<String, Object> modificacion)
      throws PerfilNotFound, ConstraintViolationException {
    try {
      clienteService.updateCliente(id, modificacion);
      return ResponseEntity.ok("Vendedor modificado");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    // otras excepeciones son manejadas por GlobalExceptionHandler y PerfilExceptionHandler
  }

}
