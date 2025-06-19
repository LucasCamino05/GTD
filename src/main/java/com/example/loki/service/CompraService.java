package com.example.loki.service;

import com.example.loki.exceptions.ProductoNoEncontradoException;
import com.example.loki.model.dto.CompraResponseDTO;
import com.example.loki.model.dto.ProductoResponseDTO;
import com.example.loki.model.entities.Perfil;

public interface CompraService {
    void crearCompra(Perfil perfil) throws IllegalAccessException;
    ProductoResponseDTO agregarProducto(Long productoId) throws ProductoNoEncontradoException;
    ProductoResponseDTO quitarProducto(Long productoId) throws ProductoNoEncontradoException;
    CompraResponseDTO obtenerCompra();
    void limpiarCompra();
    void confirmarCompra(); // para guardar en DB
}

