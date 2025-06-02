package com.example.loki.service;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.Vendedor;
import com.example.loki.repository.VendedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendedorService {
    private final VendedorRepository vendedorRepository;

    public VendedorService(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
    }

    public List<Vendedor> getVendedor(){
        return vendedorRepository.findAll();
    }

    public Optional<Vendedor> getVendedorById(Long id){
        return vendedorRepository.findById(id);
    }

    public void saveVendedor(Vendedor vendedor){
        vendedorRepository.save(vendedor);
    }
    public void deleteVendedor(Long id) throws PerfilNotFound{
        if(getVendedorById(id).isEmpty()){
            throw new PerfilNotFound("Usuario no encontrado");
        }
        vendedorRepository.deleteById(id);
    }
}
