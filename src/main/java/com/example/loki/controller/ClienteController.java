package com.example.loki.controller;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.dto.ClienteRequestDTO;
import com.example.loki.model.dto.ClienteResponseDTO;
import com.example.loki.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/clientes")
public class ClienteController {

  @Autowired
  private ClienteService clienteService;

  @GetMapping
  public List<ClienteResponseDTO> getCliente(){
    return clienteService.getClientes()
            .stream()
            .toList();
  }
  @GetMapping("/{id}")
  public ClienteResponseDTO getClienteById(@PathVariable Long id) throws PerfilNotFound{
    return clienteService.getCliente(id);
  }

  @PostMapping("/add")
  public ResponseEntity<?> crear(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO, BindingResult result) {
    if(result.hasErrors()){
      Map<String, String> errores = new HashMap<>();
      result.getFieldErrors().forEach(error ->
              errores.put(error.getField(),error.getDefaultMessage()));
      return ResponseEntity.badRequest().body(errores);
    }
    ClienteResponseDTO clienteResponseDTO = clienteService.saveCliente(clienteRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponseDTO);
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
