package com.example.loki.controller;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.Vendedor;
import com.example.loki.service.VendedorService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
      throws PerfilNotFound {
    Optional<Vendedor> vendedor = vendedorService.getVendedorById(id);
    if (vendedor.isPresent()) {
      try {
        objectMapper.updateValue(vendedor.get(), modificacion);
        vendedorService.validarVendedor(vendedor.get());
      } catch (JsonMappingException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
      vendedorService.saveVendedor(vendedor.get());
      return ResponseEntity.ok("Vendedor modificado");
    } else {
      throw new PerfilNotFound("Usuario no encontrado");
      // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendedor no
      // encontrado");
    }
  }

  @PostMapping
  public ResponseEntity<?> crear(@RequestBody Vendedor vendedor) {
    vendedorService.saveVendedor(vendedor);
    return ResponseEntity.ok("Vendedor creado");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarVendedor(@PathVariable Long id) throws PerfilNotFound {
    vendedorService.deleteVendedor(id);
    return ResponseEntity.ok("Vendedor eliminado");
  }

  @ExceptionHandler
  public ResponseEntity<String> manejadorPerfilNotFound(PerfilNotFound ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler
  public ResponseEntity<?> manejadorArgumentoInvalido(MethodArgumentNotValidException e) {
    Map<String, Object> respuesta = new HashMap<>();
    Map<String, String> errores = new HashMap<>();

    e.getBindingResult().getAllErrors().forEach((error) -> {
      String atributo = ((FieldError) error).getField();
      String mensaje = error.getDefaultMessage();
      errores.put(atributo, mensaje);
    });

    respuesta.put("status", "error");
    respuesta.put("message", "datos invalidos");
    respuesta.put("errors", errores);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(respuesta);
  }
  public ResponseEntity<?> manejadorUsuarioInvalido(ConstraintViolationException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }

}
