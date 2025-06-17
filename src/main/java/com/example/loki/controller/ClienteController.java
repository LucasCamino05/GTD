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
import com.example.loki.model.dto.ClienteRequestDTO;
import com.example.loki.model.dto.ClienteResponseDTO;
import com.example.loki.service.ClienteService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("loki/v1/clientes")
public class ClienteController {

  @Autowired
  private ClienteService clienteService;

  @GetMapping
  public List<ClienteResponseDTO> getCliente() {
    return clienteService.getClientes()
        .stream()
        .toList();
  }

  @GetMapping("/{id}")
  public ClienteResponseDTO getClienteById(@PathVariable Long id) throws PerfilNotFound {
    return clienteService.getCliente(id);
  }

  @PostMapping("/add")
  public ResponseEntity<?> crear(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
    ClienteResponseDTO clienteResponseDTO = clienteService.saveCliente(clienteRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponseDTO);
    // MethodArgumentNotValidException es manejada por GlobalExceptionHandler
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
