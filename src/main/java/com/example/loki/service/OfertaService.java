package com.example.loki.service;

import com.example.loki.exceptions.OfertaNoEncontradaException;
import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.exceptions.ProductoNoEncontradoException;
import com.example.loki.model.dto.OfertaRequestDTO;
import com.example.loki.model.dto.OfertaResponseDTO;
import com.example.loki.model.entities.Cliente;
import com.example.loki.model.entities.Oferta;
import com.example.loki.model.entities.Perfil;

import java.util.List;

public interface OfertaService {
    List<OfertaResponseDTO> getAllOfertas(Perfil perfil) throws PerfilNotFound;
    Oferta getOfertaById(Long id);
    OfertaResponseDTO crearOferta(OfertaRequestDTO ofertaRequestDTO, Cliente cliente) throws ProductoNoEncontradoException;
    void deleteOferta(Long id);
    void updateOferta(Long id, Double nuevoPrecio, Perfil perfil) throws OfertaNoEncontradaException;
}
