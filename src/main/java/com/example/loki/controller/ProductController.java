package com.example.loki.controller;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.dto.OfertaResponseDTO;
import com.example.loki.model.dto.ProductoRequestDTO;
import com.example.loki.model.dto.ProductoResponseDTO;
import com.example.loki.model.entities.Perfil;
import com.example.loki.model.entities.Vendedor;
import com.example.loki.security.UserDetailsImpl;
import com.example.loki.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/productos")
public class ProductController {
    private final ProductoService service;

    @Autowired
    public ProductController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Perfil perfil = userDetails.getPerfil();

        try{
            List<ProductoResponseDTO> ofertas = service.getAllProducts(perfil);
            return ResponseEntity.ok().body(ofertas);
        }catch (PerfilNotFound e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

    @PostMapping("/crear")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductoRequestDTO nuevo,
                                           BindingResult result,
                                           Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Perfil perfil = userDetails.getPerfil();

        if (!(perfil instanceof Vendedor)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Solo los vendedores pueden crear productos.");
        }

        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage())
            );

            return ResponseEntity.badRequest().body(errores);
        }

        ProductoResponseDTO guardado = service.createProduct(nuevo, (Vendedor) perfil);

        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Perfil perfil = userDetails.getPerfil();

        if (!(perfil instanceof Vendedor)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Solo los vendedores pueden eliminar productos.");
        }

        service.deleteProduct(id);
        return ResponseEntity.ok("Producto " + id + " eliminado.");
    }

    @PatchMapping("/actualizar/{id}")
    public ResponseEntity updateProductoById(
            @PathVariable Long id,
            @RequestBody Map<String, Object> modificacion,
            Authentication authentication){

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Perfil perfil = userDetails.getPerfil();

        if (!(perfil instanceof Vendedor)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Solo los vendedores pueden modificar productos.");
        }

        try{
            service.updateProducto(id, modificacion);
            return ResponseEntity.ok("Producto modificado correctamente.");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
