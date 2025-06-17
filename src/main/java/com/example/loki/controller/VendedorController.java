package com.example.loki.controller;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.dto.VendedorRequestDTO;
import com.example.loki.model.dto.VendedorResponseDTO;
import com.example.loki.service.VendedorService;
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
@RequestMapping("/loki/v1/vendedores")
public class VendedorController {

  @Autowired
  private VendedorService vendedorService;

  @GetMapping
  public List<VendedorResponseDTO> getVendedor(){
    return vendedorService.getVendedores();
  }
  @GetMapping("/{id}")
  public String getVendedorById(@PathVariable Long id) throws PerfilNotFound{
    return vendedorService.getVendedor(id)
            .toString();
  }

  @PostMapping()
  public ResponseEntity<?> crear(@Valid @RequestBody VendedorRequestDTO vendedorRequestDTO, BindingResult result) {
    if(result.hasErrors()){
      Map<String, String> errores = new HashMap<>();
      result.getFieldErrors().forEach(error ->
              errores.put(error.getField(),error.getDefaultMessage()));
      return ResponseEntity.badRequest().body(errores);
    }

    VendedorResponseDTO vendedorResponseDTO = vendedorService.saveVendedor(vendedorRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(vendedorResponseDTO);
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
