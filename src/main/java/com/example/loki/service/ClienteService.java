package com.example.loki.service;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.Cliente;
import com.example.loki.repository.ClienteRepository;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

  @Autowired
  private final ClienteRepository repository;

  @Autowired
  private Validator validator;

  public ClienteService(ClienteRepository repository) {
    this.repository = repository;
  }

  public List<Cliente> getCliente() {
    return repository.findAll();
  }

  public Optional<Cliente> getClienteById(Long id) {
    return repository.findById(id);
  }

  public void saveCliente(Cliente cliente) {
    repository.save(cliente);
  }

  public void deleteCliente(Long id) throws PerfilNotFound {
    if (getClienteById(id).isEmpty()) {
      throw new PerfilNotFound("Usuario no encontrado");
    }
    repository.deleteById(id);
  }

  public void validateCliente(Cliente cliente) {
    Set<ConstraintViolation<Cliente>> errores_validacion = validator.validate(cliente);
    if (!errores_validacion.isEmpty()) {
      throw new ConstraintViolationException(null, errores_validacion);
    }
  }

  public void updateCliente(Long id, Map<String, Object> modificacion)
      throws PerfilNotFound, ConstraintViolationException {
    Cliente cliente = getClienteById(id)
        .orElseThrow(() -> new PerfilNotFound("Usuario no encontrado"));

    if (modificacion.containsKey("id")) {
      throw new IllegalArgumentException("No se puede modificar el id del cliente.");
    }

    modificacion.forEach((key, value) -> {
      Field atributo = ReflectionUtils.findField(Cliente.class, key);
      if (atributo != null) {
        atributo.setAccessible(true);
        ReflectionUtils.setField(atributo, cliente, value);
      }
    });

    validateCliente(cliente);
    saveCliente(cliente);
  }

}
