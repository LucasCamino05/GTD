package com.example.loki.service;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.dto.ClienteRequestDTO;
import com.example.loki.model.dto.ClienteResponseDTO;
import com.example.loki.model.entities.Cliente;
import com.example.loki.model.enums.Rol;
import com.example.loki.model.mappers.ClienteMapper;
import com.example.loki.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper clienteMapper;

    @Autowired
    public ClienteService(ClienteRepository repository, ClienteMapper clienteMapper) {
        this.repository = repository;
        this.clienteMapper = clienteMapper;
    }

    public List<ClienteResponseDTO> getClientes(){
        return repository.findAll().stream().map(clienteMapper::ClientetoDTO).toList();
    }

    public ClienteResponseDTO getCliente(Long id) throws PerfilNotFound{
        Cliente cliente = getClienteById(id);
        return clienteMapper.ClientetoDTO(cliente);
    }

    public ClienteResponseDTO saveCliente(ClienteRequestDTO clienteRequestDTO){
        Cliente cliente = clienteMapper.ClienteDTOtoEntity(clienteRequestDTO);
        cliente.setRol(Rol.CLIENTE);
        cliente.setFecha_alta(LocalDate.now());
        repository.save(cliente);
        return clienteMapper.ClientetoDTO(cliente);
    }

    public void deleteCliente(Long id)
            throws PerfilNotFound{
        repository.delete(getClienteById(id));
    }

    public void modificarCliente(Long id, Map<String, Object> newAttribute)
            throws PerfilNotFound, IllegalAccessException {
        Cliente cliente = getClienteById(id);
        if(newAttribute.containsKey("id")){
            throw new IllegalAccessException("No se puede acceder a este campo.");
        }

        newAttribute.forEach((key,value) -> {
            Field campo = ReflectionUtils.findField(Cliente.class,key);
            if(campo != null) {
                campo.setAccessible(true);
                ReflectionUtils.setField(campo, cliente, value);
            }
        });
        repository.save(cliente);
    }

    private Cliente getClienteById(Long id) throws PerfilNotFound {
        return repository.findById(id).orElseThrow(() -> new PerfilNotFound("No existe el usuario"));
    }
}