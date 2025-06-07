package com.example.loki.service;

import com.example.loki.dto.ProductoRequestDTO;
import com.example.loki.dto.ProductoResponseDTO;
import com.example.loki.mappers.ProductoMapper;
import com.example.loki.model.Producto;
import com.example.loki.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
                .map(productoMapper::toDTOResponse)
                .toList();
    }

    @Override
    public ProductoResponseDTO getProductById(Long id) {
        Optional<Producto> producto = repository.streamById(id);
        if(producto.isPresent()) {
            return productoMapper.toDTOResponse(producto.get());
        }else{
            throw new IllegalArgumentException("Producto no encontrado");
        }
    }

    @Override
    public ProductoResponseDTO createProduct(ProductoRequestDTO nuevo) {
        Producto producto = productoMapper.toEntity(nuevo);
        producto.setFechaAlta(LocalDate.now());
        Producto guardado = repository.save(producto);
        return productoMapper.toDTOResponse(guardado);
    }

    @Override
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}
