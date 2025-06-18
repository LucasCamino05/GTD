package com.example.loki.service;

import com.example.loki.model.dto.CompraResponseDTO;
import com.example.loki.model.entities.Perfil;

public interface CompraService {
    void crearCompra(Perfil perfil) throws IllegalAccessException;
    void agregarProducto(Long productoId) throws IllegalStateException;
    void quitarProducto(Long productoId);
    CompraResponseDTO obtenerCompra();
    void limpiarCompra();
    void confirmarCompra(); // para guardar en DB
}

