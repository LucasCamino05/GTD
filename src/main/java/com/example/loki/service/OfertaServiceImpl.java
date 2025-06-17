package com.example.loki.service;

import com.example.loki.exceptions.ProductoNoEncontradoException;
import com.example.loki.model.dto.OfertaRequestDTO;
import com.example.loki.model.dto.OfertaResponseDTO;
import com.example.loki.model.entities.Cliente;
import com.example.loki.model.entities.Oferta;
import com.example.loki.model.entities.Producto;
import com.example.loki.model.enums.EstadoOferta;
import com.example.loki.model.enums.Rol;
import com.example.loki.model.mappers.OfertaMapper;
import com.example.loki.repository.OfertaRepository;
import com.example.loki.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OfertaServiceImpl implements OfertaService{
    private final OfertaMapper mapper;
    private final ProductoRepository productoRepository;
    private final OfertaRepository ofertaRepository;

    @Autowired
    public OfertaServiceImpl(OfertaRepository repository, OfertaMapper mapper, ProductoRepository productoRepository, OfertaRepository ofertaRepository) {
        this.mapper = mapper;
        this.productoRepository = productoRepository;
        this.ofertaRepository = ofertaRepository;
    }

    @Override
    public List<Oferta> getAllOfertas() {
        return List.of();
    }

    @Override
    public Oferta getOfertaById(Long id) {
        return null;
    }

    @Override
    public OfertaResponseDTO crearOferta(OfertaRequestDTO ofertaRequestDTO, Cliente cliente) throws ProductoNoEncontradoException {
        Producto producto = productoRepository.findById(ofertaRequestDTO.getIdProducto())
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado"));

        Oferta oferta = new Oferta();

        oferta.setProducto(producto);
        oferta.setVendedor(producto.getVendedor());
        oferta.setCliente(cliente);

        oferta.setFecha(LocalDate.now());
        oferta.setEstado(EstadoOferta.EN_CURSO);

        oferta.agregarPrecio(cliente.getRol(), ofertaRequestDTO.getPrecio());

        Oferta guardado = ofertaRepository.save(oferta);

        return mapper.ofertaToDTO(guardado);
    }

    @Override
    public void deleteOferta(Long id) {

    }

    @Override
    public void updateOferta(Long id, Double nuevoPrecio) {

    }
}
