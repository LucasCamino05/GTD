package com.example.loki.service;

import com.example.loki.model.entities.Oferta;

import java.util.List;

public interface OfertaService {
    List<Oferta> getAllOfertas();
    Oferta getOfertaById(Long id);
    Oferta createOferta(Oferta oferta);
    void deleteOferta(Long id);
    void updateOferta(Long id, Double nuevoPrecio);
}
