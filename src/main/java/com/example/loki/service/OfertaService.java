package com.example.loki.service;

import com.example.loki.exceptions.ProductoNoEncontradoException;
import com.example.loki.model.dto.OfertaRequestDTO;
import com.example.loki.model.dto.OfertaResponseDTO;
import com.example.loki.model.entities.Cliente;
import com.example.loki.model.entities.Oferta;
import com.example.loki.model.enums.Rol;

import java.util.List;

public interface OfertaService {
    List<Oferta> getAllOfertas();
    Oferta getOfertaById(Long id);
    OfertaResponseDTO crearOferta(OfertaRequestDTO ofertaRequestDTO, Cliente cliente) throws ProductoNoEncontradoException;
    void deleteOferta(Long id);
    void updateOferta(Long id, Double nuevoPrecio);
}
