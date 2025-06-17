package com.example.loki.service;

import com.example.loki.model.entities.Oferta;
import com.example.loki.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
