package com.example.loki.service;

import com.example.loki.dto.ProductoRequestDTO;
import com.example.loki.dto.ProductoResponseDTO;
import com.example.loki.mappers.ProductoMapper;
import com.example.loki.model.Producto;
import com.example.loki.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository repository;
    private final ProductoMapper productoMapper;

    @Autowired
    public ProductoServiceImpl(ProductoRepository repository, ProductoMapper productoMapper) {
        this.repository = repository;
        this.productoMapper = productoMapper;
    }

    @Override
    public List<ProductoResponseDTO> getAllProducts() {
        List<Producto> productos =  repository.findAll();
        return productos.stream()
                .map(p -> productoMapper.toDTOResponse(p))
                .toList();
    }

    @Override
    public Optional<Producto> getProductById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ProductoResponseDTO createProduct(ProductoRequestDTO dto) {
        Producto producto = productoMapper.toEntity(dto);
        repository.save(producto);
        return productoMapper.toDTOResponse(producto);
    }

    @Override
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}
