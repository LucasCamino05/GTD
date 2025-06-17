package com.example.loki.service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.Cliente;
import com.example.loki.model.dto.ClienteRequestDTO;
import com.example.loki.model.dto.ClienteResponseDTO;
import com.example.loki.model.mappers.ClienteMapper;
import com.example.loki.repository.ClienteRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Service
public class ClienteService {

  private final ClienteRepository repository;
  private final ClienteMapper clienteMapper;

  @Autowired
  private Validator validator;

  @Autowired
  public ClienteService(ClienteRepository repository, ClienteMapper clienteMapper) {
    this.repository = repository;
    this.clienteMapper = clienteMapper;
  }

  public List<ClienteResponseDTO> getClientes() {
    return repository.findAll().stream().map(clienteMapper::ClientetoDTO).toList();
  }

  public ClienteResponseDTO getCliente(Long id) throws PerfilNotFound {
    Cliente cliente = getClienteById(id);
    return clienteMapper.ClientetoDTO(cliente);
  }

  public ClienteResponseDTO saveCliente(ClienteRequestDTO clienteRequestDTO) {
    Cliente cliente = clienteMapper.ClienteDTOtoEntity(clienteRequestDTO);
    cliente.setFecha_alta(LocalDate.now());
    repository.save(cliente);
    return clienteMapper.ClientetoDTO(cliente);
  }

  public void deleteCliente(Long id)
      throws PerfilNotFound {
    repository.delete(getClienteById(id));
  }

  public void validateCliente(Cliente cliente) {
    Set<ConstraintViolation<Cliente>> errores_validacion = validator.validate(cliente);
    if (!errores_validacion.isEmpty()) {
      throw new ConstraintViolationException(null, errores_validacion);
    }
  }

  public void updateCliente(Long id, Map<String, Object> modificacion)
      throws PerfilNotFound, ConstraintViolationException {
    Cliente cliente = getClienteById(id);

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
    repository.save(cliente);
  }

  private Cliente getClienteById(Long id) throws PerfilNotFound {
    return repository.findById(id).orElseThrow(() -> new PerfilNotFound("No existe el usuario"));
  }
}
