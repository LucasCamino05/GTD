package com.example.loki.service;

import com.example.loki.exceptions.NotFoundException;
import com.example.loki.model.entities.Cliente;
import com.example.loki.model.entities.Oferta;
import com.example.loki.model.entities.Perfil;
import com.example.loki.model.entities.Vendedor;
import com.example.loki.model.enums.EstadoOferta;
import com.example.loki.repository.OfertaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfertaServiceImpl implements OfertaService{
    private final OfertaRepository repository;

    @Autowired
    public OfertaServiceImpl(OfertaRepository repository){
        this.repository = repository;
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
    public Oferta createOferta(Oferta oferta) {
        return null;
    }

    @Override
    public void deleteOferta(Long id) {

    }

    @Override
    public void updateOferta(Long id, Double nuevoPrecio) {

    }

    @Transactional
    public Oferta aceptarOferta(Long ofertaID, Perfil perfil) throws NotFoundException, AuthorizationServiceException {
        Oferta oferta = repository.findById(ofertaID)
                .orElseThrow(() -> new NotFoundException("No se encontro ninguna oferta."));
        if(perfil instanceof Cliente && oferta.getCliente().getId().equals(perfil.getId())){
            oferta.setEstado(EstadoOferta.ACEPTADA);
        } else if (perfil instanceof Vendedor && oferta.getVendedor().getId().equals(perfil.getId())) {
            oferta.setEstado(EstadoOferta.ACEPTADA);
        }else{
            throw new AuthorizationServiceException("No es posible aceptar la oferta.");
        }

        return repository.save(oferta);
    }

    @Transactional
    public Oferta rechazarOferta(Long ofertaID, Perfil perfil) throws NotFoundException, AuthorizationServiceException {
        Oferta oferta = repository.findById(ofertaID)
                .orElseThrow(() -> new NotFoundException("No se encontro ninguna oferta."));
        if(perfil instanceof Cliente && oferta.getCliente().getId().equals(perfil.getId())){
            oferta.setEstado(EstadoOferta.CANCELADA);
        } else if (perfil instanceof Vendedor && oferta.getVendedor().getId().equals(perfil.getId())) {
            oferta.setEstado(EstadoOferta.CANCELADA);
        }else{
            throw new AuthorizationServiceException("No es posible aceptar la oferta.");
        }

        return repository.save(oferta);
    }
}
