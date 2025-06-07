package com.example.loki.service;

import com.example.loki.exceptions.ProductoNoEncontradoException;
import com.example.loki.model.dto.ProductoRequestDTO;
import com.example.loki.model.dto.ProductoResponseDTO;

import java.util.List;
import java.util.Map;

public interface ProductoService {
    List<ProductoResponseDTO> getAllProducts();
    ProductoResponseDTO getProductById(Long id);
    ProductoResponseDTO createProduct(ProductoRequestDTO dto);
    void deleteProduct(Long id);
    void updateProducto(Long id, Map<String, Object> modificacion) throws ProductoNoEncontradoException;
}
