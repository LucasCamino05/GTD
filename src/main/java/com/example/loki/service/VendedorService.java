package com.example.loki.service;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.Vendedor;
import com.example.loki.repository.VendedorRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import jakarta.validation.Validator;

@Service
public class VendedorService {
  private final VendedorRepository vendedorRepository;

  @Autowired
  private Validator validator;
  // @Autowired private ObjectMapper objectMapper;

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
    Vendedor vendedor = getVendedorById(id)
        .orElseThrow(() -> new PerfilNotFound("Usuario no encontrado"));

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
    saveVendedor(vendedor);
  }
  // */

}
