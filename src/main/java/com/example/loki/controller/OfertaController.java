package com.example.loki.controller;

import com.example.loki.service.OfertaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("loki/v1/oferta")
public class OfertaController{
    private final OfertaServiceImpl ofertaService;

    @Autowired
    public OfertaController(OfertaServiceImpl ofertaService){
        this.ofertaService = ofertaService;
    }
}
