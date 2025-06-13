package com.example.loki.controller;

import com.example.loki.model.dto.ProductoRequestDTO;
import com.example.loki.model.dto.ProductoResponseDTO;
import com.example.loki.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("loki/v1/productos")
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
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        try{
            ProductoResponseDTO producto = service.getProductById(id);
            return ResponseEntity.ok(producto);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ProductoResponseDTO> createProduct(@RequestBody ProductoRequestDTO nuevo){
        ProductoResponseDTO guardado = service.createProduct(nuevo);

        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id){
        service.deleteProduct(id);
        return ResponseEntity.ok("Producto " + id + " eliminado.");
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateProductoById(
            @PathVariable Long id,
            @RequestBody Map<String, Object> modificacion){
        try{
            service.updateProducto(id, modificacion);
            return ResponseEntity.ok("Producto modificado correctamente.");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
