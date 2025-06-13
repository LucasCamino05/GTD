package com.example.loki.service;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.Cliente;
import com.example.loki.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    @Autowired
    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> getClientes(){
        return repository.findAll();
    }

    public Cliente getCliente(Long id) throws PerfilNotFound{
        return getClienteById(id);
    }

    public void saveCliente(Cliente cliente){
        repository.save(cliente);
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