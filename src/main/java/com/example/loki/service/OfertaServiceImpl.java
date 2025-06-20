package com.example.loki.service;

import com.example.loki.exceptions.OfertaNoEncontradaException;
import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.exceptions.ProductoNoEncontradoException;
import com.example.loki.model.dto.OfertaRequestDTO;
import com.example.loki.model.dto.OfertaResponseDTO;
import com.example.loki.model.entities.*;
import com.example.loki.model.enums.EstadoOferta;
import com.example.loki.model.enums.Rol;
import com.example.loki.model.mappers.OfertaMapper;
import com.example.loki.repository.OfertaRepository;
import com.example.loki.repository.PerfilRepository;
import com.example.loki.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfertaServiceImpl implements OfertaService{
    private final OfertaMapper mapper;
    private final ProductoRepository productoRepository;
    private final OfertaRepository ofertaRepository;
    private final PerfilRepository perfilRepository;
    private final NotificacionService notificacionService;
    private final CompraServiceImpl compraService;

    @Autowired
    public OfertaServiceImpl(OfertaMapper mapper,
                             ProductoRepository productoRepository,
                             OfertaRepository ofertaRepository,
                             PerfilRepository perfilRepository,
                             NotificacionService notificacionService,
                             CompraServiceImpl compraService
    ) {
        this.mapper = mapper;
        this.productoRepository = productoRepository;
        this.ofertaRepository = ofertaRepository;
        this.perfilRepository = perfilRepository;
        this.compraService = compraService;
        this.notificacionService = notificacionService;
    }

    @Override
    public List<OfertaResponseDTO> getAllOfertas(Perfil perfil) throws PerfilNotFound {
        if(perfil instanceof Vendedor){
            Vendedor vendedor =(Vendedor) perfilRepository.findById(perfil.getId())
                    .orElseThrow(() -> new PerfilNotFound("No se encontro el perfil."));

            return vendedor.getOfertas()
                    .stream()
                    .map(oferta -> {
                        OfertaResponseDTO dto = mapper.ofertaToDTO(oferta);
//                        dto.setPrecioOfertado(oferta.getOfertas().get(vendedor.getRol()));
                        return dto;
                    })
                    .toList();
        }

        if(perfil instanceof Cliente){
            Cliente cliente = (Cliente) perfilRepository.findById(perfil.getId())
                    .orElseThrow(() -> new PerfilNotFound("No se encontro el perfil."));
            return cliente.getOfertas().stream()
                    .map(oferta -> {
                        OfertaResponseDTO dto = mapper.ofertaToDTO(oferta);
//                        dto.setPrecioOfertado(oferta.getOfertas().get(cliente.getRol()));
                        return dto;
                    })
                    .toList();
        }

        throw new PerfilNotFound("Perfil no encontrado.");
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
        oferta.setUltimaOferta(ofertaRequestDTO.getPrecio());
        oferta.setFecha(LocalDate.now());
        oferta.setEstado(EstadoOferta.EN_CURSO);


        oferta.agregarPrecio(cliente.getRol(), ofertaRequestDTO.getPrecio());

        Oferta guardado = ofertaRepository.save(oferta);
        OfertaResponseDTO guardadoDTO = mapper.ofertaToDTO(guardado);
        notificacionService.notificar(producto.getVendedor(), "Nueva oferta recibida de "
                        + cliente.getNombre()
                        + " "
                        + cliente.getApellido()
        );

//        guardadoDTO.setPrecioOfertado(guardado.getOfertas().get(cliente.getRol()));
        return mapper.ofertaToDTO(guardado);
    }

    @Override
    public void deleteOferta(Long id) {

    }

    @Override
    public void updateOferta(Long id, Double nuevoPrecio, Perfil perfil) throws OfertaNoEncontradaException {
        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() -> new OfertaNoEncontradaException("Oferta no encontrada."));

        oferta.agregarPrecio(perfil.getRol(), nuevoPrecio);
        oferta.setUltimaOferta(nuevoPrecio);
        ofertaRepository.save(oferta);

        Perfil receptor = (perfil instanceof Cliente) ? oferta.getVendedor() : oferta.getCliente();
        System.out.println("El que hace la oferta: " + perfil.getNombre());
        System.out.println("El que recibe la oferta: " + receptor.getNombre());

        notificacionService.notificar(receptor, "Nueva oferta recibida de "
                + perfil.getNombre()
                + " "
                + perfil.getApellido()
        );
    }

    //   LO HAGO ASI POR SI QUEREMOS AGREGAR ESO DE QUIEN ACEPTA O QUIEN RECHAZA LA OFERTA.
    public OfertaResponseDTO rechazarOferta(Long id, Perfil perfil) throws OfertaNoEncontradaException {
        Oferta oferta = ofertaRepository.findById(id).orElseThrow(()-> new OfertaNoEncontradaException("No encontrada"));
        if(perfil.getRol().equals(Rol.CLIENTE)){
            oferta.setEstado(EstadoOferta.CANCELADA);
        }
        else if (perfil.getRol().equals(Rol.VENDEDOR)){
            oferta.setEstado(EstadoOferta.CANCELADA);
        }
        return mapper.ofertaToDTO(oferta);
    }

    public OfertaResponseDTO aceptarOferta(Long id, Perfil perfil) throws OfertaNoEncontradaException{

        Oferta oferta = ofertaRepository.findById(id).orElseThrow(()-> new OfertaNoEncontradaException("No encontrada"));
        if(perfil.getRol().equals(Rol.CLIENTE)){
            oferta.setEstado(EstadoOferta.ACEPTADA);
        }
        else if (perfil.getRol().equals(Rol.VENDEDOR)){
            oferta.setEstado(EstadoOferta.ACEPTADA);
        }
        try {
            compraService.confirmarCompra(oferta);
        } catch (IllegalAccessException | ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }

        return mapper.ofertaToDTO(oferta);
    }
}
