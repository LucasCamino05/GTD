package com.example.loki.service;

import com.example.loki.exceptions.PerfilNotFound;
import com.example.loki.model.dto.VendedorRequestDTO;
import com.example.loki.model.dto.VendedorResponseDTO;
import com.example.loki.model.mappers.VendedorMapper;
import com.example.loki.model.Vendedor;
import com.example.loki.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

@Service
public class VendedorService {
    private final VendedorRepository vendedorRepository;
    private final VendedorMapper vendedorMapper;

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
            throws PerfilNotFound{
        Vendedor vendedor = getVendedorById(id);
        return vendedorMapper.toDTO(vendedor);
    }

    public VendedorResponseDTO saveVendedor(VendedorRequestDTO vendedorRequestDTO){
        Vendedor vendedor = vendedorMapper.toEntity(vendedorRequestDTO);
        vendedor.setFecha_alta(LocalDate.now());
        vendedorRepository.save(vendedor);
        
        return vendedorMapper.toDTO(vendedor);
    }

    public void deleteVendedor(Long id)
            throws PerfilNotFound{
        Optional<Vendedor> vendedor = Optional.ofNullable(getVendedorById(id));
        if(vendedor.isEmpty()){
            throw new PerfilNotFound("Usuario no encontrado");
        }
        vendedorRepository.delete(vendedor.get());
    }

    public void modificarVendedor(Long id,
                                  Map<String, Object> newAttribute)
            throws PerfilNotFound, IllegalAccessException{
        Vendedor vendedor = getVendedorById(id);

        if(newAttribute.containsKey("id")){
            throw new IllegalAccessException("No se puede acceder a este campo.");
        }

        newAttribute.forEach((key,value) -> {
            Field campo = ReflectionUtils.findField(Vendedor.class,key);
            if(campo != null) {
                campo.setAccessible(true);
                ReflectionUtils.setField(campo, vendedor, value);
            }
        });

        vendedorRepository.save(vendedor);
    }

    public VendedorResponseDTO getVendedorDTO(Long id)
            throws PerfilNotFound{
        Vendedor vendedor = vendedorRepository.findById(id)
                .orElseThrow(() -> new PerfilNotFound("No se encontro vendedor."));
        return vendedorMapper.toDTO(vendedor);
    }

    private Vendedor getVendedorById(Long id)
            throws PerfilNotFound{
        return vendedorRepository.findById(id).orElseThrow(() -> new PerfilNotFound("No se encontro el usuario"));
    }
}