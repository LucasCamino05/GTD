package com.example.loki.service;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.Vendedor;
import com.example.loki.repository.VendedorRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import jakarta.validation.Validator;

@Service
public class VendedorService {
  private final VendedorRepository vendedorRepository;

  @Autowired
  private Validator validator;

  public VendedorService(VendedorRepository vendedorRepository) {
    this.vendedorRepository = vendedorRepository;
  }

  public List<Vendedor> getVendedor() {
    return vendedorRepository.findAll();
  }

  public Optional<Vendedor> getVendedorById(Long id) {
    return vendedorRepository.findById(id);
  }

  public void saveVendedor(Vendedor vendedor) {
    vendedorRepository.save(vendedor);
  }

  public void deleteVendedor(Long id) throws PerfilNotFound {
    if (getVendedorById(id).isEmpty()) {
      throw new PerfilNotFound("Usuario no encontrado");
    }
    vendedorRepository.deleteById(id);
  }

  public void validarVendedor(Vendedor vendedor) {
    Set<ConstraintViolation<Vendedor>> errores_validacion = validator.validate(vendedor);
    if (!errores_validacion.isEmpty()) {
      throw new ConstraintViolationException(null, errores_validacion);
    }
  }
}
