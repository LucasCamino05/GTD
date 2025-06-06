package com.example.loki.controller;

import com.example.loki.dto.ProductoRequestDTO;
import com.example.loki.dto.ProductoResponseDTO;
import com.example.loki.mappers.ProductoMapper;
import com.example.loki.model.Producto;
import com.example.loki.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("loki/v1/products")
public class ProductController {
    private final ProductoService service;

    @Autowired
    public ProductController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductoResponseDTO> getAllProducts(){
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Producto> getProductById(@PathVariable Long id){
        return service.getProductById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductoResponseDTO> createProduct(@RequestBody ProductoRequestDTO dto){
        ProductoResponseDTO guardado = service.createProduct(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id){
        service.deleteProduct(id);
        return ResponseEntity.ok("Producto " + id + " eliminado.");
    }
}
