package com.example.loki.controller;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.loki.model.Cliente;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

  @Autowired
  private ClienteService clienteService;

  @GetMapping
  public List<String> getCliente(){
    return clienteService.getCliente()
            .stream().map(x-> x.toString())
            .toList();
  }
  @GetMapping("/{id}")
  public Cliente getClienteById(@PathVariable Long id) throws PerfilNotFound{
    return clienteService.getClienteById(id).orElseThrow(() -> new PerfilNotFound("Usuario no encontrado"));
  }

  @PostMapping
  public ResponseEntity<?> crear(@RequestBody Cliente cliente) {
    clienteService.saveCliente(cliente);
    return ResponseEntity.ok("cliente creado");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarCliente(@PathVariable Long id) throws PerfilNotFound{
    clienteService.deleteCliente(id);
    return ResponseEntity.ok("Eliminado con exito!");
  }
  @ExceptionHandler
  public ResponseEntity<String> manejadorPerfilNotFound(PerfilNotFound ex){
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}
