package com.example.loki.service;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.exceptions.ProductoNoEncontradoException;
import com.example.loki.model.dto.ProductoRequestDTO;
import com.example.loki.model.dto.ProductoResponseDTO;
import com.example.loki.model.entities.Perfil;
import com.example.loki.model.entities.Vendedor;

import java.util.List;
import java.util.Map;

public interface ProductoService {
    List<ProductoResponseDTO> getAllProducts(Perfil perfil) throws PerfilNotFound;
    ProductoResponseDTO getProductById(Long id);
    ProductoResponseDTO createProduct(ProductoRequestDTO dto, Vendedor vendedor);
    void deleteProduct(Long id);
    void updateProducto(Long id, Map<String, Object> modificacion) throws ProductoNoEncontradoException;
}
