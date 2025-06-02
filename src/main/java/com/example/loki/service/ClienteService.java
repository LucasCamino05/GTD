package com.example.loki.service;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.Cliente;
import com.example.loki.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> getCliente(){
        return repository.findAll();
    }

    public Optional<Cliente> getClienteById(Long id){
        return repository.findById(id);
    }

    public void saveCliente(Cliente cliente){
        repository.save(cliente);
    }

    public void deleteCliente(Long id) throws PerfilNotFound{
        if(getClienteById(id).isEmpty()) {
            throw new PerfilNotFound("Usuario no encontrado");
        }
        repository.deleteById(id);
    }
}
