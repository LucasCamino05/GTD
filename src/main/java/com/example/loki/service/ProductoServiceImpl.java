package com.example.loki.service;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.exceptions.ProductoNoEncontradoException;
import com.example.loki.model.dto.ProductoRequestDTO;
import com.example.loki.model.dto.ProductoResponseDTO;
import com.example.loki.model.entities.Perfil;
import com.example.loki.model.entities.Vendedor;
import com.example.loki.model.mappers.ProductoMapper;
import com.example.loki.model.entities.Producto;
import com.example.loki.repository.PerfilRepository;
import com.example.loki.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository repository;
    private final ProductoMapper productoMapper;
    private final PerfilRepository perfilRepository;

    @Autowired
    public ProductoServiceImpl(ProductoRepository repository, ProductoMapper productoMapper, PerfilRepository perfilRepository) {
        this.repository = repository;
        this.productoMapper = productoMapper;
        this.perfilRepository = perfilRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> getAllProducts(Perfil perfil) throws PerfilNotFound {
        if(perfil instanceof Vendedor){
            Vendedor vendedor = (Vendedor) perfilRepository.findById(perfil.getId()).orElseThrow(() -> new PerfilNotFound("error"));
            return vendedor.getProductos()
                    .stream()
                    .map(productoMapper::toDTOResponse)
                    .toList();
        }

        return repository.findAll().stream()
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
    public ProductoResponseDTO createProduct(ProductoRequestDTO nuevo, Vendedor vendedor) {
        Producto producto = productoMapper.toEntity(nuevo);
        producto.setFechaAlta(LocalDate.now());
        producto.setVendedor(vendedor);
        Producto guardado = repository.save(producto);
        return productoMapper.toDTOResponse(guardado);
    }

    @Override
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateProducto(Long id, Map<String, Object> modificacion) throws ProductoNoEncontradoException {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado"));

        if(modificacion.containsKey("id")){
            throw new IllegalArgumentException("No se puede modificar el id del producto.");
        }

        modificacion.forEach((key, value) -> {
            Field campoModificar = ReflectionUtils.findField(Producto.class, key);
            if(campoModificar != null){
                campoModificar.setAccessible(true);
                ReflectionUtils.setField(campoModificar, producto, value);
            }
        });
        repository.save(producto);
    }
}
