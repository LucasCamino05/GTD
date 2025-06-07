package com.example.loki.service;

import com.example.loki.dto.ProductoRequestDTO;
import com.example.loki.dto.ProductoResponseDTO;
import com.example.loki.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<ProductoResponseDTO> getAllProducts();
    ProductoResponseDTO getProductById(Long id);
    ProductoResponseDTO createProduct(ProductoRequestDTO dto);
    void deleteProduct(Long id);
    //Producto updateProduct();
}
