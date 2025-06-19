package com.example.loki.service;

import com.example.loki.exceptions.ProductoNoEncontradoException;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompraServiceImpl implements CompraService {
    private final List<Producto> productos;
    private final NotificacionService notificacionService;
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
            CompraMapper compraMapper, NotificacionService notificacionService) {
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
        this.compraRepository = compraRepository;
        this.clienteMapper = clienteMapper;
        this.productoMapper = productoMapper;
        this.compraMapper = compraMapper;
        this.productos = new ArrayList<>();
        this.notificacionService = notificacionService;
    }

    @Transactional
    @Override
    public void confirmarCompra() throws IllegalStateException{
        validarClienteInicializado();

        for (Producto producto : productos) {
            int stockActual = producto.getStock();
            if (stockActual <= 0) {
                throw new IllegalStateException("El producto '" + producto.getNombre() + "' no tiene stock suficiente.");
            }
            producto.setStock(stockActual - 1);
            productoRepository.save(producto); // persistir el cambio de stock
        }

        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setProductos(productos);

        compraRepository.save(compra);
        notificarVendedores(productos);
        limpiarCompra();
    }

    private void notificarVendedores(List<Producto> productos) {
        for(Producto producto : productos){
            notificacionService.notificar(producto.getVendedor(),
                    cliente.getNombre()
                            + " " + cliente.getApellido()
                            + " compro un producto por: $"
                            + producto.getPrecio()
            );
        }
    }

    @Override
    public ProductoResponseDTO agregarProducto(Long productoId) throws ProductoNoEncontradoException {
        validarClienteInicializado();
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con ID: " + productoId));
        productos.add(producto);
        return productoMapper.toDTOResponse(producto);
    }

    @Override
    public ProductoResponseDTO quitarProducto(Long productoId) throws ProductoNoEncontradoException{
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow( () -> new ProductoNoEncontradoException("Producto no encontrado"));
        productos.remove(producto);
        return productoMapper.toDTOResponse(producto);
    }

    @Override
    public CompraResponseDTO obtenerCompra() throws IllegalStateException {
        validarClienteInicializado();
        Double total = productos.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();

        ClienteResponseDTO clienteDTO = clienteMapper.ClientetoDTO(this.cliente);
        List<ProductoResponseDTO> productosDTO = productos.stream()
                .map(productoMapper::toDTOResponse)
                .toList();

        return compraMapper.CompratoDTO(clienteDTO, productosDTO, total);
    }

    @Override
    public void limpiarCompra() {
        productos.clear();
    }

    @Override
    public void crearCompra(Perfil perfil) throws IllegalAccessException{
        if(perfil instanceof Vendedor){
            throw new IllegalAccessException("No puede comprar si no es cliente.");
        }

        this.cliente = (Cliente) perfil;
        this.productos.clear();
    }

    private void validarClienteInicializado() throws IllegalStateException{
        if (this.cliente == null) {
            throw new IllegalStateException("Debe iniciar una compra antes de realizar esta acción.");
        }
    }
}