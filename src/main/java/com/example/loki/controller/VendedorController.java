package com.example.loki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loki.model.Vendedor;
import com.example.loki.repository.VendedorRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {
  @Autowired
  private VendedorRepository repositorio;

  @PostMapping
  public ResponseEntity<?> crear(@RequestBody Vendedor vendedor) {
    repositorio.save(vendedor);
    return ResponseEntity.ok("Vendedor creado");
  }
}
