package com.example.loki.service;

import com.example.loki.model.dto.ClienteResponseDTO;
import com.example.loki.model.dto.CompraResponseDTO;
import com.example.loki.model.dto.ProductoResponseDTO;
import com.example.loki.model.entities.*;
import com.example.loki.model.mappers.ClienteMapper;
import com.example.loki.model.mappers.CompraMapper;
import com.example.loki.model.mappers.ProductoMapper;
import com.example.loki.repository.ClienteRepository;
import com.example.loki.repository.CompraRepository;
import com.example.loki.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompraServiceImpl implements CompraService {
    private final List<Producto> productos;
    private Cliente cliente;

    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final CompraRepository compraRepository;
    private final ClienteMapper clienteMapper;
    private final ProductoMapper productoMapper;
    private final CompraMapper compraMapper;

    @Autowired
    public CompraServiceImpl(
            ProductoRepository productoRepository,
            ClienteRepository clienteRepository,
            CompraRepository compraRepository,
            ClienteMapper clienteMapper,
            ProductoMapper productoMapper,
            CompraMapper compraMapper) {
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
        this.compraRepository = compraRepository;
        this.clienteMapper = clienteMapper;
        this.productoMapper = productoMapper;
        this.compraMapper = compraMapper;
        this.productos = new ArrayList<>();
    }

    @Override
    public void confirmarCompra() throws IllegalStateException{
        validarClienteInicializado();
        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setProductos(productos);

        compraRepository.save(compra);
        limpiarCompra();
    }

    @Override
    public void crearCompra(Perfil perfil) throws IllegalAccessException{
        if(perfil instanceof Vendedor){
            throw new IllegalAccessException("No puede comprar si no es cliente.");
        }

        this.cliente = (Cliente) perfil;
        this.productos.clear();
    }

    @Override
    public void agregarProducto(Long productoId) throws IllegalStateException {
        validarClienteInicializado();
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));
        productos.add(producto);
    }

    @Override
    public void quitarProducto(Long productoId) {
        productos.removeIf(producto -> producto.getId().equals(productoId));
    }

    @Override
    public CompraResponseDTO obtenerCompra() throws IllegalStateException {
        validarClienteInicializado();
        Double total = productos.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();

        ClienteResponseDTO clienteDTO = clienteMapper.ClientetoDTO(cliente);
        List<ProductoResponseDTO> productosDTO = productos.stream()
                .map(productoMapper::toDTOResponse)
                .toList();

        return compraMapper.CarritotoDTO(clienteDTO, productosDTO, total);
    }

    @Override
    public void limpiarCompra() {
        productos.clear();
    }

    private void validarClienteInicializado() throws IllegalStateException{
        if (this.cliente == null) {
            throw new IllegalStateException("Debe iniciar una compra antes de realizar esta acción.");
        }
    }
}