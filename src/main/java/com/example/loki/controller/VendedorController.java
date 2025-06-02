package com.example.loki.controller;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.Vendedor;
import com.example.loki.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {

  @Autowired
  private VendedorService vendedorService;

  @GetMapping
  public List<String> getVendedor(){
    return vendedorService.getVendedor()
            .stream().map(x -> x.toString())
            .toList();
  }
  @GetMapping("/{id}")
  public String getVendedorById(@PathVariable Long id) throws PerfilNotFound{
    return vendedorService.getVendedorById(id)
            .orElseThrow(() -> new PerfilNotFound("Usuario no encontrado"))
            .toString();
  }

  @PostMapping
  public ResponseEntity<?> crear(@RequestBody Vendedor vendedor) {
    vendedorService.saveVendedor(vendedor);
    return ResponseEntity.ok("Vendedor creado");
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarVendedor(@PathVariable Long id) throws PerfilNotFound{
    vendedorService.deleteVendedor(id);
    return ResponseEntity.ok("Vendedor eliminado");
  }
  @ExceptionHandler
  public ResponseEntity<String> manejadorPerfilNotFound(PerfilNotFound ex){
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}
