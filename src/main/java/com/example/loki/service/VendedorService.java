package com.example.loki.service;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.dto.VendedorRequestDTO;
import com.example.loki.model.dto.VendedorResponseDTO;
import com.example.loki.model.mappers.VendedorMapper;
import com.example.loki.model.Vendedor;
import com.example.loki.repository.VendedorRepository;

import jakarta.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import jakarta.validation.Validator;
import java.util.Set;
import jakarta.validation.ConstraintViolationException;

@Service
public class VendedorService {
  private final VendedorRepository vendedorRepository;
  private final VendedorMapper vendedorMapper;

  @Autowired
  private Validator validator;
  // @Autowired private ObjectMapper objectMapper;

  @Autowired
  public VendedorService(VendedorRepository vendedorRepository, VendedorMapper vendedorMapper) {
    this.vendedorRepository = vendedorRepository;
    this.vendedorMapper = vendedorMapper;
  }

  public List<VendedorResponseDTO> getVendedores() {
    List<Vendedor> vendedores = vendedorRepository.findAll();
    return vendedores.stream()
        .map(vendedorMapper::toDTO)
        .toList();
  }

  public VendedorResponseDTO getVendedor(Long id)
      throws PerfilNotFound {
    Vendedor vendedor = getVendedorById(id);
    return vendedorMapper.toDTO(vendedor);
  }

  public VendedorResponseDTO saveVendedor(VendedorRequestDTO vendedorRequestDTO) {
    Vendedor vendedor = vendedorMapper.toEntity(vendedorRequestDTO);
    vendedor.setFecha_alta(LocalDate.now());
    vendedorRepository.save(vendedor);

    return vendedorMapper.toDTO(vendedor);
  }

  public void deleteVendedor(Long id)
      throws PerfilNotFound {
    Optional<Vendedor> vendedor = Optional.ofNullable(getVendedorById(id));
    if (vendedor.isEmpty()) {
      throw new PerfilNotFound("Usuario no encontrado");
    }
    vendedorRepository.delete(vendedor.get());
  }

  public void validarVendedor(Vendedor vendedor) {
    Set<ConstraintViolation<Vendedor>> errores_validacion = validator.validate(vendedor);
    if (!errores_validacion.isEmpty()) {
      throw new ConstraintViolationException(null, errores_validacion);
    }
  }

  /*
  public void modificarVendedor(Long id, Map<String, Object> modificacion)
      throws JsonMappingException, PerfilNotFound, ConstraintViolationException {
    Vendedor vendedor = getVendedorById(id)
        .orElseThrow(() -> new PerfilNotFound("Usuario no encontrado"));

    if (modificacion.containsKey("id")) {
      throw new IllegalArgumentException("No se puede modificar el id del vendedor.");
    }

    objectMapper.updateValue(vendedor, modificacion);
    validarVendedor(vendedor);
    saveVendedor(vendedor);
  }
  */

  // /*
  public void modificarVendedor(Long id, Map<String, Object> modificacion)
      throws PerfilNotFound, ConstraintViolationException {
    Vendedor vendedor = getVendedorById(id);

    if (modificacion.containsKey("id")) {
      throw new IllegalArgumentException("No se puede modificar el id del vendedor.");
    }

    modificacion.forEach((key, value) -> {
      Field atributo = ReflectionUtils.findField(Vendedor.class, key);
      if (atributo != null) {
        atributo.setAccessible(true);
        ReflectionUtils.setField(atributo, vendedor, value);
      }
    });

    validarVendedor(vendedor);
    vendedorRepository.save(vendedor);
  }
  // */

  public VendedorResponseDTO getVendedorDTO(Long id)
      throws PerfilNotFound {
    Vendedor vendedor = vendedorRepository.findById(id)
        .orElseThrow(() -> new PerfilNotFound("No se encontro vendedor."));
    return vendedorMapper.toDTO(vendedor);
  }

  private Vendedor getVendedorById(Long id)
      throws PerfilNotFound {
    return vendedorRepository.findById(id).orElseThrow(() -> new PerfilNotFound("No se encontro el usuario"));
  }
}
